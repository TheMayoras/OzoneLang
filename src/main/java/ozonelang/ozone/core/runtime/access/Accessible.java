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

package ozonelang.ozone.core.runtime.access;

import ozonelang.ozone.core.lexer.Context;
import ozonelang.ozone.core.runtime.type.OzObject;
import ozonelang.ozone.core.runtime.type.OzString;

public interface Accessible {
    /**
     * Return a property of an object accessed with a getter/setter.
     *
     * Example of ozone code that'll invoke this:
     * {@code
     *  $d = MyVariable.length;
     * }
     *
     * @param propertyName the name of the property to be accessed,
     * {@code length} in the above example
     * @param contexts the contexts this property call appears in.
     * @param <T> The return type of the property
     * @return The property
     */
    <T extends OzObject> T getProperty(OzString propertyName, Context... contexts);

    /**
     * Set a property of an object.
     *
     * Example of ozone code that'll invoke this:
     *
     * {@code
     *  MyObject.MyWritableProp = 0;
     * }
     * {@code
     *  MyObject.MyReadonlyProp = 0; // error
     * }
     *
     * @param value the new value for the property
     * @param <T> the type of the value
     */
    <T extends OzObject> void setProperty(T value);
}
