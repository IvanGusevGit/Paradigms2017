package expression.exceptions;

public class IncrorrectZoneException extends ParsingException{
    public IncrorrectZoneException(String expression, int index, int size) {
        super("Invalid part found.\n" + expression + "\n" + errorRepresentation(index, size));
    }
}
