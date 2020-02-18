package backend.exceptions;

public class ExistingConversationException extends RuntimeException {
    public ExistingConversationException(String message) {
        super(message);
    }
}
