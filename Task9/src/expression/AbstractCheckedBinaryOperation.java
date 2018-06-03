package expression;

import expression.exceptions.EvaluatingException;

public abstract class AbstractCheckedBinaryOperation implements CommonExpression {
    private final CommonExpression leftArgument, rightArgument;

    protected String getStringOFArguments(int leftValue, int rightValue) {
        return "(" + leftValue + ", " + rightValue + ")";
    }

    public AbstractCheckedBinaryOperation(CommonExpression leftArgument, CommonExpression rightArgument) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int leftValue = leftArgument.evaluate(x, y, z);
        int rightValue = rightArgument.evaluate(x, y, z);
        checkArguments(leftValue, rightValue);
        return apply(leftValue, rightValue);
    }

    @Override
    public double evaluate(double x) {
        throw new Error("Bad Command");
    }

    @Override
    public int evaluate(int x) {
        throw new Error("Bad Command");
    }

    protected abstract int apply(int leftValue, int rightValue) throws EvaluatingException;

    public abstract void checkArguments(int leftValue, int rightValue) throws EvaluatingException;

}
