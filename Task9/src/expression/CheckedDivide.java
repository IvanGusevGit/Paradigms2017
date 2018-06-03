package expression;

import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class CheckedDivide extends AbstractCheckedBinaryOperation {
    public CheckedDivide(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    public void checkArguments(int leftValue, int rightValue) throws DivisionByZeroException, OverflowException {
        if (rightValue == 0) {
            throw new DivisionByZeroException("Divide" + getStringOFArguments(leftValue, rightValue));
        } else if (leftValue == Integer.MIN_VALUE && rightValue == -1) {
            throw new OverflowException("Divide" + getStringOFArguments(leftValue, rightValue));
        }
    }

    @Override
    protected int apply(int leftValue, int rightValue) {
        return leftValue / rightValue;
    }
}