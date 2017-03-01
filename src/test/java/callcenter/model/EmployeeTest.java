package callcenter.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.easymock.EasyMockSupport;
import org.junit.Test;

public class EmployeeTest extends EasyMockSupport {
    @Test(expected = NullPointerException.class)
    public void everyEmployeeHasId() {
        getNew(null, "Juan Perez");
    }

    @Test(expected = NullPointerException.class)
    public void everyEmployeeHasName() {
        getNew(1L, null);
    }

    @Test
    public void employeesIdMustBeUnique() {
        assertThat(getNew(1L, "Juan Perez"), equalTo(getNew(1L, "Armando Paredes")));
        assertThat(getNew(1L, "Juan Perez").hashCode(), equalTo(getNew(1L, "Armando Paredes").hashCode()));
    }

    protected Employee getNew(final Long id, final String name) {
        return new Employee(id, name);
    }
}
