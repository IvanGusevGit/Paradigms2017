package expression;

public class Divide extends AbstractBinaryOperation {
    public Divide(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    protected double apply(double leftValue, double rightValue) {
        return leftValue / rightValue;
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue / rightValue;
    }

}

