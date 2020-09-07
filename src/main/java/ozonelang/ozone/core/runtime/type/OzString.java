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
import ozonelang.ozone.core.runtime.exception.ErrorConstants;
import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;

import java.io.Serializable;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

public class OzString extends OzObject implements Serializable {
    private static final long serialVersionUID = 7645000375801965956L;

    private String value;

    public OzString(String s, Context... contexts) {
        super(s, contexts);
        value = s;
    }

    public static OzString fromString(String[] s) {
        if (s.length == 1)
            return new OzString(s[0]);
        return new OzString(String.join(" ", s));
    }

    public static OzString fromString(String delim, String... s) {
        return new OzString(String.join(delim, s));
    }

    @Override
    public OzString genericName() {
        return new OzString("string");
    }

    @Override
    public OzString repr() {
        return fromString(value);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public OzObject callOperator(Parser.Operator op, OzObject s) {
        OzString val = null;
        if (s instanceof OzString)
            val = (OzString) s;
        else
            raiseEx(new OzoneException(
                            String.format("cannot apply operator '%s' on types '%s', '%s'",
                                    op.getOperator(), getClass().getSimpleName(), getClass().getSimpleName()),
                            ErrorConstants.TYPE_MISMATCH_E,
                            true,
                            getContexts().toArray(new Context[0])
                    )
            );
        switch (op) {
            case PLUS:
                return fromString(getValue() + val.getValue());
            case EQ:
                return new OzBool(getValue().equals(val.getValue()));
            /*
             * These are just kinda the same with slight differences.
             */
            case GT: {
                var me = getValue();
                var oth = val.getValue();
                if (me.length() == oth.length() && me.length() == 1)
                    return new OzBool(me.charAt(0) > oth.charAt(0));
                return new OzBool(getValue().length() > val.getValue().length());
            }
            case LT: {
                var me = getValue();
                var oth = val.getValue();
                if (me.length() == oth.length() && me.length() == 1)
                    return new OzBool(me.charAt(0) < oth.charAt(0));
                return new OzBool(me.length() < oth.length());
            }
            case GTEQ: {
                var me = getValue();
                var oth = val.getValue();
                if (me.length() == oth.length() && me.length() == 1)
                    return new OzBool(me.charAt(0) >= oth.charAt(0));
                return new OzBool(me.length() >= oth.length());
            }
            case LTEQ: {
                var me = getValue();
                var oth = val.getValue();
                if (me.length() == oth.length() && me.length() == 1)
                    return new OzBool(me.charAt(0) <= oth.charAt(0));
                return new OzBool(me.length() <= oth.length());
            }
            case STRSPACE:
                return fromString(" ", getValue(), val.getValue());
            case STRCOMMA:
                return fromString(", ", getValue(), val.getValue());
            default: {
                raiseEx(new OzoneException(
                        String.format("cannot apply operator '%s' on types '%s', '%s'",
                                op.getOperator(), getClass().getSimpleName(), getClass().getSimpleName()),
                        ErrorConstants.TYPE_MISMATCH_E,
                        new StackTrace(getContexts().toArray(new Context[0])),
                        true
                ));
                return null;
            }
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String s) {
        value = s;
    }
}
