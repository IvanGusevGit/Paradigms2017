package parser;

import exceptions.*;
import expression.*;
import operations.Operations;

import java.util.EnumSet;
import java.util.Set;
import java.util.Stack;


public class ExpressionParser<T> {

    private enum Token {
        EMPTY,
        ADD,
        SUB,
        MUL,
        DIV,
        NEGATIVE,
        OPENING_PARENTHESIS,
        CLOSING_PARENTHESIS,
        CONST,
        VARIABLE,
        END,
        MIN,
        MAX,
        COUNT
    }

    private Set<Token> operationsSet = EnumSet.of(Token.ADD, Token.DIV, Token.SUB, Token.MUL, Token.NEGATIVE, Token.SUB);
    private Set<Token> binaryOperations = EnumSet.of(Token.ADD, Token.DIV, Token.SUB,  Token.MUL);

    private Operations<T> operations;
    private String expression, constValue;
    private char name;
    private Token currentToken, nextToken;
    private int index, currentTokenStart, currentTokenEnd, nextTokenStart, nextTokenEnd;
    private Stack<Integer> openings;

    private void setNextTokenBorders(int left, int right) {
        nextTokenStart = left;
        nextTokenEnd = right;
    }

    private void skipWhitespace() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
    }

    private boolean available(int count) {
        return index + count <= expression.length();
    }

    private char currentCharacter() {
        return expression.charAt(index);
    }

    private int getNumberBorder(int start) {
        while (start < expression.length() && operations.isNumberCharacter(expression.charAt(start))) {
            start++;
        }
        return start;
    }

    private boolean test(String pattern) {
        skipWhitespace();
        if (!available(pattern.length())) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (expression.charAt(index + i) != pattern.charAt(i)) {
                return false;
            }
        }
        index += pattern.length();
        return true;
    }

    private Token processToken() throws ParsingException {
        skipWhitespace();
        if (!available(1)) {
            setNextTokenBorders(index - 1, index - 1);
            return Token.END;
        } else if (test("count")) {
            setNextTokenBorders(index - 5, index - 1);
            return Token.COUNT;
        } else if (test("min")) {
            setNextTokenBorders(index - 3, index - 1);
            return Token.MIN;
        } else if (test("max")) {
            setNextTokenBorders(index - 3, index - 1);
            return Token.MAX;
        } else if (test("+")) {
            setNextTokenBorders(index - 1, index - 1);
            return Token.ADD;
        } else if (test("-")) {
            setNextTokenBorders(index - 1, index - 1);
            if (currentToken == Token.CONST || currentToken == Token.VARIABLE || currentToken == Token.CLOSING_PARENTHESIS) {
                return Token.SUB;
            } else {
                if (Character.isDigit(expression.charAt(index))) {
                    int end = getNumberBorder(index);
                    constValue = "-" + expression.substring(index, end);
                    setNextTokenBorders(index - 1, end - 1);
                    index = end;
                    return Token.CONST;
                }
                return Token.NEGATIVE;
            }
        } else if (test("*")) {
            setNextTokenBorders(index - 1, index - 1);
            return Token.MUL;
        } else if (test("/")) {
            setNextTokenBorders(index - 1, index - 1);
            return Token.DIV;
        } else if (test("(")) {
            setNextTokenBorders(index - 1, index - 1);
            return Token.OPENING_PARENTHESIS;
        } else if (test(")")) {
            setNextTokenBorders(index - 1, index - 1);
            if (openings.size() == 0) {
                throw new ClosingBeforeOpeningParenthesisException(expression, nextTokenStart);
            }
            return Token.CLOSING_PARENTHESIS;
        } else if (test("x") || test("y") || test("z")) {
            setNextTokenBorders(index - 1, index - 1);
            name = expression.charAt(index - 1);
            return Token.VARIABLE;
        } else {
            if (operations.isNumberCharacter(currentCharacter())) {
                int end = getNumberBorder(index);
                constValue = expression.substring(index, end);
                setNextTokenBorders(index, end - 1);
                index = end;
                return Token.CONST;
            } else {
                throw new UnknownOperandException(expression, index, 1);
            }
        }
    }

    private void updateTokens() throws ParsingException {
        currentToken = nextToken;
        currentTokenStart = nextTokenStart;
        currentTokenEnd = nextTokenEnd;
        nextToken = processToken();
        if (operationsSet.contains(currentToken) && (nextToken == Token.END || nextToken == Token.CLOSING_PARENTHESIS)) {
            throw new MissingArgumentException(expression, index - 1);
        }
        if (currentToken == nextToken && currentToken != Token.OPENING_PARENTHESIS && currentToken != Token.CLOSING_PARENTHESIS && currentToken != Token.END && currentToken != Token.NEGATIVE  && currentToken != Token.COUNT) {
            throw new IncorrectZoneException(expression, index - 1, 1);
        }
        if (binaryOperations.contains(currentToken) && binaryOperations.contains(nextToken)) {
            throw new MissingArgumentException(expression, index - 1);
        }
        if ((currentToken == Token.OPENING_PARENTHESIS || currentToken == Token.EMPTY) && binaryOperations.contains(nextToken)) {
            throw new MissingArgumentException(expression, nextTokenStart);
        }
    }

    private CommonExpression<T> buildUnary() throws ParsingException {
        CommonExpression<T> result;
        updateTokens();
        switch (currentToken) {
            case CONST:
                try {
                    result = new Const(operations.parseNumber(constValue));
                } catch (IllegalConstArgument e) {
                    throw new IllegalConstArgument(expression, currentTokenStart, currentTokenEnd - currentTokenStart + 1);
                }
                updateTokens();
                break;
            case VARIABLE:
                result = new Variable(name);
                updateTokens();
                break;
            case NEGATIVE:
                result = new Negative(buildUnary(), operations);
                break;
            case COUNT:
                result = new Count(buildUnary(), operations);
                break;
            case OPENING_PARENTHESIS:
                openings.push(index - 1);
                result = buildMinMax();
                if (currentToken != Token.CLOSING_PARENTHESIS) {
                    throw new MissingClosingParenthesisException(expression, index - 1);
                } else {
                    openings.pop();
                    updateTokens();
                }
                break;
            default:
                int startingPoint = openings.size() == 0 ? 0 : openings.peek();
                throw new IncorrectZoneException(expression, startingPoint, index - startingPoint);
        }
        return result;
    }

    private CommonExpression<T> buildMulDiv() throws ParsingException {
        CommonExpression<T> result = buildUnary();
        while (true) {
            switch (currentToken) {
                case MUL:
                    result = new Multiply(result, buildUnary(),operations);
                    break;
                case DIV:
                    result = new Divide(result, buildUnary(),operations);
                    break;
                default:
                    return result;
            }
        }
    }

    private CommonExpression<T> buildAddSub() throws ParsingException {
        CommonExpression<T> result = buildMulDiv();
        while (true) {
            switch (currentToken) {
                case ADD:
                    result = new Add(result, buildMulDiv(),operations);
                    break;
                case SUB:
                    result = new Subtract(result, buildMulDiv(), operations);
                    break;
                default:
                    return result;
            }
        }
    }

    private CommonExpression<T> buildMinMax() throws ParsingException{
        CommonExpression<T> result = buildAddSub();
        while (true) {
            switch (currentToken) {
                case MIN:
                    result = new Min(result, buildAddSub(), operations);
                    break;
                case MAX:
                    result = new Max(result, buildAddSub(), operations);
                    break;
                default:
                    return result;
            }
        }
    }

    public CommonExpression<T> parse(String expression, Operations<T> operations) throws ParsingException{
        this.operations = operations;
        this.expression = expression;
        currentToken = Token.EMPTY;
        nextToken = Token.END;
        openings = new Stack<>();
        index = 0;
        nextToken = processToken();
        CommonExpression<T> result = buildMinMax();
        if (currentToken == Token.CLOSING_PARENTHESIS) {
            throw new ClosingBeforeOpeningParenthesisException(expression, currentTokenStart);
        }
        return result;
    }
}
