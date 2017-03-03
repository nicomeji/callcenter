package callcenter.service;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.commons.lang3.RandomUtils;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import callcenter.model.Employee;
import callcenter.model.PhoneCall;
import callcenter.util.EmployeeQueue;
import lombok.Data;
import lombok.NonNull;

public class DispatcherIntegrationTest extends EasyMockSupport {
    private static final int CALLS = 30;
    private final int MILISECONDS = 1000;
    private EmployeeQueue queue;
    private CallRouting callRouting;

    @Before
    public void setUp() {
        queue = new EmployeeQueue();
    }

    @After
    public void tearDown() {
        verifyAll();
        resetAll();
    }

    @Test
    public void phoneCallDispatchedOneEmployee() throws Exception {
        callRouting = new ThreadSafeCallRoutingMock(createMock(CallRouting.class), () -> {
            return 0;
        });
        try (Dispatcher dispatcher = new Dispatcher(queue, callRouting)) {
            PhoneCall call1 = new PhoneCall(1L, new Date());
            PhoneCall call2 = new PhoneCall(2L, new Date());
            PhoneCall call3 = new PhoneCall(3L, new Date());
            PhoneCall call4 = new PhoneCall(4L, new Date());
            PhoneCall call5 = new PhoneCall(5L, new Date());
            Employee emp = new Employee(1L, "Juan", Employee.Type.EMPLOYEE);
            queue.add(emp);
            expect(callRouting.sendCall(emp, call1)).andReturn(null).once();
            expect(callRouting.sendCall(emp, call2)).andReturn(null).once();
            expect(callRouting.sendCall(emp, call3)).andReturn(null).once();
            expect(callRouting.sendCall(emp, call4)).andReturn(null).once();
            expect(callRouting.sendCall(emp, call5)).andReturn(null).once();
            replayAll();
            dispatcher.dispatchCall(call1);
            dispatcher.dispatchCall(call2);
            dispatcher.dispatchCall(call3);
            dispatcher.dispatchCall(call4);
            dispatcher.dispatchCall(call5);
        }
    }

    @Test
    public void phoneCallDispatchedManyEmployees() throws Exception {
        callRouting = new ThreadSafeCallRoutingMock(createMock(CallRouting.class), () -> {
            return RandomUtils.nextInt(5 * MILISECONDS, 10 * MILISECONDS);
        });
        try (Dispatcher dispatcher = new Dispatcher(queue, callRouting)) {
            PhoneCall call1 = new PhoneCall(1L, new Date());
            PhoneCall call2 = new PhoneCall(2L, new Date());
            PhoneCall call3 = new PhoneCall(3L, new Date());
            PhoneCall call4 = new PhoneCall(4L, new Date());
            PhoneCall call5 = new PhoneCall(5L, new Date());
            Employee emp1 = new Employee(1L, "Juan", Employee.Type.EMPLOYEE);
            Employee emp2 = new Employee(2L, "Carlos", Employee.Type.EMPLOYEE);
            Employee emp3 = new Employee(3L, "Pedro", Employee.Type.EMPLOYEE);
            Employee emp4 = new Employee(4L, "Luis", Employee.Type.EMPLOYEE);
            Employee emp5 = new Employee(5L, "Miguel", Employee.Type.EMPLOYEE);
            expect(callRouting.sendCall(emp1, call1)).andReturn(null).once();
            expect(callRouting.sendCall(emp2, call2)).andReturn(null).once();
            expect(callRouting.sendCall(emp3, call3)).andReturn(null).once();
            expect(callRouting.sendCall(emp4, call4)).andReturn(null).once();
            expect(callRouting.sendCall(emp5, call5)).andReturn(null).once();
            replayAll();
            dispatcher.dispatchCall(call1);
            queue.add(emp1);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call2);
            queue.add(emp2);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call3);
            queue.add(emp3);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call4);
            queue.add(emp4);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call5);
            queue.add(emp5);
        }
    }

    @Test
    public void phoneCallDispatchedManyEmployees2() throws Exception {
        callRouting = new ThreadSafeCallRoutingMock(createMock(CallRouting.class), () -> {
            return 1 * MILISECONDS;
        });
        try (Dispatcher dispatcher = new Dispatcher(queue, callRouting)) {
            PhoneCall call1 = new PhoneCall(11L, new Date());
            PhoneCall call2 = new PhoneCall(12L, new Date());
            PhoneCall call3 = new PhoneCall(13L, new Date());
            PhoneCall call4 = new PhoneCall(14L, new Date());
            PhoneCall call5 = new PhoneCall(15L, new Date());
            PhoneCall call6 = new PhoneCall(16L, new Date());
            PhoneCall call7 = new PhoneCall(17L, new Date());
            PhoneCall call8 = new PhoneCall(18L, new Date());
            PhoneCall call9 = new PhoneCall(19L, new Date());
            PhoneCall call10 = new PhoneCall(20L, new Date());
            Employee emp1 = new Employee(1L, "Juan", Employee.Type.EMPLOYEE);
            Employee emp2 = getEmployee(2L, "Pablo", getDateMinusDays(2), Employee.Type.EMPLOYEE);
            Employee emp3 = getEmployee(3L, "Pedro", getDateMinusDays(1), Employee.Type.EMPLOYEE);
            Employee emp4 = new Employee(4L, "Miguel", Employee.Type.SUPERVISOR);
            Employee emp5 = getEmployee(5L, "Carlos", getDateMinusDays(2), Employee.Type.SUPERVISOR);
            Employee emp6 = getEmployee(6L, "Martin", getDateMinusDays(1), Employee.Type.SUPERVISOR);
            Employee emp7 = new Employee(7L, "Nico", Employee.Type.DIRECTOR);
            Employee emp8 = getEmployee(8L, "Sofi", getDateMinusDays(1), Employee.Type.DIRECTOR);
            expect(callRouting.sendCall(emp1, call1)).andReturn(null).once();
            expect(callRouting.sendCall(emp2, call2)).andReturn(null).once();
            expect(callRouting.sendCall(emp3, call3)).andReturn(null).once();
            expect(callRouting.sendCall(emp4, call4)).andReturn(null).once();
            expect(callRouting.sendCall(emp5, call5)).andReturn(null).once();
            expect(callRouting.sendCall(emp6, call6)).andReturn(null).once();
            expect(callRouting.sendCall(emp7, call7)).andReturn(null).once();
            expect(callRouting.sendCall(emp8, call8)).andReturn(null).once();
            expect(callRouting.sendCall(emp1, call9)).andReturn(null).once();
            expect(callRouting.sendCall(emp2, call10)).andReturn(null).once();
            queue.add(emp1);
            queue.add(emp2);
            queue.add(emp3);
            queue.add(emp4);
            queue.add(emp5);
            queue.add(emp6);
            queue.add(emp7);
            queue.add(emp8);
            replayAll();
            dispatcher.dispatchCall(call1);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call2);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call3);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call4);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call5);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call6);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call7);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call8);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call9);
            TimeUnit.MILLISECONDS.sleep(10);
            dispatcher.dispatchCall(call10);
        }
    }

    @Test
    public void loadTest() throws Exception {
        callRouting = new ThreadSafeCallRoutingMock(createMock(CallRouting.class), () -> {
            return RandomUtils.nextInt(5 * MILISECONDS, 10 * MILISECONDS);
        });
        try (Dispatcher dispatcher = new Dispatcher(queue, callRouting)) {
            Employee emp1 = new Employee(1L, "Juan", Employee.Type.EMPLOYEE);
            Employee emp2 = getEmployee(2L, "Pablo", getDateMinusDays(4), Employee.Type.EMPLOYEE);
            Employee emp3 = getEmployee(3L, "Pedro", getDateMinusDays(3), Employee.Type.EMPLOYEE);
            Employee emp4 = getEmployee(4L, "Angel", getDateMinusDays(2), Employee.Type.EMPLOYEE);
            Employee emp5 = getEmployee(5L, "Diego", getDateMinusDays(1), Employee.Type.EMPLOYEE);
            Employee emp6 = new Employee(6L, "Miguel", Employee.Type.SUPERVISOR);
            Employee emp7 = getEmployee(7L, "Carlos", getDateMinusDays(2), Employee.Type.SUPERVISOR);
            Employee emp8 = getEmployee(8L, "Martin", getDateMinusDays(1), Employee.Type.SUPERVISOR);
            Employee emp9 = new Employee(9L, "Nico", Employee.Type.DIRECTOR);
            Employee emp10 = getEmployee(10L, "Sofi", getDateMinusDays(1), Employee.Type.DIRECTOR);
            expect(callRouting.sendCall(anyObject(), anyObject())).andReturn(null).times(CALLS);
            queue.add(emp1);
            queue.add(emp2);
            queue.add(emp3);
            queue.add(emp4);
            queue.add(emp5);
            queue.add(emp6);
            queue.add(emp7);
            queue.add(emp8);
            queue.add(emp9);
            queue.add(emp10);
            replayAll();
            for (Long i = 0L; i < CALLS; i++) {
                dispatcher.dispatchCall(new PhoneCall(i, new Date()));
            }
            TimeUnit.SECONDS.sleep(CALLS);
        }
    }

    private Employee getEmployee(Long id, String name, Date lastCallEndDate, Employee.Type type) {
        Employee emp = new Employee(id, name, type);
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

    @Data
    private class ThreadSafeCallRoutingMock implements CallRouting {
        @NonNull
        private final CallRouting mock;

        @NonNull
        private final Supplier<Integer> getPhoneCallDuration;

        @Override
        public PhoneCall sendCall(Employee employee, PhoneCall call) throws InterruptedException {
            int i = getPhoneCallDuration.get();
            TimeUnit.MILLISECONDS.sleep(i);
            synchronized (mock) {
                return mock.sendCall(employee, call);
            }
        }

        @Override
        public PhoneCall waitting(PhoneCall call) {
            return null;
        }
    }
}
