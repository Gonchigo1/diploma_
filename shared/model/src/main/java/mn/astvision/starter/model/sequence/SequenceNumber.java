package mn.astvision.starter.model.sequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntity;
import mn.astvision.starter.model.sequence.enums.SequenceType;

@FieldNameConstants
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SequenceNumber extends BaseEntity {

    private SequenceType type;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int sequence;
}
