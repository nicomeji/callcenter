package callcenter.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(of = "id")
public class PhoneCall implements Comparable<PhoneCall> {
    private final Long id;
    private final Date start;
    private Date end;

    public PhoneCall(@NonNull Long id, @NonNull Date start) {
        this.start = start;
        this.id = id;
    }

    public PhoneCall(@NonNull Long id, @NonNull Date start, Date end) {
        this(id, start);
        this.end = end;
    }

    @Override
    public int compareTo(PhoneCall call) {
        if (call.getEnd() == null) {
            return 1;
        }
        if (end == null) {
            return -1;
        }
        return call.getEnd().compareTo(end);
    }
}
