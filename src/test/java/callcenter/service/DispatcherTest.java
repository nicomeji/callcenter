package callcenter.service;

import static org.easymock.EasyMock.expect;

import java.util.Calendar;
import java.util.Date;

import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import callcenter.model.Employee;
import callcenter.model.PhoneCall;
import callcenter.util.EmployeeQueue;

public class DispatcherTest extends EasyMockSupport {
    private EmployeeQueue queue;
    private CallRouting callRouting;

    @Before
    public void setUp() {
        queue = createStrictMock(EmployeeQueue.class);
        callRouting = createStrictMock(CallRouting.class);
    }

    @After
    public void tearDown() {
        verifyAll();
        resetAll();
    }

    @Test
    public void phoneCallDispatched() throws Exception {
        try(Dispatcher dispatcher = new Dispatcher(queue, callRouting)) {
            PhoneCall call = new PhoneCall(1L, new Date());
            Employee emp = getEmployee(1L, getDateMinusDays(1));
            expect(queue.poll()).andReturn(emp).once();
            expect(queue.add(emp)).andReturn(Boolean.TRUE).once();
            expect(callRouting.sendCall(emp, call)).andReturn(null).once();
            replayAll();
            dispatcher.dispatchCall(call);
        }
    }

    @Test
    public void phoneCallDispatched2() throws Exception {
        try(Dispatcher dispatcher = new Dispatcher(queue, callRouting)) {
            PhoneCall call1 = new PhoneCall(1L, new Date());
            PhoneCall call2 = new PhoneCall(2L, new Date());
            PhoneCall call3 = new PhoneCall(3L, new Date());
            Employee emp1 = getEmployee(1L, getDateMinusDays(2));
            Employee emp2 = getEmployee(2L, getDateMinusDays(1));
            Employee emp3 = getEmployee(3L, getDateMinusDays(0));

            expect(queue.poll()).andReturn(emp1).once();
            expect(queue.add(emp1)).andReturn(Boolean.TRUE).once();
            expect(callRouting.sendCall(emp1, call1)).andReturn(null).once();
            
            expect(queue.poll()).andReturn(emp2).once();
            expect(queue.add(emp2)).andReturn(Boolean.TRUE).once();
            expect(callRouting.sendCall(emp2, call2)).andReturn(null).once();
            
            expect(queue.poll()).andReturn(emp3).once();
            expect(queue.add(emp3)).andReturn(Boolean.TRUE).once();
            expect(callRouting.sendCall(emp3, call3)).andReturn(null).once();
            replayAll();
            dispatcher.dispatchCall(call1);
            dispatcher.dispatchCall(call2);
            dispatcher.dispatchCall(call3);
        }
    }

    private Employee getEmployee(Long id, Date lastCallEndDate) {
        Employee emp = new Employee(id, id.toString(), Employee.Type.EMPLOYEE);
        PhoneCall call = new PhoneCall(id, lastCallEndDate);
        call.setEnd(lastCallEndDate);
        emp.setLastCall(call);
        return emp;
    }

    private Date getDateMinusDays(int minusDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -minusDays);
        return cal.getTime();
    }
}
