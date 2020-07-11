package ozonelang.ozone.core.parser;

import org.kaivos.nept.parser.TokenList;

//import static ozonelang.ozone.core.parser.Parser.isNext;
//import static ozonelang.ozone.core.parser.Parser.parseIdentifier;

public class ExpressionParser {
    /*public static String evalStringExpression(final TokenList tl) {
        var current = new StringBuilder();
        var collective = new StringBuilder();
        tl.accept("\"");
        current.append(tl.seekString());
        tl.accept("\"");
        collective.append(current.toString());
        current.setLength(0);
        switch (tl.seekString()) {
            case "..":
                tl.accept("\"");
                collective.append(" ").append(tl.seekString());
                tl.accept("\"");
            case "...":
                tl.accept("\"");
                collective.append(", ").append(tl.seekString());
                tl.accept("\"");
            case "+":
                tl.accept("\"");
                collective.append(tl.seekString());
                tl.accept("\"");
            default:
                //throw new
        }
    }*/
    public static double evalNumberExpression(final String expression) {
        return new Object() {
            int position = -1;
            int ch;
            void nextChar() {
                if (++position < expression.length()) {
                    ch = expression.charAt(position);
                } else {
                    ch = -1;
                }
            }

            boolean eat(int charToEat) {
                while (ch == ' ')
                    nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (position < expression.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }
            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+'))
                        x += parseTerm();
                    else if (eat('-'))
                        x -= parseTerm();
                    else
                        return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*'))
                        x *= parseFactor();
                    else if (eat('/'))
                        x /= parseFactor();
                    else
                        return x;
                }
            }

            double parseFactor() {
                if (eat('+'))
                    return parseFactor();
                if (eat('-'))
                    return -parseFactor();

                double x;
                int startPos = this.position;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.position));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                if (eat('^'))
                    x = Math.pow(x, parseFactor());
                return x;
            }
        }.parse();
    }
}
