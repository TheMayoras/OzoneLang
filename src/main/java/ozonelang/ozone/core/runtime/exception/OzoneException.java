package ozonelang.ozone.core.runtime.exception;

import ozonelang.ozone.core.AST.Context;

import static java.lang.System.exit;

public class OzoneException {
    public final StackTrace trace;
    public final int traceLength;
    public final String name;
    public final String message;
    public final int exitCode;
    private boolean fatal;

    public OzoneException(String message, String name, StackTrace trace, boolean fatal) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.message = message;
        this.exitCode = 2;
    }

    public OzoneException(String message, String name, StackTrace trace, boolean fatal, int exitCode) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.message = message;
        this.exitCode = exitCode;
    }
    public OzoneException(Throwable nested, StackTrace trace, boolean fatal) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = nested.getClass().getCanonicalName();
        this.fatal = fatal;
        this.message = nested.getMessage();
        this.exitCode = 2;
    }
    public OzoneException(String message, String name, boolean fatal, Context... contexts) {
        this.trace = new StackTrace(contexts);
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.message = message;
        this.exitCode = 2;
    }

    public OzoneException(Throwable nested, boolean fatal, Context... contexts) {
        this.trace = new StackTrace(contexts);
        this.traceLength = trace.length();
        this.name = nested.getClass().getCanonicalName();
        this.fatal = fatal;
        this.message = nested.getMessage();
        this.exitCode = 2;
    }

    public OzoneException(String message, String name, boolean fatal, int exitCode, Context... contexts) {
        this.trace = new StackTrace(contexts);
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.message = message;
        this.exitCode = exitCode;
    }
    public OzoneException(Throwable nested, boolean fatal, int exitCode, Context... contexts) {
        this.trace = new StackTrace(contexts);
        this.traceLength = trace.length();
        this.name = nested.getClass().getCanonicalName();
        this.fatal = fatal;
        this.message = nested.getMessage();
        this.exitCode = exitCode;
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
                "[%s occurred at file '%s', line %s, col %s. Message: '%s']\n",
                ex.name,
                locs.get(0).getFile(),
                locs.get(0).getStartLine(),
                locs.get(0).getStartCol(),
                ex.getMessage()
        );
        ex.trace.printTrace();
        if (ex.isFatal()) {
            stream.printf("Unrecoverable error, terminating with exit code %d\n", ex.exitCode);
            exit(ex.exitCode);
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
