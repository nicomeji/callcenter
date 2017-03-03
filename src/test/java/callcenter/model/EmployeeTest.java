package callcenter.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Date;
import java.util.TreeSet;

import org.easymock.EasyMockSupport;
import org.junit.Test;

public class EmployeeTest extends EasyMockSupport {
    @Test
    public void everyEmployeeHasIdAndName() {
        Employee juan = new Employee(1L, "Juan Perez", Employee.Type.EMPLOYEE);
        assertThat(juan.getId(), is(1L));
        assertThat(juan.getName(), is("Juan Perez"));
    }

    @Test(expected=NullPointerException.class)
    public void employeeCannotBeCompareWithNull() {
        Employee juan = new Employee(1L, "Juan Perez", Employee.Type.EMPLOYEE);
        juan.compareTo(null);
    }

    @Test(expected = NullPointerException.class)
    public void everyEmployeeHasId() {
        new Employee(null, "Juan Perez", Employee.Type.EMPLOYEE);
    }

    @Test(expected = NullPointerException.class)
    public void everyEmployeeHasName() {
        new Employee(1L, null, Employee.Type.EMPLOYEE);
    }

    @Test(expected = NullPointerException.class)
    public void everyEmployeeHasType() {
        new Employee(1L, "Juan Perez", null);
    }

    @Test
    public void employeesIdMustBeUnique() {
        assertThat(new Employee(1L, "Juan Perez", Employee.Type.EMPLOYEE), is(equalTo(new Employee(1L, "Armando Paredes", Employee.Type.EMPLOYEE))));
        assertThat(new Employee(1L, "Juan Perez", Employee.Type.EMPLOYEE).hashCode(),
                is(equalTo(new Employee(1L, "Armando Paredes", Employee.Type.EMPLOYEE).hashCode())));
    }

    @Test
    public void employeesIdMustBeUnique2() {
        assertThat(new Employee(1L, "Juan Perez", Employee.Type.EMPLOYEE), is(equalTo(new Employee(1L, "Armando Paredes", Employee.Type.SUPERVISOR))));
        assertThat(new Employee(1L, "Juan Perez", Employee.Type.EMPLOYEE).hashCode(),
                is(equalTo(new Employee(1L, "Armando Paredes", Employee.Type.SUPERVISOR).hashCode())));
    }

    @Test
    public void employeesHaveMorePriority() {
        Employee juan = new Employee(1L, "Juan", Employee.Type.EMPLOYEE);
        Employee pedro = new Employee(2L, "Pedro", Employee.Type.SUPERVISOR);
        Employee jorge = new Employee(3L, "Jorge", Employee.Type.DIRECTOR);
        TreeSet<Employee> orderedSet = new TreeSet<>();
        orderedSet.add(jorge);
        orderedSet.add(pedro);
        orderedSet.add(juan);
        assertThat(orderedSet.first(), is(juan));
        assertThat(orderedSet, contains(juan, pedro, jorge));
    }

    @Test
    public void employeesHaveMorePriority2() {
        Employee juan = new Employee(1L, "Juan", Employee.Type.EMPLOYEE);
        Employee pablo = getEmployee(2L, "Pablo", new Date(), Employee.Type.EMPLOYEE);
        Employee pedro = new Employee(3L, "Pedro", Employee.Type.SUPERVISOR);
        Employee luis = getEmployee(4L, "Luis", new Date(), Employee.Type.SUPERVISOR);
        Employee jorge = new Employee(5L, "Jorge", Employee.Type.DIRECTOR);
        Employee carlos = getEmployee(6L, "Carlos", new Date(), Employee.Type.DIRECTOR);
        TreeSet<Employee> orderedSet = new TreeSet<>();
        orderedSet.add(pedro);
        orderedSet.add(carlos);
        orderedSet.add(luis);
        orderedSet.add(pablo);
        orderedSet.add(jorge);
        orderedSet.add(juan);
        assertThat(orderedSet.first(), is(juan));
        assertThat(orderedSet, contains(juan, pablo, pedro, luis, jorge, carlos));
    }

    private Employee getEmployee(Long id, String name, Date lastCallEndDate, Employee.Type type) {
        Employee emp = new Employee(id, name, type);
        PhoneCall call = new PhoneCall(id, lastCallEndDate);
        call.setEnd(lastCallEndDate);
        emp.setLastCall(call);
        return emp;
    }
}
