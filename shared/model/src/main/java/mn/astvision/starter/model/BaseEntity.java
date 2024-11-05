package mn.astvision.starter.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

/**
 * @author digz6666
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class BaseEntity extends BaseEntityWithoutId {

    @Id
    private String id;

    @Transient
    public String getKey() {
        return this.id;
    }
}
