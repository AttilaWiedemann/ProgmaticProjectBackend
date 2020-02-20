package backend.exceptions;

public class NotValidFileException extends RuntimeException {
    public NotValidFileException(String message) {
        super(message);
    }
}
