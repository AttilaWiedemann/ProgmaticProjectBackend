package backend.exceptions;

public class NotExistingUserException extends RuntimeException {
    public NotExistingUserException(String message) {
        super(message);
    }
}
