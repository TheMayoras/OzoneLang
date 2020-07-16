package ozonelang.ozone.core.parser;

import org.kaivos.nept.parser.ParsingException;
import org.kaivos.nept.parser.Token;
import org.kaivos.nept.parser.TokenList;
import org.kaivos.nept.parser.TokenScanner;
import ozonelang.ozone.core.AST.Context;
import ozonelang.ozone.core.AST.node.ModuleStmtNode;
import ozonelang.ozone.core.AST.node.RootNode;
import ozonelang.ozone.core.AST.node.UseStmtNode;

import java.util.Arrays;

import static org.kaivos.nept.parser.TokenList.expected;

public final class Parser {
    private enum ModuleState {
        IDENTIFIER,
        SEPARATOR,
    }
    private final TokenScanner tokenScanner;
    private Context currentPos;
    private RootNode root;
    private final static String EOF = "<EOF>";
    public Parser() {
        tokenScanner = new TokenScanner()
                .addCommentRule("/*", "*/")
                .addCommentRule("//", System.lineSeparator())
                .addEscapeCode('\n', System.lineSeparator())
                .addOperators("*-.,:;*/%&$()[]{}+_><")
                .addOperatorRule(":=")
                .addOperatorRule("::")
                .addOperatorRule("..")
                .addOperatorRule("...")
                .addStringRule("'", "'", '\\')
                .addStringRule('"', '"', '\\')
                .appendOnEOF("<EOF>");
    }

    @ParsingFunction(parent = "parse", expression = "$r = 4")
    private void parseVariable(TokenList tokens) throws Exception {
        if (isNext(tokens, "$"))
            tokens.shift();
        Token varName = tokens.next();
        if (!validIdentifier(varName.getToken()))
            throw new Exception(String.format("File '%s' at line %s: '%s' is not a valid identifier name",
                    varName.getFile(), varName.getLine(), varName.getToken()));
        tokens.accept("=");
        if (isNext(tokens, "\"") || isNext(tokens, "'"))
            tokens.shift();
        tokens.shift();
    }
    @ParsingFunction(parent = "parse", expression = "mod std::regex;")
    private ModuleStmtNode parseModuleStmt(TokenList tokens) {
        if (isNext(tokens, "mod"))
            tokens.shift();
        var mod = parseModule(tokens);
        return new ModuleStmtNode(currentPos, root, mod);
    }

    @ParsingFunction(parent = "parse", expression = "use std::regex;")
    private UseStmtNode parseUseStmt(TokenList tokens) {
        if (isNext(tokens, "use"))
            tokens.shift();
        var mod = parseModule(tokens);
        return new UseStmtNode(currentPos, root, mod);
    }

    @ParsingFunction(parent = "parseModuleStmt, parseUseStmt", expression = "std::regex")
    private String parseModule(TokenList tokens) {
        if (tokens.isNext("::"))
            throw new ParsingException(expected("identifier"), tokens.seek());
        ModuleState state = ModuleState.IDENTIFIER;
        StringBuilder builder = new StringBuilder();
        String id;
        while (!tokens.isNext(";", "<EOF>")) {
            if (state == ModuleState.IDENTIFIER) {
                id = tokens.seekString();
                builder.append(id);
                tokens.shift();
                state = ModuleState.SEPARATOR;
                if (tokens.isNext("::") && tokens.seekString(1).equals(";"))
                    throw new ParsingException(expected(";"), tokens.seek());
            } else {
                tokens.accept("::");
                builder.append("::");
                state = ModuleState.IDENTIFIER;
            }
        }
        return builder.toString();
    }

    @ParsingFunction
    public void parse(String source, String filename) {
        TokenList tokens = tokenScanner.tokenize(source, filename);
        currentPos = new Context(filename, source, 1, source.split("\n").length);
        root = new RootNode(currentPos);
        while (!isNext(tokens, EOF)) {
            if (isNext(tokens, "mod"))
                root.addChild(parseModuleStmt(tokens));
            if (isNext(tokens, "use"))
                ;
        }
    }
    public static Token acceptIdentifier(TokenList tl) {
        if (!validIdentifier(tl.seekString()))
            throw new ParsingException(expected("identifier"), tl.seek());
        return tl.next();
    }
    private static boolean validIdentifier(String applicant) {
        if (applicant == null || applicant.strip().equals(""))
            return false;
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
            case "mod":
            case "use":
            case "obj":
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
    private static void parseAnnotation(TokenList tl) {
        if (tl.isNext("#"))
            tl.shift();
        tl.accept("[");
        acceptIdentifier(tl);
        if (tl.acceptIfNext("(")) {

        }
    }
}
