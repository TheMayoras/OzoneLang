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

import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;

%%
%class Lexer
%unicode
%public
%line
%column
%apiprivate
%type Token
%ctorarg String file
%init{
    this.file = file;
%init}
%{
    public final String file;

    StringBuilder string = new StringBuilder();

    private Token makeToken(SymbolType sym, Object value) {
        return new Token(file, sym, value, getLine(), getColumn());
    }

    private Token makeToken(SymbolType sym) {
        return new Token(file, sym, getLine(), getColumn());
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

    public Token next() throws IOException {
        return yylex();
    }

    public boolean hasNext() {
        return !yyatEOF();
    }

    public List<Token> lex() throws IOException {
        List<Token> tokens = new ArrayList<>();
        while (hasNext()) {
            tokens.add(yylex());
        }
        return tokens;
    }

    private enum CommentType {
        DOC,
        EOL,
        BLOCK,
    }

    private CommentType commentType;

    private CommentType getCommentType() {
        switch (yytext()) {
            case "/+":
                return CommentType.DOC;
            case "/*":
                return CommentType.BLOCK;
            default:
                return CommentType.EOL;
        }
    }

    private Token lexBool() {
        switch (yytext()) {
            case "yes":
                return makeToken(SymbolType.YES);
            case "no":
                return makeToken(SymbolType.NO);
            case "nothing":
                return makeToken(SymbolType.NOTHING);
        }
        return new Token("", SymbolType.NOTHING, 0, 0);
    }
%}
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

Comment = {TraditionalComment} | {DocumentationComment} | {EndOfLineComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
/* EOF without line terminator */
/* '#!' hashbang for unix systems */
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}? | "#!" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/+" {CommentContent} "+/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Boolean = "yes" | "no" | "nothing"
DecIntegerLiteral = 0 | [1-9][0-9]*
FloatLiteral = {DecIntegerLiteral} + "." + {DecIntegerLiteral}
LongLiteral = {DecIntegerLiteral} + ( "l" | "L" )
ShortLiteral = {DecIntegerLiteral} + ( "s" | "S" )
Identifier =  [_a-zåäöA-ZÅÄÖ]+[_a-zåäöA-ZÅÄÖ0-9]*
%state STRING
%%
/* keywords */
<YYINITIAL> "func"              { return makeToken(SymbolType.FUNC); }
<YYINITIAL> "bool"              { return makeToken(SymbolType.BOOL); }
<YYINITIAL> "break"             { return makeToken(SymbolType.BREAK); }
<YYINITIAL> "if"                { return makeToken(SymbolType.IF); }
<YYINITIAL> "else"              { return makeToken(SymbolType.ELSE); }
<YYINITIAL> "elif"              { return makeToken(SymbolType.ELIF); }
<YYINITIAL> "when"              { return makeToken(SymbolType.WHEN); }
<YYINITIAL> "is"                { return makeToken(SymbolType.IS); }
<YYINITIAL> "use"               { return makeToken(SymbolType.USE); }
<YYINITIAL> "mod"               { return makeToken(SymbolType.MOD); }

<YYINITIAL> {
    /* longs, shorts and floats */
    {LongLiteral}                  { return makeToken(SymbolType.LONG_LITERAL, yytext()); }
    {ShortLiteral}                 { return makeToken(SymbolType.SHORT_LITERAL, yytext()); }
    {FloatLiteral}                 { return makeToken(SymbolType.FLOAT_LITERAL, yytext()); }
    /* booleans (and `nothing`) */
    {Boolean}                      { return lexBool(); }

    /* identifiers */
    {Identifier}                   { return makeToken(SymbolType.IDENTIFIER, yytext()); }

    /* literals */
    {DecIntegerLiteral}            { return makeToken(SymbolType.INTEGER_LITERAL, yytext()); }
    \" | '                         { string.setLength(0); yybegin(STRING); }

    /* operators */
    "=="                           { return makeToken(SymbolType.EQ); }
    "!"                            { return makeToken(SymbolType.NOT); }
    "!="                           { return makeToken(SymbolType.NOTEQ); }
    "="                            { return makeToken(SymbolType.ASSIGN); }
    "+"                            { return makeToken(SymbolType.PLUS); }
    "-"                            { return makeToken(SymbolType.MINUS); }
    "/"                            { return makeToken(SymbolType.DIV); }
    "*"                            { return makeToken(SymbolType.MUL); }
    "^"                            { return makeToken(SymbolType.POW); }
    "$"                            { return makeToken(SymbolType.VAR); }
    "..."                          { return makeToken(SymbolType.CONCAT_COMMA); }
    ".."                           { return makeToken(SymbolType.CONCAT_SPACE); }
    "."                            { return makeToken(SymbolType.DOT); }
    ":"                            { return makeToken(SymbolType.COLON); }
    "::"                           { return makeToken(SymbolType.MODULE_SEPARATOR); }
    "@"                            { return makeToken(SymbolType.AT); }
    "#"                            { return makeToken(SymbolType.HASHTAG); }
    ")"                            { return makeToken(SymbolType.RPAREN); }
    "("                            { return makeToken(SymbolType.LPAREN); }
    "]"                            { return makeToken(SymbolType.SQ_BRACET_R); }
    "["                            { return makeToken(SymbolType.SQ_BRACET_L); }
    "}"                            { return makeToken(SymbolType.RBRACE); }
    "{"                            { return makeToken(SymbolType.LBRACE); }


    /* comments */
    {Comment}                      { /* ignore safely */ }

    /* whitespace */
    {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
      \" | '                         { yybegin(YYINITIAL); return makeToken(SymbolType.STRING_LITERAL, string.toString()); }
      [^\n\r[\"|\']\\]+              { string.append( yytext() ); }
      \\t                            { string.append('\t'); }
      \\n                            { string.append('\n'); }

      \\r                            { string.append('\r'); }
      \\\"                           { string.append('\"'); }
      \\\'                           { string.append('\''); }
      \\                             { string.append('\\'); }
      <<EOF>>                        { throw new ParsingError("unexpected EOF in middle of a string literal",
                                        this.file, yytext(), getLine(), getColumn()); }
}

[^]                              { throw new ParsingError(this.file, yytext(), getLine(), getColumn()); }