package mn.astvision.starter.model.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author digz6666
 * Төлбөр төлөх хэлбэрийн төрөл
 */
@Getter
@AllArgsConstructor
public enum PaymentMethodType {

    GOLOMT_BANK_CARD("GOLOMT_BANK_CARD", 1), // картын мэдээллээ оруулж төлбөр хийх
    GOLOMT_BANK_SOCIAL_PAY("GOLOMT_BANK_SOCIAL_PAY", 2), // social pay - ашиглах бол нээх
    QPAY("QPAY", 3), // QPay
    WECHAT_PAY("WECHAT_PAY", 4), // WeChat Pay
    ;

    private final String value;
    private final Integer order;

    public static PaymentMethodType fromString(String value) {
        try {
            return PaymentMethodType.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}
