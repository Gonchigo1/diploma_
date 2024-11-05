package mn.astvision.starter.exception.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author digz6666
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentException extends RuntimeException {
    private Object responseError;
    private boolean result;

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Object responseError) {
        super(message);
        this.responseError = responseError;
        this.result = true;
    }

    public PaymentException(String message, Object responseError, boolean result) {
        super(message);
        this.responseError = responseError;
        this.result = result;
    }
}
