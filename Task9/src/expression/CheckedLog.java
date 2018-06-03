package expression;

import expression.exceptions.EvaluatingException;
import expression.exceptions.IllegalOperationArgumentException;
import expression.exceptions.OverflowException;

public class CheckedLog extends AbstractCheckedBinaryOperation {
    public CheckedLog(CommonExpression leftArgument, CommonExpression rightArgument) {
        super(leftArgument, rightArgument);
    }

    @Override
    protected int apply(int leftValue, int rightValue) throws EvaluatingException{
        checkArguments(leftValue, rightValue);
        int answer = 0;
        try {
            while (leftValue / rightValue != 0) {
                answer++;
                leftValue = new CheckedDivide(new Const(leftValue), new Const(rightValue)).evaluate(0,0,0);
            }
        } catch (EvaluatingException e) {
            throw new OverflowException("Log" + getStringOFArguments(leftValue, rightValue));
        }
        return answer;
    }

    @Override
    public void checkArguments(int leftValue, int rightValue) throws IllegalOperationArgumentException {
        if (rightValue == 1 || rightValue <= 0 || leftValue <= 0) {
            throw new IllegalOperationArgumentException("Log" + getStringOFArguments(leftValue, rightValue));
        }
    }
}
