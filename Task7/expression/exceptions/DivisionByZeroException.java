package expression.exceptions;

public class DivisionByZeroException extends EvaluatingException{
    public DivisionByZeroException(String operationData) {
        super("division by zero", operationData);
    }
}
