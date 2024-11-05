package mn.astvision.starter.exception.aspect;

public class ValidateUserException extends RuntimeException {

    public ValidateUserException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidateUserException(String msg) {
        super(msg);
    }
}
