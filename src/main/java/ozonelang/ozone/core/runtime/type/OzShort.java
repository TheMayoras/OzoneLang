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
import ozonelang.ozone.core.parser.Parser;

public class OzShort extends OzObject {
    private short value;

    public OzShort(short value, Context... contexts) {
        super(value, contexts);
        this.value = value;
    }

    @Override
    public OzObject callOperator(Parser.ExpressionParser.Operator op, OzObject o) {
        return super.callOperator(op, o);
    }

    @Override
    public OzString genericName() {
        return OzString.fromString("short");
    }

    @Override
    public OzString repr() {
        return OzString.fromString(String.valueOf(this.value));
    }

    @Override
    public Short getValue() {
        return value;
    }
}
