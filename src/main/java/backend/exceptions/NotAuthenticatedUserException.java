package backend.exceptions;

public class NotAuthenticatedUserException extends RuntimeException {
    public NotAuthenticatedUserException(String message) {
        super(message);
    }
}
