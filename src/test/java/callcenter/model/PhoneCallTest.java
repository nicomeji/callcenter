package callcenter.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class PhoneCallTest {
    @Test
    public void everyCallHasIdAndStart() {
        PhoneCall call = new PhoneCall(1L, new Date());
        assertThat(call.getId(), is(notNullValue()));
        assertThat(call.getStart(), is(notNullValue()));
    }

    @Test(expected = NullPointerException.class)
    public void callCannotBeCompareWithNull() {
        PhoneCall call = new PhoneCall(1L, new Date());
        call.compareTo(null);
    }

    @Test(expected = NullPointerException.class)
    public void everyCallHasId() {
        new PhoneCall(null, new Date());
    }

    @Test(expected = NullPointerException.class)
    public void everyCallHasStart() {
        new PhoneCall(1L, null);
    }

    @Test
    public void endIsOptional() {
        PhoneCall call = new PhoneCall(1L, new Date());
        assertThat(call.getEnd(), is(nullValue()));
    }

    @Test
    public void callsAreComparedByEndTime() {
        Set<PhoneCall> orderedSet = new TreeSet<>();
        PhoneCall call1 = new PhoneCall(1L, new Date());
        PhoneCall call2 = new PhoneCall(2L, new Date());
        call2.setEnd(getDateMinusDays(1));
        PhoneCall call3 = new PhoneCall(3L, new Date());
        call3.setEnd(getDateMinusDays(0));
        orderedSet.add(call1);
        orderedSet.add(call2);
        orderedSet.add(call3);
        assertThat(orderedSet, contains(call3, call2, call1));
    }

    @Test
    public void callsAreComparedByEndTime2() {
        Set<PhoneCall> orderedSet = new TreeSet<>();
        PhoneCall call1 = new PhoneCall(1L, new Date());
        PhoneCall call2 = new PhoneCall(2L, new Date());
        call2.setEnd(getDateMinusDays(1));
        PhoneCall call3 = new PhoneCall(3L, new Date());
        call3.setEnd(getDateMinusDays(0));
        orderedSet.add(call3);
        orderedSet.add(call2);
        orderedSet.add(call1);
        assertThat(orderedSet, contains(call3, call2, call1));
    }

    private Date getDateMinusDays(int minusDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -minusDays);
        return cal.getTime();
    }
}
