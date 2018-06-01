package tabulator;

import exceptions.EvaluatingException;
import exceptions.IllegalConstArgument;
import exceptions.NoSuchModeException;
import exceptions.ParsingException;
import expression.CommonExpression;
import operations.*;
import parser.ExpressionParser;

import java.util.HashMap;

public class GenericTabulator implements Tabulator {

    private final static HashMap<String, Operations<?>> MODES = new HashMap<>();

    static {
        MODES.put("i", new IntegerOperations(true));
        MODES.put("d", new DoubleOperations());
        MODES.put("bi", new BigIntegerOperations());
        MODES.put("u", new IntegerOperations(false));
        MODES.put("l", new LongOperations());
        MODES.put("s", new ShortOperations());
    }

    private static Operations<?> processOperation(String mode) throws NoSuchModeException {
        Operations<?> answer = MODES.get(mode);
        if (answer == null) {
            throw new NoSuchModeException(mode);
        }
        return answer;
    }

    private static <T> Object[][][] processTable(Operations<T> operations, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception{
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        CommonExpression<T> expressionStructure;
        try {
            expressionStructure = new ExpressionParser<T>().parse(expression, operations);
        } catch (ParsingException e) {
            return table;
        }

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        table[i - x1][j - y1][k - z1] = expressionStructure.evaluate(
                                operations.parseNumber(Integer.toString(i)),
                                operations.parseNumber(Integer.toString(j)),
                                operations.parseNumber(Integer.toString(k))
                        );
                    } catch (EvaluatingException e) {
                        table[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }

        return table;
    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return processTable(processOperation(mode), expression, x1, x2, y1, y2, z1, z2);
    }
}
