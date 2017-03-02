package callcenter.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class PhoneCallTest {
    @Test(expected = NullPointerException.class)
    public void everyCallHasId() {
        new PhoneCall(null, new Date(), new Date());
    }

    @Test(expected = NullPointerException.class)
    public void everyCallHasStart() {
        new PhoneCall(1L, null, new Date());
    }

    @Test
    public void endIsOptional() {
        PhoneCall call = new PhoneCall(1L, new Date(), null);
        assertThat(call.getEnd(), is(nullValue()));
    }

    @Test
    public void callsAreComparedByEndTime() {
        Set<PhoneCall> orderedSet = new TreeSet<>();
        PhoneCall call1 = new PhoneCall(1L, new Date(), null);
        PhoneCall call2 = new PhoneCall(2L, new Date(), getDateMinusDays(1));
        PhoneCall call3 = new PhoneCall(3L, new Date(), getDateMinusDays(0));
        orderedSet.add(call1);
        orderedSet.add(call2);
        orderedSet.add(call3);
        assertThat(orderedSet, contains(call1, call2, call3));
    }

    @Test
    public void callsAreComparedByEndTime2() {
        Set<PhoneCall> orderedSet = new TreeSet<>();
        PhoneCall call1 = new PhoneCall(1L, new Date(), null);
        PhoneCall call2 = new PhoneCall(2L, new Date(), getDateMinusDays(1));
        PhoneCall call3 = new PhoneCall(3L, new Date(), getDateMinusDays(0));
        orderedSet.add(call3);
        orderedSet.add(call2);
        orderedSet.add(call1);
        assertThat(orderedSet, contains(call1, call2, call3));
    }

    private Date getDateMinusDays(int minusDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -minusDays);
        return cal.getTime();
    }
}
