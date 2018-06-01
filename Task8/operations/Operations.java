package operations;
import exceptions.*;

public interface Operations<T> {

    T negate(T x) throws OverflowException;

    T add(T x, T y) throws OverflowException;

    T subtract(T x, T y) throws OverflowException;

    T multiply(T x, T y) throws OverflowException;

    T divide(T x, T y) throws EvaluatingException;

    T min(T x, T y );

    T max(T x, T y);

    int count(T x) throws InvalidOperationException;

    T parseNumber(String pattern) throws IllegalConstArgument;

    String toString(T x);

    boolean isNumberCharacter(char c);
}
