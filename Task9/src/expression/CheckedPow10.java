package expression;

public class CheckedPow10 extends CheckedPow {
    public CheckedPow10(CommonExpression argument) {
        super(new Const(10), argument);
    }
}
