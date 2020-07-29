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

import ozonelang.ozone.core.lexer.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

/**
 * A base class representing a statement
 * or block in source code.
 */
public abstract class CodeNode {
    /**
     * Get the children of this CodeNode
     */
    public abstract List<CodeNode> getChildren();

    /**
     * The parent of this CodeNode
     */
    protected CodeNode parent;

    /**
     * Returns the parent node of this CodeNode.
     * Every CodeNode has a parent, except for {@link RootNode}
     */
    public CodeNode getParent() {
        return parent;
    }

    /**
     * Whether this CodeNode is a value expression or not
     */
    public abstract boolean hasReturnValue();

    /**
     * The contexts of this CodeNode
     */
    protected List<Context> contexts = new ArrayList<>();

    /**
     * Get the contexts of this CodeNode
     * @return The contexts of this CodeNode
     */
    public List<Context> getContexts() {
        return contexts;
    }

    @Override
    public String toString() {
        return String.format("[%s %d:%d-%d:%d]",
                             contexts.get(1).getFile(),
                             contexts.get(1).getStartLine(),
                             contexts.get(1).getStartCol(),
                             contexts.get(1).getStartLine(),
                             contexts.get(1).getEndCol()
        );
    }

    /**
     * Creates a new instance of CodeNode
     *
     * @param contexts The {@link Context} objects
     * this CodeNode is related to
     */
    public CodeNode(CodeNode parent, Context... contexts) {
        this.parent = parent;
        if (contexts.length < 1)
            raiseEx(new RuntimeException(String.format("internal error at file %s", getClass().getName().replace(".", "/"))), true, contexts);
        this.contexts.addAll(Arrays.asList(contexts));
    }
}
