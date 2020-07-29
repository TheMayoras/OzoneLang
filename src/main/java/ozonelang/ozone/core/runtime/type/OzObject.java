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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class OzObject implements Serializable {
    private static final long serialVersionUID = 7932392952560561002L;

    protected final Context[] contexts;

    protected Collection<Context> getContexts() {
        return Arrays.asList(contexts);
    }

    public OzObject(Context... contexts) {
        this.contexts = contexts;
    }

    public OzString genericName() {
        return OzString.fromString("object");
    }

    public OzObject dup() {
        return SerializationUtils.clone(this);
    }

    public String repr() {
        return "<" + genericName() + "#0x" + Integer.toHexString(System.identityHashCode(this)) + ">";
    }
}
