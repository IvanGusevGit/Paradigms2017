package exceptions;

public class ClosingBeforeOpeningParenthesisException extends BrokenParenthesisBalance {
    public ClosingBeforeOpeningParenthesisException(String expression, int index) {
        super("Closing before opening parenthesis found in " + index + ".\n" + expression + "\n" + errorRepresentation(index, 1));
    }
}
