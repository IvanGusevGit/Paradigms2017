package expression.exceptions;

public class BrokenParenthesisBalance extends ParsingException {
    public BrokenParenthesisBalance(String message) {
        super(message);
    }
}
