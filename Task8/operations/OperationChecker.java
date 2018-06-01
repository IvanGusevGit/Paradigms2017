package operations;

import exceptions.*;

public interface OperationChecker<T> {

    void checkNegate(T value) throws OverflowException;

    void checkAdd(T leftValue, T rightValue) throws OverflowException;

    void checkSubtract(T leftValue, T rightValue) throws OverflowException;

    void checkMultiply(T leftValue, T rightValue) throws OverflowException;

    void checkDivide(T leftValue, T rightValue) throws EvaluatingException;

    String toStringType(T x);

    default String getStringOfArguments(T ... arguments) {
        StringBuilder result = new StringBuilder("(");
        for (int i = 0; i < arguments.length; i++) {
            result.append(toStringType(arguments[i]));
        }
        result.append(")");
        return result.toString();
    }
}
