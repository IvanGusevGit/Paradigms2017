package expression;

public class And extends AbstractBinaryOperation {
    public And(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue & rightValue;
    }

    @Override
    protected double apply(double leftValue, double rightValue) {
        return leftValue;
    }
}
