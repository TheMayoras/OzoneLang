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

package ozonelang.ozone.core.ast.node.arithmetic;

import ozonelang.ozone.core.ast.NodePriority;
import ozonelang.ozone.core.ast.node.CodeNode;
import ozonelang.ozone.core.ast.node.ExpressionNode;
import ozonelang.ozone.core.lexer.Context;
import ozonelang.ozone.core.lexer.SymbolType;
import ozonelang.ozone.core.lexer.Token;
import ozonelang.ozone.core.runtime.exception.ErrorConstants;
import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;
import ozonelang.ozone.core.runtime.type.OzFloat;
import ozonelang.ozone.core.runtime.type.OzInteger;
import ozonelang.ozone.core.runtime.type.OzLong;
import ozonelang.ozone.core.runtime.type.OzNothing;
import ozonelang.ozone.core.runtime.type.OzObject;
import ozonelang.ozone.core.runtime.type.OzShort;
import ozonelang.ozone.core.runtime.type.OzString;

import java.util.Objects;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

public class AdditionNode extends ExpressionNode {
    public AdditionNode(CodeNode parent, Token rhs, Token lhs) {
        super(parent, "+", rhs, lhs);
    }

    @Override
    public OzObject evaluate() {
        if (rhs.getSym() != lhs.getSym()) {
            raiseEx(
                    new OzoneException(
                            String.format(
                                    "invalid operation of types '%s' and '%s'",
                                    SymbolType.typename(rhs.getSym()),
                                    SymbolType.typename(lhs.getSym())),
                            ErrorConstants.TYPE_MISMATCH_E,
                            new StackTrace(
                                    getContexts().toArray(new Context[0])
                            ),
                            true
                            )
            );
        }
        else {
            int intVal = Integer.parseInt(rhs.getToken()) + Integer.parseInt(lhs.getToken());
            String stringVal = rhs.getToken() + lhs.getToken();
            switch (Objects.requireNonNull(SymbolType.typename(rhs.getSym()))) {
                case "integer":
                    return new OzInteger(
                            intVal,
                            getContextsArr()
                    );
                case "float":
                    return new OzFloat(
                            intVal,
                            getContextsArr()
                    );
                case "string":
                    return new OzString(
                            stringVal,
                            getContextsArr()
                    );
                case "short":
                    return new OzShort(
                            (short) intVal,
                            getContextsArr()
                    );
                case "long":
                    return new OzLong(
                            intVal,
                            getContextsArr()
                    );
                case "bool":
                    raiseEx(new OzoneException(
                            "Cannot use operator '+' on 'bool'",
                            ErrorConstants.TYPE_MISMATCH_E,
                            new StackTrace(
                                    getContextsArr()
                            ),
                            true
                    ));
            }
        }
        return new OzNothing(getContextsArr());
    }

    @Override
    public NodePriority getPriority() {
        return NodePriority.LOW;
    }
}
