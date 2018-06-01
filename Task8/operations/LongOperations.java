package operations;

import exceptions.DivisionByZeroException;
import exceptions.EvaluatingException;
import exceptions.IllegalConstArgument;
import exceptions.InvalidOperationException;

public class LongOperations extends AbstractOperations<Long>{

    public LongOperations() {
        super(false, null);
    }

    @Override
    protected Long applyNegate(Long x) {
        return -x;
    }

    @Override
    protected Long applyAdd(Long x, Long y) {
        return x + y;
    }

    @Override
    protected Long applySubtract(Long x, Long y) {
        return x - y;
    }

    @Override
    protected Long applyMultiply(Long x, Long y) {
        return x * y;
    }

    @Override
    protected Long applyDivide(Long x, Long y) throws EvaluatingException {
        if (y == 0) throw new DivisionByZeroException("(" + x + ", " + y + ")");
        return x / y;
    }

    @Override
    public Long min(Long x, Long y) {
        return (x < y) ? x : y;
    }

    @Override
    public Long max(Long x, Long y) {
        return (x > y) ? x : y;
    }

    @Override
    public int count(Long x) throws InvalidOperationException {
        return Long.bitCount(x);
    }

    @Override
    public Long parseNumber(String pattern) throws IllegalConstArgument {
        try {
            return Long.parseLong(pattern);
        } catch (NumberFormatException e) {
            throw new IllegalConstArgument(pattern, 0, pattern.length());
        }
    }

    @Override
    public String toString(Long x) {
        return x.toString();
    }

    @Override
    public boolean isNumberCharacter(char c) {
        return Character.isDigit(c);
    }
}
