package expression;

public class Divide extends AbstractBinaryOperation {
    public Divide(MultiExpression leftArgument, MultiExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue / rightValue;
    }

    @Override
    protected double apply(double leftValue, double rightValue) {
        return leftValue / rightValue;
    }
}

