package expression;

import exceptions.OverflowException;
import operations.Operations;

public class Add<T> extends AbstractBinaryOperation<T> {
    public Add(CommonExpression<T> leftArgument, CommonExpression<T> rightArgument, Operations<T> operations) {
        super(leftArgument, rightArgument, operations);
    }

    @Override
    protected T apply(T leftValue, T rightValue) throws OverflowException {
        return operations.add(leftValue, rightValue);
    }
}
