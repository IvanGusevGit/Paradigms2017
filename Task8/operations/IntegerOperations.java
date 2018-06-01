package operations;

import exceptions.DivisionByZeroException;
import exceptions.EvaluatingException;
import exceptions.IllegalConstArgument;

public class IntegerOperations extends AbstractOperations<Integer> {

    public IntegerOperations(boolean checked) {
        super(checked, new IntegerOperationChecker());
    }

    @Override
    protected Integer applyNegate(Integer x) {
        return -x;
    }

    @Override
    protected Integer applyAdd(Integer x, Integer y) {
        return x + y;
    }

    @Override
    protected Integer applySubtract(Integer x, Integer y) {
        return x - y;
    }

    @Override
    protected Integer applyMultiply(Integer x, Integer y) {
        return x * y;
    }

    @Override
    protected Integer applyDivide(Integer x, Integer y) throws EvaluatingException {
        if (y == 0) throw new DivisionByZeroException("(" + x + ", " + y + ")");
        return x / y;
    }

    @Override
    public Integer min(Integer x, Integer y) {
        return (x < y) ? x : y;
    }

    @Override
    public Integer max(Integer x, Integer y) {
        return (x > y) ? x : y;
    }

    @Override
    public int count(Integer x) {
        return Integer.bitCount(x);
    }

    @Override
    public Integer parseNumber(String pattern) throws IllegalConstArgument {
        try {
            return Integer.parseInt(pattern);
        } catch (NumberFormatException e) {
            throw new IllegalConstArgument(pattern, 0, pattern.length());
        }
    }

    @Override
    public String toString(Integer x) {
        return x.toString();
    }

    @Override
    public boolean isNumberCharacter(char c) {
        return Character.isDigit(c);
    }
}
