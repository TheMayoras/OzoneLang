package ozonelang.ozone.core.AST;

public class Context {
    private String source;
    private String file;
    private long line;

    public Context(String file, String source, long line) {
        this.source = source;
        this.file = file;
        this.line = line;
    }

    public Context(String file, String source, int line) {
        this.source = source;
        this.file = file;
        this.line = (long) line;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
