package mn.astvision.starter.model.analytics;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mn.astvision.starter.model.BaseEntity;

import java.time.LocalDate;

/**
 * @author digz6666
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SessionCount extends BaseEntity {

    private LocalDate date;
    private long count;
}
