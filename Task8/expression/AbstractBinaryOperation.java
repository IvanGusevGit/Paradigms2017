package expression;


import exceptions.EvaluatingException;
import operations.Operations;

import java.util.Objects;

public abstract class AbstractBinaryOperation<T> implements CommonExpression<T> {
    private final CommonExpression<T> leftArgument, rightArgument;
    protected final Operations<T> operations;
    public AbstractBinaryOperation(CommonExpression leftArgument, CommonExpression rightArgument, Operations<T> operations) {
        this.leftArgument = Objects.requireNonNull(leftArgument, "Null argument found.");
        this.rightArgument = Objects.requireNonNull(rightArgument, "Null argument found.");
        this.operations = Objects.requireNonNull(operations, "Null argumnet found");
    }

    protected abstract T apply(T leftValue, T rightValue) throws EvaluatingException;

    @Override
    public T evaluate(T x, T y, T z) throws EvaluatingException {
        return apply(leftArgument.evaluate(x, y, z), rightArgument.evaluate(x, y, z));
    }

}
