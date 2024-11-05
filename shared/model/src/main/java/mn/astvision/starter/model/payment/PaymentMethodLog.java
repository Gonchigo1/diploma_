package mn.astvision.starter.model.payment;

import lombok.*;
import mn.astvision.starter.model.BaseEntityWithUser;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;

/**
 * Төлбөрийн лог
 * @author digz6666
 */
@Sharded(shardKey = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentMethodLog extends BaseEntityWithUser {

    private String number; // төлбөрийн дугаар

    // initiate request data
    private LocalDateTime initiateRequestDate;
    private Object initiateRequestData;

    // initiate response data
    private LocalDateTime initiateResponseDate;
    private Object initiateResponseData;
    private Boolean initiateResult;

    // process response data
    private LocalDateTime processResponseDate;
    private Object processResponseData;
    private String processResponseMessage;

    // verify request data
    private LocalDateTime verifyRequestDate;
    private Object verifyRequestData;

    // verify response data
    private LocalDateTime verifyResponseDate;
    private Object verifyResponseData;
    private Boolean verifyResult; // verify үр дүн
    private String verifyMessage; // verify мессеж
}
