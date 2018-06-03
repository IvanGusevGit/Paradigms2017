package expression.parser;

import expression.exceptions.ParsingException;
import expression.CommonExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    CommonExpression parse(String expression) throws ParsingException;
}
