package ozonelang.ozone.core.runtime.type;

import java.io.Serializable;

public class OzString extends OzObject implements Serializable {
    private static final long serialVersionUID = 7645000375801965956L;

    private String value;

    OzString(String s) {
        value = s;
    }

    @Override
    public OzString genericName() {
        return new OzString("string");
    }

    @Override
    public String repr() {
        return value;
    }

    public void setValue(String s) {
        value = s;
    }

    public static OzString fromString(String... s) {
        if (s.length == 0)
            return new OzString(s[0]);
        return new OzString(String.join(" ", s));
    }

    public static OzString fromString(char delim, String... s) {
        return new OzString(String.join(Character.toString(delim), s));
    }
}
