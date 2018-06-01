package expression;

public abstract class AbstractOperation implements Expression {
    Expression leftArgument, rightArgument;

    AbstractOperation(Expression leftArgument, Expression rightArgument) {
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;
    }

    public int evaluate(int value) {
        return calc(value);
    }

    abstract int calc(int value);
}
