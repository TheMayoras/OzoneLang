package ozonelang.ozone.core.AST.exception;

import ozonelang.ozone.core.AST.Location;

public class OzoneException {
    public final StackTrace trace;
    public final int traceLength;
    public final String name;
    public final boolean fatal;

    public OzoneException(String name, StackTrace trace, boolean fatal) {
        this.trace = trace;
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
    }

    public OzoneException(String name, boolean fatal, Location... locations) {
        this.trace = new StackTrace(locations);
        this.traceLength = trace.length();
        this.name = name;
        this.fatal = fatal;
    }

    public static void raise(OzoneException ex) {
        var stream = ex.trace.getOutputStream();
        var locs = ex.trace.getLocations();
        stream.printf(
                "[%s occurred at file '%s', line %s]",
                ex.name,
                locs.get(0).getFile(),
                locs.get(0).getLine()
        );
        ex.trace.printTrace();
    }
}
