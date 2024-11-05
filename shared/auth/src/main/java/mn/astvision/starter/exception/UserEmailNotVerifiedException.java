package mn.astvision.starter.exception;

/**
 * @author digz6666
 */
public class UserEmailNotVerifiedException extends RuntimeException {

    public UserEmailNotVerifiedException(String message) {
        super(message);
    }

    public UserEmailNotVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}
