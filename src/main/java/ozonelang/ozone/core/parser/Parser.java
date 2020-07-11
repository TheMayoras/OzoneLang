package ozonelang.ozone.core.parser;

import org.kaivos.nept.parser.ParsingException;
import org.kaivos.nept.parser.Token;
import org.kaivos.nept.parser.TokenList;
import org.kaivos.nept.parser.TokenScanner;

import java.util.Arrays;

public final class Parser {
    private final String endOfLineIdentifier = ";";
    private final TokenScanner tokenScanner;
    private TokenList tokens;
    private final static String EOF = "<EOF>";
    public Parser() {
        tokenScanner = new TokenScanner()
                .addCommentRule("/*", "*/")
                .addCommentRule("//", System.lineSeparator())
                .addEscapeCode('\n', System.lineSeparator())
                .addOperators("*-.,:;*/%&$()[]{}+_><")
                .addOperatorRule(":=")
                .addStringRule("'", "'", '\\')
                .addStringRule('"', '"', '\\')
                .appendOnEOF("<EOF>");
    }
    private void parseVariable(TokenList tokens) throws Exception {
        if (tokens.isNext("$"))
            tokens.shift();
        Token varName = tokens.next();
        if (!validIdentifier(varName.getToken()))
            throw new Exception(String.format("File '%s' at line %s: '%s' is not a valid identifier name",
                    varName.getFile(), varName.getLine(), varName.getToken()));
        tokens.accept("=");
        if (tokens.isNext("\"") || tokens.isNext("'"))
            tokens.shift();
        tokens.shift();
    }
    public void parse(String source, String filename) throws Exception {
        tokens = tokenScanner.tokenize(source, filename);
        System.out.println(tokens);
        while (!isNext(tokens, EOF)) {
            if (tokens.isNext("$")) parseVariable(tokens);
        }
    }
    private Token acceptIdentifier(TokenList tl) {
        if (!validIdentifier(tl.seekString()))
            throw new ParsingException(TokenList.expected("identifier"), tl.seek());
        return tl.seek();
    }
    private boolean validIdentifier(String applicant) {
        String numberRe = "[0-9]+";
        /*
         * It's an integer, a long, a float, or a short.
         */
        if (applicant.matches(numberRe)
                || applicant.matches(numberRe + "S")
                || applicant.matches(numberRe + "L")
                || applicant.matches(numberRe + "." + numberRe))
            return false;
        /*
         * Other invalid characters.
         */
        if (!applicant.matches("^[a-zA-Z0-9_]+$"))
            return false;
        switch (applicant) {
            case "if":
            case "else":
            case "whl":
            case "for":
            case "fore":
            case "func":
            case "struct":
            case "unless":
            case "until":
            case "ret":
            case "int":
            case "short":
            case "long":
            case "float":
            case "string":
            case "bool":
            case "nothing":
            case "yes":
            case "no":
                return false;
            default:
                return true;
        }
    }
    public static boolean isNext(TokenList tokens, String... keyword) {
        try {
            return Arrays.asList(keyword).contains(tokens.seek().getToken());
        } catch (IndexOutOfBoundsException e) {}
        return false;
    }
    @ParsingFunction(parent = "parse", expression = "#[annotation]")
    private void parseAnnotation(TokenList tl) {
        if (tl.isNext("#"))
            tl.shift();
        tl.accept("[");
        acceptIdentifier(tl);
        if (tl.acceptIfNext("(")) {

        }
    }
    /*@ParsingFunction(parent = "parseAnnotation", expression = "(5, 7.34)")
    private void parseArgumentQueue(TokenList tl) {
        if (tl.isNext("("))
            tl.shift();
        Token arg;
        while (!tl.isNext(")")) {
            arg = acceptIdentifier(tl);
            if (tl.acceptIfNext(":="))
                tl.shift();
            tl.acceptIfNext(",");
        }
    }*/
}
