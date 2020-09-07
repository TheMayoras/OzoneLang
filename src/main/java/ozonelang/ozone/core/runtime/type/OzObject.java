/*
 * This file is part of Ozone.
 *
 * Ozone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ozone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ozone.  If not, see <https://www.gnu.org/licenses/>.
 */

package ozonelang.ozone.core.runtime.type;

import org.apache.commons.lang3.SerializationUtils;
import ozonelang.ozone.core.lexer.Context;
import ozonelang.ozone.core.parser.Parser;
import ozonelang.ozone.core.runtime.access.Accessible;
import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

public class OzObject implements Serializable, Accessible {
    private static final long serialVersionUID = 7932392952560561002L;
    private Object value;
    protected final Map<String, OzObject> defaultProperties;
    protected final Context[] contexts;
    protected Collection<Context> getContexts() {
        return Arrays.asList(contexts);
    }

    public OzObject(Object value, Context... contexts) {
        this.contexts = contexts;
        this.value = value;
        var initProps = new HashMap<String, OzObject>();
        initProps.put("AsString", repr());
        initProps.put("HashCode", OzString.fromString(Integer.toHexString(System.identityHashCode(this))));
        initProps.put("BinaryHash", OzString.fromString(Integer.toBinaryString(System.identityHashCode(this))));
        defaultProperties = Collections.unmodifiableMap(initProps);
    }

    public OzString genericName() {
        return OzString.fromString("object");
    }

    public OzObject dup() {
        return SerializationUtils.clone(this);
    }

    public OzString repr() {
        return OzString.fromString("<" + genericName() + "#0x" + Integer.toHexString(System.identityHashCode(this)) + ">");
    }

    public Object getValue() {
        return value;
    }

    @Override
    public <T extends OzObject> T getProperty(OzString propertyName, Context... contexts) {
        return null;
    }

    public OzObject callOperator(Parser.Operator op, OzObject o) {
        raiseEx(new OzoneException(new UnsupportedOperationException(
                String.format("cannot apply operator '%s' on types '%s', '%s'", op.getOperator(), genericName(), genericName())),
                new StackTrace(getContexts().toArray(new Context[0])), true)
        );
        return null;
    }

    @Override
    public <T extends OzObject> void setProperty(T value) {}
}
