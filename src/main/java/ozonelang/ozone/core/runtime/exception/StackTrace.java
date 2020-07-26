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

package ozonelang.ozone.core.runtime.exception;

import ozonelang.ozone.core.lexer.Context;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

public class StackTrace {
    private List<Context> codePoints = new ArrayList<>();
    private final PrintStream outStream;
    public StackTrace(Context... contexts) {
        codePoints.addAll(Arrays.asList(contexts));
        outStream = out;
    }
    public StackTrace(PrintStream output, Context... contexts) {
        outStream = output;
        codePoints.addAll(Arrays.asList(contexts));
    }
    public List<Context> getLocations() {
        return Collections.unmodifiableList(codePoints);
    }
    public void addContext(Context context) {
        codePoints.add(context);
    }
    public PrintStream getOutputStream() {
        return outStream;
    }

    public int length() {
        return codePoints.size();
    }
    public void printTrace() {
        for (int i = 1; i < codePoints.size(); i++) {
            outStream.printf("\t[at file '%s', line %s, column %d]\n", codePoints.get(i).getFile(),
                    codePoints.get(i).getStartLine(), codePoints.get(i).getStartCol());
        }
    }
}
