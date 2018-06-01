package expression;

import exceptions.EvaluatingException;
import exceptions.IllegalConstArgument;
import operations.Operations;

public class Count<T> extends AbstractUnaryOperation<T> {
    public Count(CommonExpression<T> argument, Operations<T> operations) {
        super(argument, operations);
    }

    @Override
    protected T apply(T value) throws EvaluatingException {
        try {
            return new Const<T>(
                    operations.parseNumber(
                            Integer.toString(
                                    operations.count(value))
                    )
            ).evaluate(null, null, null);
        } catch (IllegalConstArgument e) {
            throw new EvaluatingException("Count", "()");
        }
    }
}
