package mn.astvision.starter.exception.email;

/**
 * @author digz6666
 */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
