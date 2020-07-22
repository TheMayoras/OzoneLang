package ozonelang.ozone.core.runtime.type;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class OzObject implements Serializable {
    private static final long serialVersionUID = 7932392952560561002L;

    public OzString genericName() {
        return OzString.fromString("obj");
    }

    public OzObject dup() {
        return SerializationUtils.clone(this);
    }

    public String repr() {
        return "<" + genericName() + "#0x" + Integer.toHexString(hashCode()) + ">";
    }
}
