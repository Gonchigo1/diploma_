package mn.astvision.starter.model.payment;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntityWithUser;
import mn.astvision.starter.model.payment.enums.PaymentMethodType;
import mn.astvision.starter.model.payment.enums.PaymentStatus;
import org.springframework.data.mongodb.core.mapping.Sharded;

/**
 * Төлбөр цуцлахтай холбоотой дата
 * @author digz6666
 */
@Sharded(shardKey = {"number"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class PaymentCancelData extends BaseEntityWithUser {

    private String number; // төлбөрийн дугаар
    private PaymentMethodType methodType; // төлбөр төлөх хэлбэр
    private PaymentStatus status; // төлбөрийн төлөв

    private Boolean cancelResult; // cancel үр дүн
    private int cancelTryCount; // cancel дуудсан оролдлогын тоо
    private String cancelMessage; // cancel мессеж
}
