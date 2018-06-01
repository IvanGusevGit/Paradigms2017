package expression;

import exceptions.OverflowException;
import operations.Operations;

public class Negative<T> extends AbstractUnaryOperation<T> {
    public Negative(CommonExpression<T> argument, Operations<T> operations) {
        super(argument, operations);
    }

    @Override
    protected T apply(T value) throws OverflowException {
        return operations.negate(value);
    }
}
