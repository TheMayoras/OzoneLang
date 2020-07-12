package ozonelang.ozone.core.runtime.exception;

import ozonelang.ozone.core.AST.Location;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

public class StackTrace {
    private List<Location> codePoints = new ArrayList<>();
    private final PrintStream outStream;
    public StackTrace(Location location, Location... locations) {
        codePoints.addAll(Arrays.asList(locations));
        codePoints.add(location);
        outStream = out;
    }
    public StackTrace(PrintStream output, Location location, Location... locations) {
        outStream = output;
        codePoints.addAll(Arrays.asList(locations));
        codePoints.add(location);
    }
    StackTrace(Location... locations) {
        outStream = out;
        codePoints.addAll(Arrays.asList(locations));
    }
    public List<Location> getLocations() {
        return Collections.unmodifiableList(codePoints);
    }
    public void addLocation(Location location) {
        codePoints.add(location);
    }
    public PrintStream getOutputStream() {
        return outStream;
    }

    public int length() {
        return codePoints.size();
    }
    public void printTrace() {
        for (int i = 1; i < codePoints.size(); i++) {
            outStream.printf("\t[at file '%s', line %s]\n", codePoints.get(i).getFile(), codePoints.get(i).getLine());
        }
    }
}
