package callcenter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * This model represents a employee. It's comparable according the business
 * logic related with the preference to pick up a new phone call.
 */
@Data
@EqualsAndHashCode(of = { "id" })
public class Employee implements Comparable<Employee> {
    public enum Type {
        EMPLOYEE(1), SUPERVISOR(2), DIRECTOR(3);

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
    public int compareTo(@NonNull Employee employee) {
        if (employee.getType().equals(this.getType())) {
            return cmpCalls(employee.getLastCall());
        }
        return type.getPriority().compareTo(employee.getType().getPriority());
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
