package exceptions;

public class UnknownOperandException extends ParsingException{
    public UnknownOperandException(String expression, int index, int size) {
        super("Unknown operand in position of " + index + ".\n" + expression + "\n" + errorRepresentation(index, size));
    }
}
