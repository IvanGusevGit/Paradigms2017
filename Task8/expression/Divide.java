package expression;

import exceptions.EvaluatingException;
import operations.Operations;

public class Divide<T> extends AbstractBinaryOperation<T> {
    public Divide(CommonExpression<T> leftArgument, CommonExpression<T> rightArgument, Operations<T> operations) {
        super(leftArgument, rightArgument, operations);
    }

    @Override
    protected T apply(T leftValue, T rightValue) throws EvaluatingException {
        return operations.divide(leftValue, rightValue);
    }
}

