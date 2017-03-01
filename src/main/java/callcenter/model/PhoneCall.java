package callcenter.model;

import java.util.Date;

import lombok.Data;
import lombok.NonNull;

@Data
public class PhoneCall {
    @NonNull
    private final Long id;

    @NonNull
    private final Date start;

    private Date end;

    public boolean isClosed () {
        return end != null;
    }
}
