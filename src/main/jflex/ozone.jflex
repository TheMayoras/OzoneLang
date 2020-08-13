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
package ozonelang.ozone.core.lexer;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.Reader;

import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;
%%
%class Lexer
%unicode
%public
%line
%column
%apiprivate
%type Token
%ctorarg String file
%ctorarg boolean doc
%init{
    this.file = file;
    this.doc = doc;
%init}
%{
    public final String file;
    public final boolean doc;

    public Lexer(Reader in, String filename) {
        this(in, filename, false);
    }

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

    private int stringStart;
%}
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
/* EOF without line terminator */
/* '#!' hashbang for unix systems */
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}? | "#!" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/+" {CommentContent} "+/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Boolean = "yes" | "no" | "nothing"
DecIntegerLiteral = 0 | [1-9][0-9]*
ByteBody = [0-9A-Fa-f]{1,8}
ByteLiteral = "0x"{ByteBody}
FloatLiteral = {DecIntegerLiteral}? + "." + {DecIntegerLiteral}
LongLiteral = {DecIntegerLiteral} + ( "l" | "L" )
ShortLiteral = {DecIntegerLiteral} + ( "s" | "S" )
Identifier =  [_a-zåäöA-ZÅÄÖ]+[_a-zåäöA-ZÅÄÖ0-9]*
%state STRING
%state SINGLE_STRING
%state TEMPLATE_STRING
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
<YYINITIAL> "in"                { return makeToken(SymbolType.IN); }
<YYINITIAL> "use"               { return makeToken(SymbolType.USE); }
<YYINITIAL> "mod"               { return makeToken(SymbolType.MOD); }
<YYINITIAL> "error"             { return makeToken(SymbolType.ERROR); }

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
    {ByteLiteral}                  { return makeToken(SymbolType.BYTE_LITERAL, yytext().replace("0x", "")); }
    {DecIntegerLiteral}            { return makeToken(SymbolType.INTEGER_LITERAL, yytext()); }
    \"                             { string.setLength(0); stringStart = yycolumn; yybegin(STRING); }
    '                              { string.setLength(0); yybegin(SINGLE_STRING); }
    `                              { string.setLength(0); yybegin(TEMPLATE_STRING); }

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
    ","                            { return makeToken(SymbolType.COMMA); }


    /* comments */
    {Comment}                      { /* ignore safely */ }
    {DocumentationComment}         { if (doc) return makeToken(SymbolType.DOC_COMMENT, yytext()); }

    /* whitespace */
    {WhiteSpace}                   { /* ignore */ }
}

<STRING> {
      \"                             { yybegin(YYINITIAL); return makeToken(SymbolType.STRING_LITERAL, string.toString()); }
      [^\n\r\"\\]+                   { string.append( yytext() ); yycolumn += yytext().length(); }
      \\t                            { string.append('\t'); yycolumn++; }
      \\n                            { string.append('\n'); yyline++; yycolumn = 0; }

      \\r                            { string.append('\r'); yycolumn = 0; }
      \\\"                           { string.append('\"'); yycolumn++; }
      \\                             { string.append('\\'); yycolumn++; }
      <<EOF>>                        { raiseEx(new ParsingError("unexpected EOF in middle of a string literal",
                                        this.file, yytext(), getLine(), getColumn()), true, new Context(file,
                                        getLine(), getLine(), getColumn(), getColumn())); }
}

<SINGLE_STRING> {
      '                              { yybegin(YYINITIAL); return makeToken(SymbolType.STRING_LITERAL, string.toString()); }
      [^\n\r\'\\]+                   { string.append( yytext() ); yycolumn += yytext().length(); }
      \\t                            { string.append('\t'); yycolumn++; }
      \\n                            { string.append('\n'); yyline++; yycolumn = 0;}

      \\r                            { string.append('\r'); yycolumn = 0; }
      \\\'                           { string.append('\''); yycolumn++; }
      \\                             { string.append('\\'); yycolumn++; }
      <<EOF>>                        { raiseEx(new ParsingError("unexpected EOF in middle of a string literal",
                                        this.file, yytext(), getLine(), getColumn()), true, new Context(file,
                                        getLine(), getColumn())); }
}

<TEMPLATE_STRING> {
      `                              { yybegin(YYINITIAL); return makeToken(SymbolType.TEMPLATE_STRING, string.toString()); }
      [^\n\r\`\\]+                   { string.append( yytext() ); yycolumn += yytext().length(); }
      \\t                            { string.append('\t'); yycolumn++; }
      \\n                            { string.append('\n'); yyline++; yycolumn = 0;}

      \\r                            { string.append('\r'); yycolumn = 0; }
      \\\`                           { string.append('`'); yycolumn++; }
      \\                             { string.append('\\'); yycolumn++; }
      <<EOF>>                        { raiseEx(new ParsingError("unexpected EOF in middle of a string literal",
                                        this.file, yytext(), getLine(), getColumn()), true, new Context(file,
                                        getLine(), getColumn())); }

}

[^]                                  { raiseEx(new ParsingError(String.format("unexpected character: '%s'", yytext()),
                                        this.file, yytext(), getLine(), getColumn()), true, new Context(file,
                                        getLine(), getColumn())); }