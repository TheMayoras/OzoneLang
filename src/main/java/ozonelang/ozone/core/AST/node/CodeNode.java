package ozonelang.ozone.core.AST.node;

import ozonelang.ozone.core.AST.Context;
import ozonelang.ozone.core.AST.Span;

import java.util.List;

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

    public abstract String getKeyword();

    public abstract boolean hasChildren();

    public abstract boolean hasChildren(int n);

    public abstract int children();

    public abstract List<CodeNode> getChildren();

    public abstract CodeNode getParent();

    public abstract void addChild(CodeNode child);

    public abstract void addChild(CodeNode... child);

    public abstract String reconstruct();

    protected abstract void setParent(CodeNode parent);
}
