package backend.exceptions;

public class NotExistingConversationException extends RuntimeException {
    public NotExistingConversationException (String message) {
        super(message);
    }
}
