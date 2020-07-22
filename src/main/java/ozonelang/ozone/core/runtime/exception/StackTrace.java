package ozonelang.ozone.core.runtime.exception;

import ozonelang.ozone.core.AST.Context;

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
    public StackTrace(PrintStream output, Context context, Context... contexts) {
        outStream = output;
        codePoints.addAll(Arrays.asList(contexts));
        codePoints.add(context);
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
