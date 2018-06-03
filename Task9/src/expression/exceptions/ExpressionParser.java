package expression.exceptions;

import expression.*;
import expression.parser.Parser;

import java.util.EnumSet;
import java.util.Set;
import java.util.Stack;


public class ExpressionParser implements expression.exceptions.Parser {

    private enum Token {
        EMPTY,
        ADD,
        SUB,
        MUL,
        DIV,
        NEGATIVE,
        LOG,
        LOG10,
        POW,
        POW10,
        OPENING_PARENTHESIS,
        CLOSING_PARENTHESIS,
        CONST,
        VARIABLE,
        END
    }

    private Set<Token> operations = EnumSet.of(Token.ADD, Token.DIV, Token.SUB, Token.LOG, Token.LOG10, Token.MUL, Token.NEGATIVE, Token.POW, Token.POW10, Token.SUB);
    private Set<Token> binaryOperations = EnumSet.of(Token.ADD, Token.DIV, Token.SUB, Token.POW, Token.MUL, Token.LOG);

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
        while (start < expression.length() && Character.isDigit(expression.charAt(start))) {
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
        } if (test("**")) {
            setNextTokenBorders(index - 2, index - 1);
            return Token.POW;
        } else if (test("//")) {
            setNextTokenBorders(index - 2, index - 1);
            return Token.LOG;
        } else if (test("log10")) {
            setNextTokenBorders(index - 5, index - 1);
            return Token.LOG10;
        } else if (test("pow10")) {
            setNextTokenBorders(index - 5, index - 1);
            return Token.POW10;
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
            if (Character.isDigit(currentCharacter())) {
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
        if (operations.contains(currentToken) && (nextToken == Token.END || nextToken == Token.CLOSING_PARENTHESIS)) {
            throw new MissingArgumentException(expression, index - 1);
        }
        if (currentToken == nextToken && currentToken != Token.OPENING_PARENTHESIS && currentToken != Token.CLOSING_PARENTHESIS && currentToken != Token.END && currentToken != Token.NEGATIVE && currentToken != Token.LOG10 && currentToken != Token.POW10) {
            throw new IncrorrectZoneException(expression, index - 1, 1);
        }
        if (binaryOperations.contains(currentToken) && binaryOperations.contains(nextToken)) {
            throw new MissingArgumentException(expression, index - 1);
        }
        if ((currentToken == Token.OPENING_PARENTHESIS || currentToken == Token.EMPTY) && binaryOperations.contains(nextToken)) {
            throw new MissingArgumentException(expression, nextTokenStart);
        }
    }

    private CommonExpression buildUnary() throws ParsingException {
        CommonExpression result;
        updateTokens();
        switch (currentToken) {
            case CONST:
                try {
                    result = new Const(Integer.parseInt(constValue));
                } catch (NumberFormatException e) {
                    throw new IllegalConstArgument(expression, currentTokenStart, currentTokenEnd - currentTokenStart + 1);
                }
                updateTokens();
                break;
            case VARIABLE:
                result = new Variable(name);
                updateTokens();
                break;
            case NEGATIVE:
                result = new Negative(buildUnary());
                break;
            case LOG10:
                result = new CheckedLog10(buildUnary());
                break;
            case POW10:
                result = new CheckedPow10(buildUnary());
                break;
            case OPENING_PARENTHESIS:
                openings.push(index - 1);
                result = buildAddSub();
                if (currentToken != Token.CLOSING_PARENTHESIS) {
                    throw new MissingClosingParenthesisException(expression, index - 1);
                } else {
                    openings.pop();
                    updateTokens();
                }
                break;
            default:
                int startingPoint = openings.size() == 0 ? 0 : openings.peek();
                throw new IncrorrectZoneException(expression, startingPoint, index - startingPoint);
        }
        return result;
    }

    private CommonExpression buildLogPow() throws ParsingException {
        CommonExpression result = buildUnary();
        while (true) {
            switch (currentToken) {
                case LOG:
                    result = new CheckedLog(result, buildUnary());
                    break;
                case POW:
                    result = new CheckedPow(result, buildUnary());
                    break;
                default:
                    return result;
            }
        }
    }

    private CommonExpression buildMulDiv() throws ParsingException {
        CommonExpression result = buildLogPow();
        while (true) {
            switch (currentToken) {
                case MUL:
                    result = new CheckedMultiply(result, buildLogPow());
                    break;
                case DIV:
                    result = new CheckedDivide(result, buildLogPow());
                    break;
                default:
                    return result;
            }
        }
    }

    private CommonExpression buildAddSub() throws ParsingException {
        CommonExpression result = buildMulDiv();
        while (true) {
            switch (currentToken) {
                case ADD:
                    result = new CheckedAdd(result, buildMulDiv());
                    break;
                case SUB:
                    result = new CheckedSubtract(result, buildMulDiv());
                    break;
                default:
                    return result;
            }
        }
    }

    @Override
    public CommonExpression parse(String expression) throws ParsingException{
        this.expression = expression;
        currentToken = Token.EMPTY;
        nextToken = Token.END;
        openings = new Stack<>();
        index = 0;
        nextToken = processToken();
        CommonExpression result = buildAddSub();
        if (currentToken == Token.CLOSING_PARENTHESIS) {
            throw new ClosingBeforeOpeningParenthesisException(expression, currentTokenStart);
        }
        return result;
    }
}
