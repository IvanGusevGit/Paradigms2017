package operations;

import exceptions.EvaluatingException;
import exceptions.OverflowException;

public abstract class AbstractOperations<T> implements Operations<T> {

    private boolean checked;
    private OperationChecker<T> checker;

    public AbstractOperations(boolean mode, OperationChecker<T> checker) {
        checked = mode;
        this.checker = checker;
    }

    @Override
    public T negate(T x) throws OverflowException {
        if (checked) {
            checker.checkNegate(x);
        }
        return applyNegate(x);
    }

    @Override
    public T add(T x, T y) throws OverflowException {
        if (checked) {
            checker.checkAdd(x, y);
        }
        return applyAdd(x, y);
    }

    @Override
    public T subtract(T x, T y) throws OverflowException {
        if (checked) {
            checker.checkSubtract(x, y);
        }
        return applySubtract(x, y);
    }

    @Override
    public T multiply(T x, T y) throws OverflowException {
        if (checked) {
            checker.checkMultiply(x, y);
        }
        return applyMultiply(x, y);
    }

    @Override
    public T divide(T x, T y) throws EvaluatingException {
        if (checked) {
            checker.checkDivide(x, y);
        }
        return applyDivide(x, y);
    }

    protected abstract T applyNegate(T x) throws OverflowException;

    protected abstract T applyAdd(T x, T y) throws OverflowException;

    protected abstract T applySubtract(T x, T y) throws OverflowException;

    protected abstract T applyMultiply(T x, T y) throws OverflowException;

    protected abstract T applyDivide(T x, T y) throws EvaluatingException;
}
