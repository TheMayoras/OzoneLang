package ozonelang.ozone.core.parser;

import ozonelang.ozone.core.lexer.Lexer;
import ozonelang.ozone.core.lexer.SymbolType;
import ozonelang.ozone.core.lexer.TokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Parser {
    private final TokenStream stream;
    private final Lexer lexer;
    public static final String EOF = "<EOF>";

    public Parser(InputStream in, String filename) throws IOException {
        lexer = new Lexer(new InputStreamReader(in), filename);
        stream = TokenStream.open(lexer);
        stream.addEOFMark(EOF);
    }

    public Parser(File file) throws IOException {
        this(new FileInputStream(file), file.getName());
    }

    @ParsingFunction(parent = "parse", expression = "$r = 5")
    public static void parseVariable(TokenStream stream) {
        // TODO: Implement everything
    }

    @ParsingFunction
    public void parse() {
        while (!stream.isNext(EOF)) {
            if (stream.isNext(SymbolType.VAR)) {
                parseVariable(stream);
            }
            if (stream.isNext(SymbolType.MOD)) {
                //parseModule(stream);
            }
        }
    }

    public Lexer getLexer() {
        return lexer;
    }
}