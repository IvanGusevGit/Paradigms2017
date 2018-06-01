import expression.parser.ExpressionParser;
import expression.*;

public class Main {
    public static void main(String[] args) {
        TripleExpression tr = (new ExpressionParser()).parse("(count ((count (y)) / (833797735))) ^ ((((-2011552484) ^ (1420378973)) ^ ((y) + (171367215))) & (count (x)))");
        System.out.println(tr.evaluate(0,1,0));
    }
}
