package expression;

import exceptions.EvaluatingException;

public interface CommonExpression<T> {
    T evaluate(T x, T y, T z) throws EvaluatingException;
}
