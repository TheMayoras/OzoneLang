package main.java.ozonelang.ozone.core.AST.exception;

import main.java.ozonelang.ozone.core.AST.Span;

public class OzoneException extends RuntimeException {

    private Span span;
    private OzoneException nestedException;

    public OzoneException(Span span) {
        super("The span " + span + " caused an exception");
        this.span = span;
    }

    public OzoneException(Span span, String message) {
        super(message);
        this.span = span;
    }

    public OzoneException(Span span, OzoneException nestedException) {
        super("The span " + span + " caused an exception");
        this.span = span;
        this.nestedException = nestedException;
    }

    public OzoneException(Span span, String message, OzoneException nestedException) {
        super(message);
        this.span = span;
        this.nestedException = nestedException;
    }

}
