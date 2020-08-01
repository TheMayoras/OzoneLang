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

package ozonelang.ozone.core.ast.node;

import ozonelang.ozone.core.ast.NodePriority;
import ozonelang.ozone.core.ast.node.arithmetic.OperandType;
import ozonelang.ozone.core.runtime.exception.ErrorConstants;
import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;
import ozonelang.ozone.core.runtime.type.OzObject;

import java.util.List;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

public abstract class ExpressionNode extends CodeNode {
    protected ExpressionNode rhs;
    protected ExpressionNode lhs;
    protected String operator;
    protected OperandType operandType;

    public ExpressionNode(CodeNode parent, String op, OperandType operandType, ExpressionNode rhs, ExpressionNode lhs) {
        super(parent, parent.getContextsArr());
        this.rhs = rhs;
        this.lhs = lhs;
        this.operandType = operandType;
        operator = op;
    }

    public abstract OzObject evaluate();

    public abstract NodePriority getPriority();

    public abstract OperandType[] getSupportedOperandTypes();

    public OperandType getOperandType() {
        return operandType;
    }

    protected void ensureType(OperandType o) {
        for (var type : getSupportedOperandTypes()) {
            if (type == o)
                return;
        }
        raiseEx(new OzoneException(
                String.format("Cannot use operator '%s' on type '%s'", getOperator(), o.toString()),
                ErrorConstants.TYPE_MISMATCH_E,
                new StackTrace(
                        getContextsArr()
                ),
                true
        ));
    }

    protected static boolean isIntegerType(OperandType o) {
        return o == OperandType.BYTE
            || o == OperandType.FLOAT
            || o == OperandType.INTEGER
            || o == OperandType.LONG
            || o == OperandType.SHORT;
    }

    public ExpressionNode getLHS() {
        return lhs;
    }

    public ExpressionNode getRHS() {
        return rhs;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public List<CodeNode> getChildren() {
        return null;
    }

    @Override
    public boolean hasReturnValue() {
        return /* definitely */ true;
    }
}
