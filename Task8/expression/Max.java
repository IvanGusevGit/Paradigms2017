package expression;

import exceptions.EvaluatingException;
import operations.Operations;

public class Max<T> extends AbstractBinaryOperation<T> {

    public Max(CommonExpression<T> leftArgument, CommonExpression<T> rightArgument, Operations<T> operations) {
        super(leftArgument, rightArgument, operations);
    }

    @Override
    protected T apply(T leftValue, T rightValue) throws EvaluatingException {
        return operations.max(leftValue, rightValue);
    }
}
