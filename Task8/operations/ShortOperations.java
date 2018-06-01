package operations;

import exceptions.DivisionByZeroException;
import exceptions.EvaluatingException;
import exceptions.IllegalConstArgument;
import exceptions.InvalidOperationException;

public class ShortOperations extends AbstractOperations<Short>{

    public ShortOperations() {
        super(false, null);
    }

    @Override
    protected Short applyNegate(Short x) {
        return (short)(-x);
    }

    @Override
    protected Short applyAdd(Short x, Short y) {
        return (short)(x + y);
    }

    @Override
    protected Short applySubtract(Short x, Short y) {
        return (short)(x - y);
    }

    @Override
    protected Short applyMultiply(Short x, Short y) {
        return (short)(x * y);
    }

    @Override
    protected Short applyDivide(Short x, Short y) throws EvaluatingException {
        if (y == 0) throw new DivisionByZeroException("(" + x + ", " + y + ")");
        return (short)(x / y);
    }

    @Override
    public Short min(Short x, Short y) {
        return (x < y) ? x : y;
    }

    @Override
    public Short max(Short x, Short y) {
        return (x > y) ? x : y;
    }

    @Override
    public int count(Short x) throws InvalidOperationException {
        return Integer.bitCount(((int)x) << 16);
    }

    @Override
    public Short parseNumber(String pattern) throws IllegalConstArgument {
        try {
            return (short)Integer.parseInt(pattern);
        } catch (NumberFormatException e) {
            throw new IllegalConstArgument(pattern, 0, pattern.length());
        }
    }

    @Override
    public String toString(Short x) {
        return x.toString();
    }

    @Override
    public boolean isNumberCharacter(char c) {
        return Character.isDigit(c);
    }
}
