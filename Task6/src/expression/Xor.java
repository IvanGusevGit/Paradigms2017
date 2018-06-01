package expression;

public class Xor extends AbstractBinaryOperation {
    public Xor(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    protected double apply(double leftValue, double rightValue) {
        return leftValue;
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue ^ rightValue;
    }
}
