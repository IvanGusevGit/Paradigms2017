package expression;

import exceptions.EvaluatingException;
import operations.Operations;

public abstract class AbstractUnaryOperation<T> implements CommonExpression<T> {
    private CommonExpression<T> argument;
    protected Operations<T> operations;

    public AbstractUnaryOperation(CommonExpression<T> argument, Operations<T> operations) {
        this.argument = argument;
        this.operations = operations;
    }

    protected abstract T apply(T value) throws EvaluatingException;

    @Override
    public T evaluate(T x, T y, T z) throws EvaluatingException {
        return apply(argument.evaluate(x, y, z));
    }
}
