package mn.astvision.starter.exception;

public class SystemApiConfException extends RuntimeException {

    public SystemApiConfException(String message) {
        super(message);
    }

    public SystemApiConfException(String message, Throwable cause) {
        super(message, cause);
    }
}