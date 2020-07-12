package ozonelang.ozone.core.runtime.exception;

import ozonelang.ozone.core.AST.Location;

import static java.lang.System.exit;

public class OzoneException {
    public final StackTrace trace;
    public final int traceLength;
    public final String name;
    public final int exitCode;
    private boolean fatal;

    public OzoneException(String name, StackTrace trace, boolean fatal) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.exitCode = 2;
    }

    public OzoneException(String name, StackTrace trace, boolean fatal, int exitCode) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.exitCode = exitCode;
    }

    public OzoneException(String name, boolean fatal, Location... locations) {
        this.trace = new StackTrace(locations);
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.exitCode = 2;
    }

    public OzoneException(String name, boolean fatal, int exitCode, Location... locations) {
        this.trace = new StackTrace(locations);
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
        this.exitCode = exitCode;
    }

    public boolean isFatal() {
        return fatal;
    }

    /*public static void catchEx(OzoneException ex) {
        ...
    }*/

    public static void raiseEx(OzoneException ex) {
        var stream = ex.trace.getOutputStream();
        var locs = ex.trace.getLocations();
        stream.printf(
                "[%s occurred at file '%s', line %s]\n",
                ex.name,
                locs.get(0).getFile(),
                locs.get(0).getLine()
        );
        ex.trace.printTrace();
        if (ex.isFatal())
            stream.printf("Unrecoverable error, terminating with exit code %d\n", ex.exitCode);
            exit(ex.exitCode);
    }
}
