package exceptions;

public class IncorrectZoneException extends ParsingException{
    public IncorrectZoneException(String expression, int index, int size) {
        super("Invalid part found.\n" + expression + "\n" + errorRepresentation(index, size));
    }
}
