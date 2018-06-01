package expression;

public class Variable implements CommonExpression {

    private final String name;

    public Variable(char name) {
        this.name = Character.toString(name);
    }

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int value) {
        return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if ("x".equals(name)) {
            return x;
        } else if ("y".equals(name)) {
            return y;
        } else if ("z".equals(name)) {
            return z;
        } else {
            return 0;
        }
    }
}
