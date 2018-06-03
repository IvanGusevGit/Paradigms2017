package expression.exceptions;

public class IllegalConstArgument extends ParsingException {
    public IllegalConstArgument(String expression, int index, int size) {
        super("Illegal constant in position of " + index + ".\n" + expression + "\n" + errorRepresentation(index, size));
    }
}
