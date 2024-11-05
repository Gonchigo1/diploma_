package mn.astvision.starter.model.email;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

/**
 * Мэйл илгээхгүй жагсаалтад дараах 2 тохиолдолд хадгална
 * - Bounce хийсэн буюу байхгүй мэйл хаягруу мэйл явуулсан
 * - Unsubscribe хийсэн буюу Хэрэглэгч мэйл хүлээн авахгүй гэж дарсан
 * Энэ листэнд орсон бол ахиж мэйл явуулахгүй
 */
@Sharded(shardKey = {"email"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class EmailSuppress extends BaseEntity {

    private String email;
    private int tryCount; // оролдлогын тоо
    private boolean active; // идэвхтэй эсэх
    private EmailSuppressType suppressType; // мэйл явуулахгүй байх төрөл
}
