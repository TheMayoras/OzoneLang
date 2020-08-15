/*
 * This file is part of Ozone.
 *
 * Ozone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ozone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ozone.  If not, see <https://www.gnu.org/licenses/>.
 */

package ozonelang.ozone.core.parser;

import ozonelang.ozone.core.ast.node.CodeNode;
import ozonelang.ozone.core.ast.node.RootNode;
import ozonelang.ozone.core.lexer.Context;
import ozonelang.ozone.core.lexer.Lexer;
import ozonelang.ozone.core.lexer.SymbolType;
import ozonelang.ozone.core.lexer.TokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class Parser {
    public static final String EOF = "<EOF>";
    private final TokenStream stream;
    private final Lexer lexer;
    private final ArrayList<Context> contexts = new ArrayList<>();
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
        // For now, just literals.
    }

    @ParsingFunction
    public RootNode parse() {
        ArrayList<CodeNode> nodes = new ArrayList<>();
        while (!stream.isNext(EOF)) {
            if (stream.isNext(SymbolType.VAR)) {
                parseVariable(stream);
            }
            if (stream.isNext(SymbolType.MOD)) {
                //parseModule(stream);
            }
        }
        return RootNode.getInstance(nodes);
    }

    public Lexer getLexer() {
        return lexer;
    }

    public static class ExpressionParser {

        public enum Operator {
            AND("&&"),
            BAND("&"),
            BNEG("~"),
            BOR("|"),
            BSIGNEDL("<<"),
            BSIGNEDR(">>"),
            BUNSIGNEDL("<<<"),
            BUNSIGNEDR(">>>"),
            BXOR("^"),
            DIV("/"),
            EQ("=="),
            GT(">"),
            GTEQ(">="),
            MINUS("-"),
            MOD("%"),
            MUL("*"),
            NOT("!"),
            NOTEQ("!="),
            OR("||"),
            PLUS("+"),
            POW("^^"),
            LT("<"),
            LTEQ("<="),
            STRCOMMA("..."),
            STRSPACE("..");

            private final String op;

            Operator(String literal) {
                this.op = literal;
            }

            public String getOperator() {
                return op;
            }
        }
        private TokenStream stream;

        @ParsingFunction
        public void parseExpression(TokenStream substream) {
            this.stream = substream;

        }
    }
}