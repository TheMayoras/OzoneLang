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
