package mn.astvision.starter.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import mn.astvision.starter.model.BaseEntityWithUser;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.LocalDateTime;

/**
 * Төлбөрийн нэмэлт дата
 * @author digz6666
 */
@Sharded(shardKey = {"number"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class PaymentAdditionalData extends BaseEntityWithUser {

    private String number; // төлбөрийн дугаар

    // НӨАТ дата
    private Boolean vatResult; // НӨАТ үр дүн
    private String vatMessage; // НӨАТ мессеж
    private String vatLotteryNumber; // НӨАТ сугалааны дугаар
    private String vatBillId; // НӨАТ баримтын ДДТД (33 орон)
    private String vatQrData; // НӨАТ баримтын QR үүсгэх дата

    // хэрэглэгчийн мэдээлэл
    private String contactCitizenNumber; // холбогдох пасспорт/үнэмлэхийн дугаар // TODO Ариука-тай тодруулах
    private String contactPhone; // холбоо барих утас
    private String contactEmail; // холбоо барих мэйл

    // qpay-тэй холбоотой дата
    private String qpayQrCode; // for qpay
    @JsonIgnore
    private LocalDateTime invoiceExpiryDate; // for qpay

    // картын мэдээлэл (картаар төлсөн бол)
    private String cardNumber; // төлсөн картын дугаар
    private String cardOwnerName; // төлсөн картын эзэмшигчийн нэр

    // төлбөрийн PDF баримтын дата
    private String receiptUrl; // төлбөрийн баримтын URL (PDF файл)
    private String receiptErrorMessage; // төлбөрийн PDF баримт үүсгэхэд гарсан алдааны мессеж

    // initiate request data
    private LocalDateTime initiateRequestDate;
    @JsonIgnore
    private Object initiateRequestData;

    // initiate response data
    @JsonIgnore
    private LocalDateTime initiateResponseDate;
    @JsonIgnore
    private Object initiateResponseData;
    @JsonIgnore
    private Boolean initiateResult;

    // process response data
    @JsonIgnore
    private LocalDateTime processResponseDate;
    @JsonIgnore
    private Object processResponseData;
    private String processResponseMessage;

    // verify request data
    @JsonIgnore
    private LocalDateTime verifyRequestDate;
    @JsonIgnore
    private Object verifyRequestData;

    // verify response data
    @JsonIgnore
    private LocalDateTime verifyResponseDate;
    @JsonIgnore
    private Object verifyResponseData;

    private boolean confirmed; // төлбөрийг боловсруулсан эсэх
    private String confirmMessage; // төлбөр боловсруулахад гарсан алдаа
}
