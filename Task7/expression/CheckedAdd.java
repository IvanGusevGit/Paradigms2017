package expression;

import expression.exceptions.OverflowException;

public class CheckedAdd extends AbstractCheckedBinaryOperation {
    public CheckedAdd(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    public void checkArguments(int leftValue, int rightValue) throws OverflowException {
        if (leftValue > 0 && Integer.MAX_VALUE - leftValue < rightValue) {
            throw new OverflowException("Add" + getStringOFArguments(leftValue, rightValue));
        }
        if (leftValue < 0 && Integer.MIN_VALUE - leftValue > rightValue) {
            throw new OverflowException("Add" + getStringOFArguments(leftValue, rightValue));
        }
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }
}
