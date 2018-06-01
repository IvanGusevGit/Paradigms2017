package expression.parser;

import expression.*;
import parser.Parser;

public class ExpressionParser implements Parser {

    private enum Token {
        EMPTY,
        END,
        NUMBER,
        VARIABLE,
        NEG,
        NOT,
        COUNT,
        XOR,
        OR,
        AND,
        ADD,
        SUB,
        DIV,
        MUL,
        OPENING_PARENTHESIS,
        CLOSING_PARENTHESIS
    }

    private String expression;
    private int index = 0;

    private Token currentToken = Token.EMPTY;
    private int value;
    private char name;

    private Character getNextCharacterIgnoreWhiteSpaces() {
        while ((index < expression.length()) && (Character.isWhitespace(expression.charAt(index)))) {
            index++;
        }
        if (index < expression.length()) {
            return expression.charAt(index);
        } else {
            currentToken = Token.END;
            return null;
        }
    }

    private Character getNextCharacter() {
        if (index < expression.length()) {
            return expression.charAt(index);
        } else {
            currentToken = Token.END;
            return null;
        }
    }

    private void nextToken() {
        Character currentCharacter = getNextCharacterIgnoreWhiteSpaces();
        if (currentToken.equals(Token.END)) {
            return;
        }

        switch (currentCharacter) {
            case '|':
                currentToken = Token.OR;
                break;
            case '^':
                currentToken = Token.XOR;
                break;
            case '&':
                currentToken = Token.AND;
                break;
            case '*':
                currentToken = Token.MUL;
                break;
            case '/':
                currentToken = Token.DIV;
                break;
            case '~':
                currentToken = Token.NOT;
                break;
            case '(':
                currentToken = Token.OPENING_PARENTHESIS;
                break;
            case ')':
                currentToken = Token.CLOSING_PARENTHESIS;
                break;
            case '-':
                if (currentToken.equals(Token.CLOSING_PARENTHESIS)
                        || currentToken.equals(Token.VARIABLE)
                        || currentToken.equals(Token.NUMBER)) {
                    currentToken = Token.SUB;
                } else {
                    currentToken = Token.NEG;
                }
                break;
            case '+':
                currentToken = Token.ADD;
                break;
            default:
                if (Character.isDigit(currentCharacter)) {
                    int leftBorder = index;
                    index++;
                    currentCharacter = getNextCharacter();
                    while ((currentCharacter != null) && (Character.isDigit(currentCharacter))) {
                        index++;
                        currentCharacter = getNextCharacter();
                    }
                    int rightBorder = index;
                    value = Integer.parseUnsignedInt(expression.substring(leftBorder, rightBorder));
                    index--;
                    currentToken = Token.NUMBER;
                } else if (currentCharacter == 'x' || currentCharacter == 'y' || currentCharacter == 'z') {
                    name = currentCharacter;
                    currentToken = Token.VARIABLE;

                } else if (index + 5 <= expression.length() && "count".equals(expression.substring(index, index + 5))) {
                    index += 4;
                    currentToken = Token.COUNT;
                }
        }
        index++;
    }

    private CommonExpression buildUnary() {
        nextToken();
        CommonExpression result;
        switch (currentToken) {
            case VARIABLE:
                result = new Variable(name);
                nextToken();
                break;
            case NEG:
                result = new Negative(buildUnary());
                break;
            case NOT:
                result = new Not(buildUnary());
                break;
            case NUMBER:
                result = new Const(value);
                nextToken();
                break;
            case COUNT:
                result = new Count(buildUnary());
                break;
            case OPENING_PARENTHESIS:
                result = descent();
                nextToken();
                break;
            default:
                return new Const(0);
        }
        return result;
    }

    private CommonExpression buildMulDiv() {
        CommonExpression result = buildUnary();
        do {
            switch (currentToken) {
                case DIV:
                    result = new Divide(result, buildUnary());
                    break;
                case MUL:
                    result = new Multiply(result, buildUnary());
                    break;
                default:
                    return result;
            }
        } while (true);
    }

    private CommonExpression buildAddSub() {
        CommonExpression result = buildMulDiv();
        do {
            switch (currentToken) {
                case ADD:
                    result = new Add(result, buildMulDiv());
                    break;
                case SUB:
                    result = new Subtract(result, buildMulDiv());
                    break;
                default:
                    return result;
            }
        } while (true);
    }

    private CommonExpression buildAnd() {
        CommonExpression result = buildAddSub();
        do {
            if (currentToken.equals(Token.AND)) {
                result = new And(result, buildAddSub());
            } else {
                return result;
            }
        } while (true);
    }

    private CommonExpression buildXor() {
        CommonExpression result = buildAnd();
        do {
            if (currentToken.equals(Token.XOR)) {
                result = new Xor(result, buildAnd());
            } else {
                return result;
            }
        } while (true);
    }

    private CommonExpression buildOr() {
        CommonExpression result = buildXor();
        do {
            if (currentToken.equals(Token.OR)) {
                result = new Or(result, buildXor());
            } else {
                return result;
            }
        } while (true);
    }

    private CommonExpression descent() {
        return buildOr();
    }

    @Override
    public CommonExpression parse(String source) {
        index = 0;
        currentToken = Token.EMPTY;
        expression = source;
        return descent();
    }
}
