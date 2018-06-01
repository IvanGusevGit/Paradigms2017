package expression;

public abstract class AbstractUnaryOperation implements CommonExpression {
    protected CommonExpression argument;

    public AbstractUnaryOperation(CommonExpression argument) {
        this.argument = argument;
    }

    protected abstract int apply(int value);

    protected abstract double apply(double value);

    @Override
    public double evaluate(double x) {
        return apply(argument.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return apply(argument.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return apply(argument.evaluate(x, y, z));
    }
}
