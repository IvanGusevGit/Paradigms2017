package expression;

import expression.exceptions.OverflowException;

public class CheckedNegate extends AbstractCheckedUnaryOperation {
    public CheckedNegate(CommonExpression argument) {
        super(argument);
    }

    @Override
    public void check(int value) throws OverflowException {
        if (value == Integer.MIN_VALUE) {
            throw new OverflowException("Negate" + getStringOfArguments(value));
        }
    }

    @Override
    protected int apply(int value) {
        return -value;
    }
}
