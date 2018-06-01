package exceptions;

public class IllegalOperationArgumentException extends EvaluatingException {
    public IllegalOperationArgumentException(String operationData) {
        super("Illegal Argument", operationData);
    }
}
