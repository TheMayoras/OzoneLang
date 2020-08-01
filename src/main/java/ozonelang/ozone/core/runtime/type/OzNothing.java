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
import ozonelang.ozone.core.runtime.exception.ErrorConstants;
import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;

import java.io.Serializable;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

public class OzNothing extends OzObject implements Serializable {
    private static final long serialVersionUID = 1030778380038760396L;

    public OzNothing(Context... contexts) {
        super(null, contexts);
    }

    @Override
    public OzString genericName() {
        return OzString.fromString("nothing");
    }

    @Override
    public OzString repr() {
        return genericName();
    }

    public static boolean ensureNonNull(OzObject candidate) {
        if (candidate == null) {
            System.out.printf(
                    "An unknown internal error occurred. If the problem persists, please report a bug to https://github.com/TheMayoras/OzoneLang." +
                    "\nThe reason seems to be: Parameter \"candidate\" of function \"ensureNonNull\" in file src/main/java/%s%n recieved a null value",
                    OzNothing.class.getName().replace(".", "/")
            );
            System.exit(1);
        }
        if (candidate instanceof OzNothing) {
            raiseEx(
                    new OzoneException(
                            "expected value, found nothing",
                            ErrorConstants.VALUE_EXPECTED_E,
                            new StackTrace(candidate.contexts),
                            true
                    )
            );
        }
        return true;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
