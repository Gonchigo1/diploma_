package mn.astvision.starter.model.email;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Мэйл илгээх queue дараалал
 * Энэ листэд орж ирсэн мэйлийг нэг удаа илгээнэ
 * Мэйл илгээхээс өмнө тухайн мэйл хаяг emailSuppress болон emailUnsubscribe жагсаалтад байгаа эсэхийг шалгана
 * Bounce хийсэн мэйл хаягийг emailSuppress-д хадгална
 * @author digz6666
 */
@Sharded(shardKey = {"id"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class EmailQueue extends BaseEntity {

    private String from; // мэйл илгээх хаяг
    private String fromName; // мэйл илгээх хаягны нэр
    private String to; // мэйл хүлээн авах хаяг

    private String subject; // гарчиг
    private String content; // агуулга
    private List<String> attachments;

    private Boolean sent; // илгээсэн эсэх
    private LocalDateTime sentDate; // илгээсэн огноо
    private String errorMessage; // алдааны мессэж
    private int tryCount; // илгээх оролдлогын тоо
}
