package expression;

import expression.exceptions.OverflowException;

public class CheckedMultiply extends AbstractCheckedBinaryOperation {
    public CheckedMultiply(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    public void checkArguments(int leftValue, int rightValue) throws OverflowException {
        if (leftValue > 0 && rightValue > 0 && Integer.MAX_VALUE / leftValue < rightValue) {
            throw new OverflowException("Multiply" + getStringOFArguments(leftValue, rightValue));
        } else if (leftValue > 0 && rightValue < 0 && Integer.MIN_VALUE / leftValue > rightValue) {
            throw new OverflowException("Multiply" + getStringOFArguments(leftValue, rightValue));
        } else if (leftValue < 0 && rightValue > 0 && Integer.MIN_VALUE / rightValue > leftValue) {
            throw new OverflowException("Multiply" + getStringOFArguments(leftValue, rightValue));
        } else if (leftValue < 0 && rightValue < 0 && Integer.MAX_VALUE / leftValue > rightValue) {
            throw new OverflowException("Multiply" + getStringOFArguments(leftValue, rightValue));
        }
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue * rightValue;
    }
}

