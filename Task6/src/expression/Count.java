package expression;

public class Count extends AbstractUnaryOperation {
    public Count(CommonExpression argument) {
        super(argument);
    }

    @Override
    protected double apply(double value) {
        return value;
    }

    @Override
    protected int apply(int value) {
        return Integer.bitCount(value);
    }
}
