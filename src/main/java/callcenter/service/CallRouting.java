package callcenter.service;

import callcenter.model.Employee;
import callcenter.model.PhoneCall;

public interface CallRouting {
    public void sendCall(Employee employee, PhoneCall call);

    public void waitting(PhoneCall call);
}
