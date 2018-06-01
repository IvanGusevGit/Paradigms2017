package expression;

public class Not extends AbstractUnaryOperation {
    public Not(CommonExpression argument) {
        super(argument);
    }

    @Override
    protected double apply(double value) {
        return value;
    }

    @Override
    protected int apply(int value) {
        return ~value;
    }
}
