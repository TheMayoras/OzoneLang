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
import ozonelang.ozone.core.lexer.Context;
import ozonelang.ozone.core.lexer.Token;
import ozonelang.ozone.core.runtime.type.OzObject;

import java.util.List;

public abstract class ExpressionNode extends CodeNode {
    protected Token rhs;
    protected Token lhs;
    protected String operator;

    public ExpressionNode(CodeNode parent, String op, Token rhs, Token lhs) {
        super(parent, parent.getContextsArr());
        this.rhs = rhs;
        this.lhs = lhs;
        operator = op;
    }

    public abstract OzObject evaluate();

    public abstract NodePriority getPriority();

    public Token getLHS() {
        return lhs;
    }

    public Token getRHS() {
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
