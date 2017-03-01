package callcenter.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.Test;

public class PhoneCallTest {
    @Test(expected = NullPointerException.class)
    public void everyCallHasId() {
        new PhoneCall(null, new Date());
    }

    @Test(expected = NullPointerException.class)
    public void everyCallHasStartTimestamp() {
        new PhoneCall(1L, null);
    }

    @Test
    public void callMayHaveEndDate() {
        PhoneCall call = new PhoneCall(1L, new Date());
        assertThat(call.isClosed(), is(false));
        call.setEnd(new Date());
        assertThat(call.isClosed(), is(true));
    }
}
