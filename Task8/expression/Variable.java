package expression;

import exceptions.EvaluatingException;

public class Variable<T> implements CommonExpression<T> {
    private final char name;

    public Variable(char name) {
        this.name = name;
    }

    public Variable(String name) {
        this.name = name.charAt(0);
    }

    @Override
    public T evaluate(T x, T y, T z) throws EvaluatingException {
        switch (name) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
            default:
                return null;
        }
    }
}
