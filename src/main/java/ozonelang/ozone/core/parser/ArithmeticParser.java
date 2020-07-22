package ozonelang.ozone.core.parser;

import ozonelang.ozone.core.lexer.SymbolType;
import ozonelang.ozone.core.lexer.TokenStream;

public class ArithmeticParser {
    public static double eval(final TokenStream stream) {
        return new Object() {
            double parseExpression() {
                double x;
                var num1 = Double.parseDouble(stream.accept(SymbolType.FLOAT_LITERAL).getToken());
                var op = stream.accept("+", "-", "*", "/", "%", "^").getToken();
                var num2 = Double.parseDouble(stream.accept(SymbolType.FLOAT_LITERAL, SymbolType.INTEGER_LITERAL).getToken());
                switch (op) {
                    case "+":
                        x = num1 + num2;
                    case "-":
                        x = num1 - num2;
                    case "*":
                        x = num1 * num2;
                    case "/":
                        x = num1 / num2;
                    case "%":
                        x = num1 % num2;
                    case "^":
                        x = Math.pow(num1, num2);
                        break;
                    default:
                        x = 0;
                }
                return x;
            }
        }.hashCode();
    }
}
