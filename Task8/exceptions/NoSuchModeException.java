package exceptions;

public class NoSuchModeException extends ExpressionException {

    public NoSuchModeException(String message) {
        super("No mode with name " + message + " was found.");
    }

}
