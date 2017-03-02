package callcenter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Data
@EqualsAndHashCode(of = { "id" })
public class Employee implements Comparable<Employee> {
    public enum Type {
        EMPLOYEE(3), SUPERVISOR(2), DIRECTOR(1);

        Type(int priority) {
            this.priority = priority;
        }

        @Getter
        private final Integer priority;
    }

    @NonNull
    private final Long id;

    @NonNull
    private String name;

    @NonNull
    private final Type type;

    private PhoneCall lastCall;

    @Override
    public int compareTo(Employee employee) {
        if (employee == null || employee.getType().getPriority().compareTo(this.getType().getPriority()) < 0) {
            return -1;
        }
        if (employee.getType().equals(this.getType())) {
            return cmpCalls(employee.getLastCall());
        }
        return 1;
    }

    private int cmpCalls(PhoneCall call) {
        if (call == null) {
            return 1;
        }
        if (lastCall == null) {
            return -1;
        }
        return call.compareTo(lastCall);
    }
}
