package expression;

import expression.exceptions.OverflowException;

public class CheckedSubtract extends AbstractCheckedBinaryOperation {
    public CheckedSubtract(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    public void checkArguments(int leftValue, int rightValue) throws OverflowException {
        if (leftValue >= 0 && rightValue < 0 && leftValue - Integer.MAX_VALUE > rightValue) {
            throw new OverflowException("Subtract" + getStringOFArguments(leftValue, rightValue));
        }
        if (leftValue <= 0 && rightValue > 0 && Integer.MIN_VALUE - leftValue > -rightValue) {
            throw new OverflowException("Subtract" + getStringOFArguments(leftValue, rightValue));
        }
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue - rightValue;
    }
}
