package ozonelang.ozone.core.parser;

public @interface ParsingFunction {
    public String parent() default "";
    public String expression() default "";
}
