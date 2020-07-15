package ozonelang.ozone.core.AST;

public class Context {
    private String source;
    private String file;
    private long startLine;
    private long endLine;

    public Context(String file, String source, long startLine, long endLine) {
        this.source = source;
        this.file = file;
        this.endLine = endLine;
        this.startLine = startLine;
    }

    public Context(String file, String source, int startLine, int endLine) {
        this.source = source;
        this.file = file;
        this.endLine = (long) endLine;
        this.startLine = (long) startLine;
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
}
