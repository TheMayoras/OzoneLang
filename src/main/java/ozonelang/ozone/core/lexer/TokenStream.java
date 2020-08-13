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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

import static ozonelang.ozone.core.lexer.ParsingError.expected;

public class TokenStream implements Iterable<Token> {
    private Lexer lexer;
    private List<Token> tokens;
    private int idx;
    private boolean addedEOF = false;

    public TokenStream(List<Token> tokens) {
        tokens.remove(tokens.size() - 1);
        this.tokens = tokens;
        idx = 0;
    }

    /**
     * Accepts a token at the current index.
     *
     * @throws ParsingError If an invalid token occurs
     * @param applicant Acceptable tokens
     * @return The accepted token
     */
    public Token accept(String... applicant) {
        var token = tokens.get(idx++);
        for (var v : applicant) {
            if (v.equals(token.getToken())) {
                idx++;
                return token;
            }
        }
        throw expected(token, applicant);
    }

    /**
     * Accepts a symbol type at the current index.
     *
     * @throws ParsingError If an invalid token occurs
     * @param applicant Acceptable symbol type
     * @return The accepted token
     */
    public Token accept(SymbolType... applicant) {
        var token = tokens.get(idx++);
        var sym = token.getSym();
        for (var s : applicant) {
            if (sym != s) {
                throw expected(token, "symbol of type '" +
                        s.toString() + "', got '" + sym.toString() + "'");
            }
        }
        return token;
    }

    /**
     * Accepts a token at a current index IF it exists.
     *
     * @param applicant Acceptable tokens
     * @return If the token was the next one
     */
    public boolean acceptIfNext(String... applicant) {
        var token = tokens.get(idx);
        for (var s : applicant) {
            if (token.is(s)) {
                idx++;
                return true;
            }
        }
        return false;
    }

    /**
     * Increments the index counter by one.
     */
    public void advance() {
        idx++;
    }

    /**
     * Accepts a symbol type at a current index IF it exists.
     *
     * @param applicant Acceptable symbol types
     * @return If the SymbolType type was the next one
     */
    public boolean acceptIfNext(SymbolType... applicant) {
        var symbol = tokens.get(idx).getSym();
        for (var sym : applicant) {
            if (symbol == sym) {
                idx++;
                return true;
            }
        }
        return false;
    }

    /**
     * Does the stream have more tokens?
     *
     * @return Whether the stream has tokens left
     */
    public boolean hasNext() {
        return idx < tokens.size();
    }

    /**
     * Return whether the next token equals to
     * one of the applicants' given.
     *
     * @return {@code true} or {@code false}
     */
    public boolean isNext(String... applicant) {
        if (!hasNext())
            return false;
        Token token = tokens.get(idx);
        for (var s : applicant) {
            if (token.is(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether the next token's symbol type
     * equals to one of the applicant symbol types given.
     *
     * @return {@code true} or {@code false}
     */
    public boolean isNext(SymbolType... applicant) {
        if (!hasNext())
            return false;
        SymbolType sym = tokens.get(idx).getSym();
        for (var s : applicant) {
            if (sym == s) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a token to the end of the list. Can only be called once.
     * Second calls to this function will be ignored.
     *
     * @param EOF The string to append
     */
    public void addEOFMark(String EOF)  {
        if (!addedEOF)
            tokens.add(new Token(seek().getFile(), SymbolType.EOF, seek().getLine(), (seek().getCol() + EOF.length())));
        addedEOF = true;
    }

    /**
     * Returns the next token in the stream.
     *
     * @return The next token in the stream.
     */
    public Token next() {
        return tokens.get(++idx);
    }

    /**
     * Returns the current token in the stream.
     *
     * @return The current token in the stream.
     */
    public Token seek() {
        return tokens.get(idx);
    }

    /**
     * Returns the current token in the stream as a string.
     *
     * @return The current token in the stream as a string.
     */
    public String seekString() {
        return tokens.get(idx).getToken();
    }

    /**
     * Returns the lexer used by this instance of TokenStream.
     *
     * @return The lexer used by this TokenStream
     */
    public Lexer getLexer() {
        return lexer;
    }

    /**
     * Accepts the next token if it is a valid identifier.
     *
     * @return The accepted identifier;
     */
    public Token acceptIdentifier() {
        Token token = tokens.get(idx++);
        if (!token.getToken().matches("[_a-zA-Z][_a-zA-Z0-9]+ | _+")) {
            throw new ParsingError(String.format("expected identifier, got '%s'",
                    token.getToken()), token.getFile(), token.getLine(), token.getCol());
        }
        return token;
    }

    /**
     * Opens a TokenStream with the specified Lexer instance.
     *
     * @throws IOException On lexer error
     * @return A new TokenStream instance
     */
    public static TokenStream open(Lexer lexer) throws IOException {
        return new TokenStream(lexer.lex());
    }

    /**
     * Opens a TokenStream with the specified filename.
     *
     * @throws IOException on lexer error
     * @return A new TokenStream instance
     */
    public static TokenStream open(String file) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
        return new TokenStream(new Lexer(reader, file).lex());
    }

    @Override
    public String toString() {
        return tokens.toString();
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public void forEach(Consumer<? super Token> action) {
        Objects.requireNonNull(action);
        for (Token t : this) {
            action.accept(t);
        }
    }

    @Override
    public Spliterator<Token> spliterator() {
        return tokens.spliterator();
    }
}
