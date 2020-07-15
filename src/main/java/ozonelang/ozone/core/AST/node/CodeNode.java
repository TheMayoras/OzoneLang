package ozonelang.ozone.core.AST.node;

import ozonelang.ozone.core.AST.Context;
import ozonelang.ozone.core.AST.Span;

public abstract class CodeNode implements Span {
    private final Context context;
    public CodeNode(Context context) {
        this.context = context;
    }
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return String.format("<%s@%s, %d-%d>", getClass().getCanonicalName(), context.getFile(), context.getStartLine(), context.getEndLine());
    }
}
