package ozonelang.ozone.core.AST.exception;

import ozonelang.ozone.core.AST.Location;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

public class StackTrace {
    private final List<Location> codePoints;
    private final PrintStream outStream;
    public StackTrace(Location... locations) {
        codePoints = Arrays.asList(locations);
        outStream = out;
    }
    public StackTrace(PrintStream output, Location... locations) {
        outStream = output;
        codePoints = Arrays.asList(locations);
    }
    public List<Location> getLocations() {
        return Collections.unmodifiableList(codePoints);
    }

    public PrintStream getOutputStream() {
        return outStream;
    }

    public int length() {
        return codePoints.size();
    }
    public void printTrace() {
        for (int i = 1; i < codePoints.size(); i++) {
            outStream.printf("\tat file '%s', line %s\n", codePoints.get(i).getFile(), codePoints.get(i).getLine());
        }
    }
}
