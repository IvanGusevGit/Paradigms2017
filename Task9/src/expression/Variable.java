package expression;

import expression.exceptions.EvaluatingException;

public class Variable implements CommonExpression {
    private final char name;

    public Variable(char name) {
        this.name = name;
    }

    public Variable(String name) {
        this.name = name.charAt(0);
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) throws EvaluatingException {
        switch (name) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
            default:
                return 0;
        }
    }
}
