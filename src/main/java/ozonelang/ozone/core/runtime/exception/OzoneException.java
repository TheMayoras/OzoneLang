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

import static java.lang.System.exit;

public class OzoneException {
    public final StackTrace trace;
    public final int traceLength;
    public final String name;
    public final String message;
    private final boolean fatal;

    public OzoneException(String message, String name, StackTrace trace, boolean fatal) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.message = message;
    }

    public OzoneException(Throwable nested, StackTrace trace, boolean fatal) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = nested.getClass().getCanonicalName();
        this.fatal = fatal;
        this.message = nested.getMessage();
    }
    public OzoneException(String message, String name, boolean fatal, Context... contexts) {
        this.trace = new StackTrace(contexts);
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.message = message;
    }

    public OzoneException(Throwable nested, boolean fatal, Context... contexts) {
        this.trace = new StackTrace(contexts);
        this.traceLength = trace.length();
        this.name = nested.getClass().getCanonicalName();
        this.fatal = fatal;
        this.message = nested.getMessage();
    }

    public boolean isFatal() {
        return fatal;
    }

    public String getMessage() {
        return message;
    }

    /*public static void catchEx(OzoneException ex) {
        ...
    }*/

    public static void raiseEx(OzoneException ex) {
        var stream = ex.trace.getOutputStream();
        var locs = ex.trace.getLocations();
        stream.printf(
                "[%s occurred at file '%s', line %s, column %s]\n[Message: '%s']\n",
                ex.name,
                locs.get(0).getFile(),
                locs.get(0).getStartLine(),
                locs.get(0).getStartCol(),
                ex.getMessage()
        );
        ex.trace.printTrace();
        if (ex.isFatal()) {
            exit(1);
        }
    }
    public static void raiseEx(Throwable nested, boolean fatal, Context... contexts) {
        var trace = new StackTrace(contexts[0]);
        for (var i = 1; i < contexts.length; i++)
            trace.addContext(contexts[i]);
        var ex = new OzoneException(nested, fatal, contexts);
        raiseEx(ex);
    }
}
