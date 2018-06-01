package expression;

import exceptions.EvaluatingException;
import operations.Operations;

public class Min<T> extends AbstractBinaryOperation<T> {

    public Min(CommonExpression<T> leftArgument, CommonExpression<T> rightArgument, Operations<T> operations) {
        super(leftArgument, rightArgument, operations);
    }

    @Override
    protected T apply(T leftValue, T rightValue) throws EvaluatingException {
        return operations.min(leftValue, rightValue);
    }
}
