package expression;

public class CheckedLog10 extends CheckedLog {
    public CheckedLog10(CommonExpression argument) {
        super(argument, new Const(10));
    }
}
