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

import ozonelang.ozone.core.lexer.Context;

public class OzByte extends OzObject {
    private byte value;

    public OzByte(byte value, Context... contexts) {
        super(value, contexts);
    }

    @Override
    public OzString genericName() {
        return OzString.fromString("byte");
    }

    @Override
    public OzString repr() {
        return OzString.fromString("0x" + Integer.toHexString(value));
    }

    @Override
    public Byte getValue() {
        return value;
    }
}