package callcenter.service.impl;

import org.easymock.EasyMockSupport;
import org.junit.Test;

import callcenter.model.PhoneCall;
import callcenter.service.CallRouting;

public class DispatcherImplTest extends EasyMockSupport {
    @Test
    public void pickupPhoneByEmployee () {
        DispatcherImpl dispatcher = new DispatcherImpl();
        PhoneCall phoneCall = createNiceMock(PhoneCall.class);
        dispatcher.setCallRouting(createNiceMock(CallRouting.class));
        replayAll();
        dispatcher.dispatchCall(phoneCall);
        verifyAll();
        resetAll();
    }
}
