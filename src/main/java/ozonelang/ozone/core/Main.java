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

package ozonelang.ozone.core;

import ozonelang.ozone.core.lexer.TokenStream;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        TokenStream ts;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            ts = TokenStream.open(new Lexer(new StringReader(s.nextLine()), "<stdin>"));
            while (ts.hasNext()) {
                System.out.println(ts.seek());
                ts.advance();
            }
        }
    }
}
