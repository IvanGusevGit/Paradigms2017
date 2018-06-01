package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.IllegalOperationArgumentException;
import expression.exceptions.OverflowException;

public class CheckedPow extends AbstractCheckedBinaryOperation {
    public CheckedPow(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    protected int apply(int leftValue, int rightValue) throws EvaluatingException {
        checkArguments(leftValue, rightValue);
        int answer = 1;
        try {
            while (rightValue > 0) {
                if (rightValue % 2 == 1) {
                    answer = new CheckedMultiply(new Const(answer), new Const(leftValue)).evaluate(0,0,0);
                    rightValue--;
                } else {
                    leftValue = new CheckedMultiply(new Const(leftValue), new Const(leftValue)).evaluate(0,0,0);
                    rightValue = rightValue >> 1;
                }
            }
        } catch (OverflowException e) {
            throw new OverflowException("Multiply" + getStringOFArguments(leftValue, rightValue));
        }
        return answer;
    }

    @Override
    public void checkArguments(int leftValue, int rightValue) throws EvaluatingException {
        if (rightValue < 0 || (leftValue == 0 && rightValue == 0)) {
            throw new IllegalOperationArgumentException("Pow" + getStringOFArguments(leftValue, rightValue));
        }
    }

}
