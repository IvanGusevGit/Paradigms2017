package operations;

import exceptions.DivisionByZeroException;
import exceptions.EvaluatingException;
import exceptions.IllegalConstArgument;
import exceptions.InvalidOperationException;

import java.math.BigInteger;

public class BigIntegerOperations extends AbstractOperations<BigInteger> {

    public BigIntegerOperations() {
        super(false, null);
    }

    @Override
    protected BigInteger applyNegate(BigInteger x) {
        return x.negate();
    }

    @Override
    protected BigInteger applyAdd(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    protected BigInteger applySubtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    protected BigInteger applyMultiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    protected BigInteger applyDivide(BigInteger x, BigInteger y) throws EvaluatingException{
        if (y.equals(new BigInteger("0"))) throw new DivisionByZeroException('(' + x.toString() + ", " + y.toString() + ')');
        return x.divide(y);
    }

    @Override
    public BigInteger min(BigInteger x, BigInteger y) {
        return x.min(y);
    }

    @Override
    public BigInteger max(BigInteger x, BigInteger y) {
        return x.max(y);
    }

    @Override
    public int count(BigInteger x) throws InvalidOperationException {
        return x.bitCount();
    }

    @Override
    public BigInteger parseNumber(String pattern) throws IllegalConstArgument {
        try {
            return new BigInteger(pattern);
        } catch (NumberFormatException e) {
            throw new IllegalConstArgument(pattern, 0, pattern.length());
        }
    }

    @Override
    public String toString(BigInteger x) {
        return x.toString();
    }

    @Override
    public boolean isNumberCharacter(char c) {
        return Character.isDigit(c);
    }
}
