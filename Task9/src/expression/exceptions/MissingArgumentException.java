package expression.exceptions;

public class MissingArgumentException extends ParsingException{
    public MissingArgumentException(String expression, int index) {
        super("Expected argument is not found in position " + index + ".\n" + expression + "\n" + errorRepresentation(index, 1));
    }
}
