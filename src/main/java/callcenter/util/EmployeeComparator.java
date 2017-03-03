package callcenter.util;

import java.util.Comparator;

import callcenter.model.Employee;

/**
 * Compares two employees using business logic related with the preference to
 * pick up a new phone call.
 */
public class EmployeeComparator implements Comparator<Employee> {

    /**
     * Compares two employees using business logic related with the preference
     * to pick up a new phone call.
     *
     * @param empl1 Employee to be compared.
     * @param empl2 Employee to be compared.
     * @return Less than 0 if empl1 is preferable over empl2, otherwise returns
     *         an integer bigger than 0. If returns 0, both employees have same
     *         priority.
     */
    @Override
    public int compare(Employee empl1, Employee empl2) {
        return empl1.compareTo(empl2);
    }
}
