package callcenter.service;

import callcenter.model.Employee;
import callcenter.model.PhoneCall;

/**
 * This interface provides a simple contract to interact with a routing service.
 * The routing service is responsible of redirect a phone call to a terminal
 * (employee's computer).
 */
public interface CallRouting {
    /**
     *
     * @param employee
     *            Employee responsible of pick up the phone call.
     * @param call
     *            Represents the call itself.
     * @return Returns the closed call.
     * @throws InterruptedException
     */
    public PhoneCall sendCall(Employee employee, PhoneCall call) throws InterruptedException;

    /**
     * It sends the call to waiting status. So the client can listen waiting
     * music. Every call starts in waiting state.
     *
     * @param call
     *            Represents the call itself.
     * @return Return the phone call.
     */
    public PhoneCall waitting(PhoneCall call);
}
