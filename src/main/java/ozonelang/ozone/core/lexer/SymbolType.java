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

public enum SymbolType {
    ASSIGN,
    AT,
    BOOL,
    BREAK,
    BYTE_LITERAL,
    COMMA,
    COLON,
    CONCAT_COMMA,
    CONCAT_SPACE,
    DIV,
    DOC_COMMENT,
    DOT,
    ELIF,
    ELSE,
    EOF,
    EQ,
    ERROR,
    FLOAT_LITERAL,
    FUNC,
    HASHTAG,
    IDENTIFIER,
    IF,
    IN,
    INTEGER_LITERAL,
    IS,
    LBRACE,
    LONG_LITERAL,
    LPAREN,
    MINUS,
    MOD,
    MODULE_SEPARATOR,
    MUL,
    NO,
    NOT,
    NOTEQ,
    NOTHING,
    PLUS,
    POW,
    RBRACE,
    RPAREN,
    SHORT_LITERAL,
    SQ_BRACET_L,
    SQ_BRACET_R,
    STRING_LITERAL,
    TEMPLATE_STRING,
    USE,
    VAR,
    GT,
    LT,
    GTEQ,
    LTEQ,
    WHEN,
    YES;

    public static String typename(SymbolType s) {
        switch (s) {
            case INTEGER_LITERAL:
                return "integer";
            case SHORT_LITERAL:
                return "short";
            case FLOAT_LITERAL:
                return "float";
            case LONG_LITERAL:
                return "long";
            case YES:
            case NO:
                return "bool";
            case STRING_LITERAL:
                return "string";
            case NOTHING:
                return "nothing";
            default:
                return null;
        }
    }
}
