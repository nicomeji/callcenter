package callcenter.util;

import java.util.Comparator;

import callcenter.model.Employee;

public class EmployeeComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee empl1, Employee empl2) {
        return empl1.compareTo(empl2);
    }
}
