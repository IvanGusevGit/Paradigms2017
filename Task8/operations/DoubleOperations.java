package operations;

import exceptions.IllegalConstArgument;
import exceptions.InvalidOperationException;

public class DoubleOperations extends AbstractOperations<Double>{

    public DoubleOperations() {
        super(false, null);
    }

    @Override
    protected Double applyNegate(Double x) {
        return -x;
    }

    @Override
    protected Double applyAdd(Double x, Double y) {
        return x + y;
    }

    @Override
    protected Double applySubtract(Double x, Double y) {
        return x - y;
    }

    @Override
    protected Double applyMultiply(Double x, Double y) {
        return x * y;
    }

    @Override
    protected Double applyDivide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double min(Double x, Double y) {
        return (x < y) ? x : y;
    }

    @Override
    public Double max(Double x, Double y) {
        return (x > y) ? x : y;
    }

    @Override
    public int count(Double x) throws InvalidOperationException {
        return Long.bitCount(Double.doubleToLongBits(x));
    }

    @Override
    public Double parseNumber(String pattern) throws IllegalConstArgument {
        try {
            return Double.parseDouble(pattern);
        } catch (NumberFormatException e) {
            throw new IllegalConstArgument(pattern, 0, pattern.length());
        }
    }

    @Override
    public String toString(Double x) {
        return x.toString();
    }

    @Override
    public boolean isNumberCharacter(char c) {
        c = Character.toLowerCase(c);
        return Character.isDigit(c) || (c == '.') || (c == 'e') || (c == 'd');
    }
}
