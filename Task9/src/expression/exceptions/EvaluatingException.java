package expression.exceptions;

public class EvaluatingException extends ExpressionException {
    public EvaluatingException(String problem, String operationData) {
        super("Evaluating failed by " + problem + " in " + operationData);
    }
}
