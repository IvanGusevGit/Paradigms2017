package expression.exceptions;

public class ParsingException extends ExpressionException {
    protected String expression;
    protected int index, size;

    public ParsingException(String message) {
        super(message);
    }

    protected static String errorRepresentation(int index, int size) {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < index; i++) {
            answer.append(' ');
        }
        for (int i = 0; i < size; i++) {
            answer.append('~');
        }
        return answer.toString();
    }

}
