package ozonelang.ozone.core.AST.node;

import ozonelang.ozone.core.AST.Context;
import ozonelang.ozone.core.AST.Span;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

public abstract class CodeNode implements Span {
    private final Context context;
    public CodeNode(Context context) {
        if (start.getStartLine() != end.getStartLine())
            raiseEx(new RuntimeException("cannot have two different files in a codeNode"), true, start, end);
        this.context = context;
    }
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return String.format("<%s@%s, %d-%d>", getClass().getCanonicalName(), start.getFile(), start.getStartLine(), end.getStartLine());
    }
}
