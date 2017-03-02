package callcenter.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import callcenter.model.Employee;
import callcenter.model.PhoneCall;

public class EmployeeQueueTest {
    private EmployeeQueue queue;

    @Test
    public void employeeWithMoreIdleTimeGoFirst() {
        Employee juan = getEmployee(1L, getDateMinusDays(0));
        Employee armando = getEmployee(2L, getDateMinusDays(1));
        Employee carlos = getEmployee(3L, getDateMinusDays(2));
        Employee martin = getEmployee(4L, getDateMinusDays(3));
        Employee pablo = getEmployee(5L, getDateMinusDays(4));

        queue.add(juan);
        queue.add(pablo);
        queue.add(armando);
        queue.add(martin);
        queue.add(carlos);

        assertThat(queue.poll(), is(equalTo(pablo)));
        assertThat(queue.poll(), is(equalTo(martin)));
        assertThat(queue.poll(), is(equalTo(carlos)));
        assertThat(queue.poll(), is(equalTo(armando)));
        assertThat(queue.poll(), is(equalTo(juan)));
    }

    @Test
    public void supervisorWithMoreIdleTimeGoFirst() {
        Employee juan = getSupervisor(1L, getDateMinusDays(0));
        Employee armando = getSupervisor(2L, getDateMinusDays(1));
        Employee carlos = getSupervisor(3L, getDateMinusDays(2));
        Employee martin = getSupervisor(4L, getDateMinusDays(3));
        Employee pablo = getSupervisor(5L, getDateMinusDays(4));

        queue.add(juan);
        queue.add(pablo);
        queue.add(armando);
        queue.add(martin);
        queue.add(carlos);

        assertThat(queue.poll(), is(equalTo(pablo)));
        assertThat(queue.poll(), is(equalTo(martin)));
        assertThat(queue.poll(), is(equalTo(carlos)));
        assertThat(queue.poll(), is(equalTo(armando)));
        assertThat(queue.poll(), is(equalTo(juan)));
    }

    @Test
    public void directorWithMoreIdleTimeGoFirst() {
        Employee juan = getDirector(1L, getDateMinusDays(0));
        Employee armando = getDirector(2L, getDateMinusDays(1));
        Employee carlos = getDirector(3L, getDateMinusDays(2));
        Employee martin = getDirector(4L, getDateMinusDays(3));
        Employee pablo = getDirector(5L, getDateMinusDays(4));

        queue.add(juan);
        queue.add(pablo);
        queue.add(armando);
        queue.add(martin);
        queue.add(carlos);

        Employee a = queue.poll();
        assertThat(a, is(equalTo(pablo)));
        assertThat(queue.poll(), is(equalTo(martin)));
        assertThat(queue.poll(), is(equalTo(carlos)));
        assertThat(queue.poll(), is(equalTo(armando)));
        assertThat(queue.poll(), is(equalTo(juan)));
    }

    @Test
    public void supervisorGoIfNoEmployeeIsAvailable() {
        Employee juan = getEmployee(1L, getDateMinusDays(0));
        Employee armando = getEmployee(2L, getDateMinusDays(1));
        Employee carlos = getEmployee(3L, getDateMinusDays(2));
        Employee martin = getSupervisor(4L, getDateMinusDays(3));
        Employee pablo = getSupervisor(5L, getDateMinusDays(4));

        queue.add(juan);
        queue.add(pablo);
        queue.add(armando);
        queue.add(martin);
        queue.add(carlos);

        assertThat(queue.poll(), is(equalTo(carlos)));
        assertThat(queue.poll(), is(equalTo(armando)));
        assertThat(queue.poll(), is(equalTo(juan)));
        assertThat(queue.poll(), is(equalTo(pablo)));
        assertThat(queue.poll(), is(equalTo(martin)));
    }

    @Test
    public void directorGoIfNoEmployeeIsAvailable() {
        Employee juan = getEmployee(1L, getDateMinusDays(0));
        Employee armando = getEmployee(2L, getDateMinusDays(1));
        Employee carlos = getEmployee(3L, getDateMinusDays(2));
        Employee martin = getDirector(4L, getDateMinusDays(3));
        Employee pablo = getDirector(5L, getDateMinusDays(4));

        queue.add(juan);
        queue.add(pablo);
        queue.add(armando);
        queue.add(martin);
        queue.add(carlos);

        assertThat(queue.poll(), is(equalTo(carlos)));
        assertThat(queue.poll(), is(equalTo(armando)));
        assertThat(queue.poll(), is(equalTo(juan)));
        assertThat(queue.poll(), is(equalTo(pablo)));
        assertThat(queue.poll(), is(equalTo(martin)));
    }

    @Test
    public void directorGoIfNoSupervisorIsAvailable() {
        Employee juan = getSupervisor(1L, getDateMinusDays(0));
        Employee armando = getSupervisor(2L, getDateMinusDays(1));
        Employee carlos = getSupervisor(3L, getDateMinusDays(2));
        Employee martin = getDirector(4L, getDateMinusDays(3));
        Employee pablo = getDirector(5L, getDateMinusDays(4));

        queue.add(juan);
        queue.add(pablo);
        queue.add(armando);
        queue.add(martin);
        queue.add(carlos);

        assertThat(queue.poll(), is(equalTo(carlos)));
        assertThat(queue.poll(), is(equalTo(armando)));
        assertThat(queue.poll(), is(equalTo(juan)));
        assertThat(queue.poll(), is(equalTo(pablo)));
        assertThat(queue.poll(), is(equalTo(martin)));
    }

    @Test
    public void employeeWithoutLastCallGoFirst() {
        Employee juan = new Employee(1L, "1", Employee.Type.EMPLOYEE);
        Employee armando = getSupervisor(2L, getDateMinusDays(1));
        Employee carlos = getSupervisor(3L, getDateMinusDays(2));
        Employee martin = getDirector(4L, getDateMinusDays(3));
        Employee pablo = getDirector(5L, getDateMinusDays(4));

        queue.add(juan);
        queue.add(pablo);
        queue.add(armando);
        queue.add(martin);
        queue.add(carlos);

        assertThat(queue.poll(), is(equalTo(juan)));
        assertThat(queue.poll(), is(equalTo(carlos)));
        assertThat(queue.poll(), is(equalTo(armando)));
        assertThat(queue.poll(), is(equalTo(pablo)));
        assertThat(queue.poll(), is(equalTo(martin)));
    }

    @Test
    public void general() {
        Employee emp1 = new Employee(1L, "1", Employee.Type.EMPLOYEE);
        Employee emp2 = getEmployee(2L, getDateMinusDays(0));
        Employee emp3 = getEmployee(3L, getDateMinusDays(1));
        Employee emp4 = new Employee(4L, "4", Employee.Type.SUPERVISOR);
        Employee emp5 = getSupervisor(5L, getDateMinusDays(0));
        Employee emp6 = getSupervisor(6L, getDateMinusDays(1));
        Employee emp7 = new Employee(7L, "7", Employee.Type.DIRECTOR);
        Employee emp8 = getDirector(8L, getDateMinusDays(0));
        Employee emp9 = getDirector(9L, getDateMinusDays(1));

        queue.add(emp9);
        queue.add(emp1);
        queue.add(emp7);
        queue.add(emp5);
        queue.add(emp2);
        queue.add(emp8);
        queue.add(emp3);
        queue.add(emp4);
        queue.add(emp6);
        
        assertThat(queue.poll(), is(equalTo(emp1)));
        assertThat(queue.poll(), is(equalTo(emp3)));
        assertThat(queue.poll(), is(equalTo(emp2)));
        assertThat(queue.poll(), is(equalTo(emp4)));
        assertThat(queue.poll(), is(equalTo(emp6)));
        assertThat(queue.poll(), is(equalTo(emp5)));
        assertThat(queue.poll(), is(equalTo(emp7)));
        assertThat(queue.poll(), is(equalTo(emp9)));
        assertThat(queue.poll(), is(equalTo(emp8)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotAddDuplicateEmployee() {
        queue.add(getEmployee(1L, getDateMinusDays(0)));
        queue.add(getEmployee(1L, getDateMinusDays(1)));
    }

    @Before
    public void setUp () {
        queue = new EmployeeQueue();
    }

    @After
    public void tearDown() {
        queue.clear();
    }

    private Date getDateMinusDays(int minusDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -minusDays);
        return cal.getTime();
    }

    private Employee getEmployee(Long id, Date lastCallEndDate) {
        Employee emp = new Employee(id, id.toString(), Employee.Type.EMPLOYEE);
        emp.setLastCall(new PhoneCall(id, lastCallEndDate,lastCallEndDate));
        return emp;
    }

    private Employee getSupervisor(Long id, Date lastCallEndDate) {
        Employee emp = new Employee(id, id.toString(), Employee.Type.SUPERVISOR);
        emp.setLastCall(new PhoneCall(id, lastCallEndDate,lastCallEndDate));
        return emp;
    }

    private Employee getDirector(Long id, Date lastCallEndDate) {
        Employee emp = new Employee(id, id.toString(), Employee.Type.DIRECTOR);
        emp.setLastCall(new PhoneCall(id, lastCallEndDate,lastCallEndDate));
        return emp;
    }
}
