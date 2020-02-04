package backend.exceptions;

public class ExistingUserException extends RuntimeException {

    public ExistingUserException(String s) {

        super(s);
    }
}
