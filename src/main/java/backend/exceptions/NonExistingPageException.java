package backend.exceptions;

public class NonExistingPageException extends RuntimeException {
    public NonExistingPageException(String message){super(message);}
}
