package ozonelang.ozone.core.AST;

public class OzoneVariable<T> {
    public final String name;
    public final T value;
    public final Class type;
    public OzoneVariable(String name, T value) {
        this.name = name;
        this.value = value;
        this.type = this.value.getClass();
    }

    @Override
    public String toString() {
        return String.format("%s<name=\"%s\" value=\"%s\" type=\"%s\">",
                this.getClass().getCanonicalName(), this.name, this.value.toString(), this.type.getName());
    }
}
