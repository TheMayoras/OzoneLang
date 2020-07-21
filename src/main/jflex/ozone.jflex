/*
 * Grammar spec for the Ozone Programming language.
 * Author: Valio Valtokari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ozonelang.ozone.core.lexer;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import ozonelang.ozone.core.AST.Token;
import ozonelang.ozone.core.AST.TokenStream;
import ozonelang.ozone.core.AST.ParsingError;
import ozonelang.ozone.core.AST.SymbolType;

%%
%class Lexer
%unicode
%public
%line
%column
%apiprivate
%type Token
%ctorarg String file
%ctorarg String eof
%init{
    if (eof.replace(" ", "").equals("")) {
        this.EOF = null;
    } else {
        this.EOF = eof;
    }
    this.file = file;
%init}
%{
    public final String file;
    private String EOF;

    StringBuilder string = new StringBuilder();

    private Token makeToken(SymbolType sym, Object value, int line, int col) {
        return new Token(file, sym, value, line, col);
    }

    private Token makeToken(SymbolType sym, int line, int col) {
        return new Token(file, sym, line, col);
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return yyline;
    }

    public int getColumn() {
        return yycolumn;
    }

    public List<Token> lex() throws IOException {
        List<Token> tokens = new ArrayList<>();
        while (!yyatEOF()) {
            tokens.add(yylex());
        }
        if (EOF != null) {
            var lastLine = tokens.get(tokens.size() - 1).getLine();
            var lastCol  = tokens.get(tokens.size() - 1).getCol();
            tokens.add(new Token(file, SymbolType.EOF, lastLine, lastCol));
        }
        return tokens;
    }
%}
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
/* EOF without line terminator */
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/+" {CommentContent} "+/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*
DecIntegerLiteral = 0 | [1-9][0-9]*
DoubleLiteral = {DecIntegerLiteral} + "." + {DecIntegerLiteral}
Boolean = "yes" | "no"
%state STRING
%%
/* keywords */
<YYINITIAL> "func"              { return makeToken(SymbolType.FUNC, yyline, yycolumn); }
<YYINITIAL> "bool"              { return makeToken(SymbolType.BOOL, yyline, yycolumn); }
<YYINITIAL> "break"             { return makeToken(SymbolType.BREAK, yyline, yycolumn); }

<YYINITIAL> {
    /* identifiers */
    {Identifier}                   { return makeToken(SymbolType.IDENTIFIER, yytext(), yyline, yycolumn); }

    /* literals */
    {DecIntegerLiteral}            { return makeToken(SymbolType.INTEGER_LITERAL, yytext(), yyline, yycolumn); }
    \"                             { string.setLength(0); yybegin(STRING); }

    /* operators */
    "="                            { return makeToken(SymbolType.ASSIGN, yyline, yycolumn); }
    "=="                           { return makeToken(SymbolType.EQ, yyline, yycolumn); }
    "+"                            { return makeToken(SymbolType.PLUS, yyline, yycolumn); }

    /* comments */
    {Comment}                      { /* ignore */ }

    /* whitespace */
    {WhiteSpace}                   { /* ignore */ }
}
<STRING> {
      \" | '                         { yybegin(YYINITIAL);
                                       return makeToken(SymbolType.STRING_LITERAL,
                                       string.toString(), yyline, yycolumn); }
      [^\n\r\"\\]+                   { string.append(yytext()); }
      \\t                            { string.append('\t'); }
      \\n                            { string.append(System.lineSeparator()); }

      \\r                            { string.append('\r'); }
      \\\" | '                       { string.append('\"'); }
      \\                             { string.append('\\'); }
}

    [^]                              { throw new ParsingError(this.file, yytext(), yyline, yycolumn); }