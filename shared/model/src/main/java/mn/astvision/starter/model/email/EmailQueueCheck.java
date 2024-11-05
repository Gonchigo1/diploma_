package mn.astvision.starter.model.email;

import lombok.*;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

/**
 * Мэйл илгээх queue-д нэг мэйл олон удаа орохоос сэргийлсэн шалгах дата
 * @author digz6666
 */
@Sharded(shardKey = {"dataId", "dataType"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmailQueueCheck extends BaseEntity {

    private String dataType;
    private String dataId;
    private String email;
}
