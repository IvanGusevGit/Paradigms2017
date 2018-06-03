package expression;

import expression.exceptions.EvaluatingException;

public abstract class AbstractCheckedUnaryOperation implements CommonExpression {
    private final CommonExpression argument;

    protected String getStringOfArguments(int value) {
        return Integer.toString(value);
    }

    public AbstractCheckedUnaryOperation(CommonExpression argument) {
        this.argument = argument;
    }

    @Override
    public double evaluate(double x) {
        throw new Error("Bad command");
    }

    @Override
    public int evaluate(int x) {
        throw new Error("Bad command");
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int value = argument.evaluate(x, y, z);
        check(value);
        return apply(value);
    }

    protected abstract int apply(int value) throws EvaluatingException;

    public abstract void check(int value) throws EvaluatingException;
}
