package expression;

import java.util.Objects;

public abstract class AbstractBinaryOperation implements CommonExpression {
    private final CommonExpression leftArgument, rightArgument;

    public AbstractBinaryOperation(CommonExpression leftArgument, CommonExpression rightArgument) {
        Objects.requireNonNull(leftArgument, "Null argument found.");
        Objects.requireNonNull(rightArgument, "Null argument found.");
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
    }

    protected abstract int apply(int leftValue, int rightValue);

    protected abstract double apply(double leftValue, double rightValue);

    @Override
    public double evaluate(double x) {
        return apply(leftArgument.evaluate(x), rightArgument.evaluate(x));
    }

    @Override
    public int evaluate(int value) {
        return apply(leftArgument.evaluate(value), rightArgument.evaluate(value));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return apply(leftArgument.evaluate(x, y, z), rightArgument.evaluate(x, y, z));
    }

}
