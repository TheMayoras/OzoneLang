package ozonelang.ozone.core.lexer;

public class Context {
    private String source;
    private String file;
    private long startLine;
    private long endLine;
    private long startCol;
    private long endCol;

    public Context(String file, String source, long startLine, long endLine, long startCol, long endCol) {
        this.source = source;
        this.file = file;
        this.endLine = endLine;
        this.startLine = startLine;
        this.startCol = startCol;
        this.endCol = endCol;
    }

    public Context(Token t, Token t2) {
        this.source = t.getToken() + "\n" + t2.getToken();
        this.file = (t.getFile().equals(t2.getFile()) ? t.getFile() : String.format("'%s' and '%s'", t.getFile(), t2.getFile()));
        this.endLine = t2.getLine();
        this.startLine = t.getLine();
        this.endCol = t2.getCol();
        this.startCol = t.getCol();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getStartLine() {
        return startLine;
    }

    public void setEndLine(long endLine) {
        this.endLine = endLine;
    }

    public long getEndLine() {
        return endLine;
    }

    public void setStartLine(long startLine) {
        this.startLine = startLine;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public long getStartCol() {
        return startCol;
    }

    public void setStartCol(long startCol) {
        this.startCol = startCol;
    }

    public long getEndCol() {
        return endCol;
    }

    public void setEndCol(long endCol) {
        this.endCol = endCol;
    }
}