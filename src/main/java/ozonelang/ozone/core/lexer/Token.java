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

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Token {
    private String token;
    private Object value;
    private final String file;
    private final SymbolType sym;
    private final int line;
    private final int col;

    public Token(String file, SymbolType sym, Object value, int line, int col) {
        this.token = String.valueOf(value);
        this.value = value;
        this.sym = sym;
        this.file = file;
        this.line = line;
        this.col = col;
    }
    public Token(String file, SymbolType sym, int line, int col) {
        this.sym = sym;
        this.file = file;
        this.line = line;
        this.col = col;
    }

    public boolean matches(String regex) {
        return Pattern.matches(regex, getToken());
    }

    public boolean matches(Pattern pattern) {
        return pattern.matcher(getToken()).matches();
    }

    public boolean is(String s) {
        return getToken().equals(s);
    }

    public String getToken() {
        return token;
    }

    public Object getValue() {
        return value;
    }

    public SymbolType getSym() {
        return sym;
    }

    public int getLine() {
        return line;
    }

    public String getFile() {
        return file;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        var template = "<%s '%s'> at line %d, column %d, file '%s'";
        if (token != null)
            return String.format(template, sym.toString(), token, line, col, file);
        return String.format(template, "TOKEN", sym.toString(), line, col, file);
    }

    public static Token[] alternative(Token wrong, SymbolType... wanted) {
        var result = new ArrayList<Token>();
        for (SymbolType s : wanted) {
            result.add(new Token(wrong.getFile(), s, wrong.getLine(), wrong.getCol()));
        }
        return result.toArray(new Token[0]);
    }
}
