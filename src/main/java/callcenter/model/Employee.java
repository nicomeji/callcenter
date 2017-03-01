package callcenter.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(of = { "id" })
public class Employee {
    @NonNull
    private final Long id;

    @NonNull
    private String name;

    private final Set<Employee> employees = new HashSet<>();

    private PhoneCall lastCall;
}
