package callcenter.service;

import callcenter.model.PhoneCall;

public interface Dispatcher {
    public void dispatchCall(PhoneCall phoneCall);
}
