package operations;

import exceptions.DivisionByZeroException;
import exceptions.OverflowException;

public class IntegerOperationChecker implements OperationChecker<Integer>{

    @Override
    public void checkNegate(Integer value) throws OverflowException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("Negate" + getStringOfArguments(value));
        }
    }

    @Override
    public void checkAdd(Integer leftValue, Integer rightValue) throws OverflowException {
        if (leftValue > 0 && Integer.MAX_VALUE - leftValue < rightValue) {
            throw new OverflowException("Add" + getStringOfArguments(leftValue, rightValue));
        }
        if (leftValue < 0 && Integer.MIN_VALUE - leftValue > rightValue) {
            throw new OverflowException("Add" + getStringOfArguments(leftValue, rightValue));
        }
    }

    @Override
    public void checkSubtract(Integer leftValue, Integer rightValue) throws OverflowException{
        if (leftValue >= 0 && rightValue < 0 && leftValue - Integer.MAX_VALUE > rightValue) {
            throw new OverflowException("Subtract.java" + getStringOfArguments(leftValue, rightValue));
        }
        if (leftValue <= 0 && rightValue > 0 && Integer.MIN_VALUE - leftValue > -rightValue) {
            throw new OverflowException("Subtract.java" + getStringOfArguments(leftValue, rightValue));
        }
    }

    @Override
    public void checkMultiply(Integer leftValue, Integer rightValue) throws OverflowException {
        if (leftValue > 0 && rightValue > 0 && Integer.MAX_VALUE / leftValue < rightValue) {
            throw new OverflowException("Multiply.java" + getStringOfArguments(leftValue, rightValue));
        } else if (leftValue > 0 && rightValue < 0 && Integer.MIN_VALUE / leftValue > rightValue) {
            throw new OverflowException("Multiply.java" + getStringOfArguments(leftValue, rightValue));
        } else if (leftValue < 0 && rightValue > 0 && Integer.MIN_VALUE / rightValue > leftValue) {
            throw new OverflowException("Multiply.java" + getStringOfArguments(leftValue, rightValue));
        } else if (leftValue < 0 && rightValue < 0 && Integer.MAX_VALUE / leftValue > rightValue) {
            throw new OverflowException("Multiply.java" + getStringOfArguments(leftValue, rightValue));
        }
    }

    @Override
    public void checkDivide(Integer leftValue, Integer rightValue) throws DivisionByZeroException, OverflowException {
        if (rightValue == 0) {
            throw new DivisionByZeroException("Divide" + getStringOfArguments(leftValue, rightValue));
        } else if (leftValue == Integer.MIN_VALUE && rightValue == -1) {
            throw new OverflowException("Divide" + getStringOfArguments(leftValue, rightValue));
        }
    }

    @Override
    public String toStringType(Integer x) {
        return x.toString();
    }
}
