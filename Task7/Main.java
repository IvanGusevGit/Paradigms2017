import expression.exceptions.ExpressionException;
import expression.TripleExpression;
import expression.exceptions.ExpressionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws ExpressionException, IOException {
        System.out.println(Integer.MAX_VALUE);
        int a = -Integer.MIN_VALUE;
        System.out.println(a);
        ExpressionParser parser = new ExpressionParser();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String exp = in.readLine();
            int x = Integer.parseInt(in.readLine());
            int y = Integer.parseInt(in.readLine());
            int z = Integer.parseInt(in.readLine());
            TripleExpression p = parser.parse(exp);
            System.out.println(p.evaluate(x, y, z));
        }
    }
}
