package callcenter.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * This model represents a phone call. Each call is comparable by it's end time,
 * which is useful to know which employee has the recent call.
 */
@Data
@EqualsAndHashCode(of = "id")
public class PhoneCall implements Comparable<PhoneCall> {
    private final Long id;
    private final Date start;
    private Date answered;
    private Date end;

    public PhoneCall(@NonNull Long id, @NonNull Date start) {
        this.start = start;
        this.id = id;
    }

    @Override
    public int compareTo(@NonNull PhoneCall call) {
        if (call.getEnd() == null) {
            return -1;
        }
        if (end == null) {
            return 1;
        }
        return call.getEnd().compareTo(end);
    }
}
