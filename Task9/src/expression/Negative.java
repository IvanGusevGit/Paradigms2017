package expression;

public class Negative extends AbstractUnaryOperation {
    public Negative(CommonExpression argument) {
        super(argument);
    }

    @Override
    protected double apply(double value) {
        return -value;
    }

    @Override
    protected int apply(int value) {
        return -value;
    }
}
