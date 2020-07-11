package ozonelang.ozone.core;

public @interface ParsingFunction {
    public String parent() default "";
    public String expression() default "";
}
