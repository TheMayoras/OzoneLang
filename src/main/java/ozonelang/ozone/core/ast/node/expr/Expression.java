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

package ozonelang.ozone.core.ast.node.expr;

import ozonelang.ozone.core.ast.node.CodeNode;
import ozonelang.ozone.core.lexer.Context;

public abstract class Expression extends CodeNode implements Precedence {
    public enum Type {
        ASSIGN,
        BINARY,
        BITWISE,
        FUNCCALL,
        IFEXPRESSION,
        LITERAL,
        LOGICAL,
        OBJECTCREATION,
        PARENTHESES,
        ROOT,
        UNARY,
    }

    protected final Type type;
    protected final int precedence;

    public Expression(Type type, int precedence, CodeNode parent, Context... contexts) {
        super(parent, contexts);
        this.type = type;
        this.precedence = precedence;
    }

    @Override
    public void addChild(CodeNode child) {
        if (!(child instanceof Expression))
            throw new RuntimeException(String.format("unexpected type: '%s'", child.getClass().getSimpleName()));
        super.addChild(child);
    }

    public abstract <T> Object evaluate();

    public final int getPrecedence() {
        return precedence;
    }

    public final Type getType() {
        return type;
    }
}
