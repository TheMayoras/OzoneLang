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

import ozonelang.ozone.core.ast.fragment.FormalArgsList;
import ozonelang.ozone.core.ast.node.CodeNode;
import ozonelang.ozone.core.lexer.Context;
import ozonelang.ozone.core.runtime.type.OzObject;

import java.util.List;

public final class FunctionCallNode extends CodeNode {
    private final FormalArgsList argsList;

    public FunctionCallNode(FormalArgsList argsList, CodeNode parent, Context... contexts) {
        super(parent, contexts);
        this.argsList = argsList;
    }

    public FunctionCallNode(CodeNode parent, List<Context> contexts, OzObject... args) {
        super(parent, contexts.toArray(new Context[0]));
        argsList = new FormalArgsList(args);
    }

    public FormalArgsList getArgsList() {
        return argsList;
    }

    @Override
    public List<CodeNode> getChildren() {
        return null;
    }
}