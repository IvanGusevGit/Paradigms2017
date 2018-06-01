package exceptions;

public class MissingClosingParenthesisException extends BrokenParenthesisBalance {
    public MissingClosingParenthesisException(String expression, int index) {
        super("Expected \")\" in position " + index + ".\n" + expression + "\n" + errorRepresentation(index, 1));
    }
}
