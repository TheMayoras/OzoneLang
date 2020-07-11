package ozonelang.ozone.core.AST;

public class Location {
    private String source;
    private String file;
    private long line;
    private long column;

    public Location(String file, String source, long line, long column) {
        this.source = source;
        this.file = file;
        this.line = line;
        this.column = column;
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

    public long getColumn() {
        return column;
    }

    public void setColumn(long column) {
        this.column = column;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
