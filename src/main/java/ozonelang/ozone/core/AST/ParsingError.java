package ozonelang.ozone.core.AST;

public class ParsingError extends RuntimeException {
    public final String token;
    public final String file;
    public final int line;
    public final int column;

    private String message;

    public ParsingError(String file, String token, int line, int col) {
        this.token = token;
        this.file = file;
        this.line = line;
        column = col;
    }

    public ParsingError(String msg, String file, String token, int line, int col) {
        this(file, token, line, col);
        this.message = msg;
    }

    @Override
    public String getMessage() {
        if (message == null)
            return String.format("Invalid token '%s' at file '%s', line %d, column %d", token, file, line, column);
        return message;
    }

    public static ParsingError expected(Token got, Token... wanted) {
        StringBuilder builder = new StringBuilder();
        if (wanted.length == 1) {
            builder.append(String.format("File '%s', line %d, column %d: Expected '%s', got '%s'",
                    got.getFile(), got.getLine(), got.getCol(), wanted[0].getToken(), got.getToken()));
        } else {
            builder = new StringBuilder(String.format("File '%s', line %d, column %d: Expected '%s' ",
                    got.getFile(), got.getLine(), got.getCol(), wanted[0].getToken()));
            int idx = 1;
            for ( ; idx < (wanted.length - 1); idx++) {
                builder.append(String.format(", '%s' ", wanted[idx].getToken()));
            }
            builder.append(String.format("or '%s'", wanted[idx]));
        }
        return new ParsingError(builder.toString(), got.getFile(), got.getToken(), got.getLine(), got.getCol());
    }
    public static ParsingError expected(Token got, String... wanted) {
        StringBuilder builder = new StringBuilder();
        if (wanted.length == 1) {
            builder.append(String.format("File '%s', line %d, column %d: Expected '%s', got '%s'",
                    got.getFile(), got.getLine(), got.getCol(), wanted[0], got.getToken()));
        } else {
            builder = new StringBuilder(String.format("File '%s', line %d, column %d: Expected '%s' ",
                    got.getFile(), got.getLine(), got.getCol(), wanted[0]));
            int idx = 1;
            for ( ; idx < (wanted.length - 1); idx++) {
                builder.append(String.format(", '%s' ", wanted[idx]));
            }
            builder.append(String.format("or '%s'", wanted[idx]));
        }
        return new ParsingError(builder.toString(), got.getFile(), got.getToken(), got.getLine(), got.getCol());
    }
}
