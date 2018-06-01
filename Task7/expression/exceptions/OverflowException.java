package expression.exceptions;

public class OverflowException extends EvaluatingException{
    public OverflowException(String operationData) {
        super("overflow", operationData);
    }
}
