package exceptions;

public class InvalidOperationException extends EvaluatingException {
    public InvalidOperationException(String whereabouts) {
        super("Invalid operation", whereabouts);
    }
}
