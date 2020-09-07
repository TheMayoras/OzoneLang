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
import java.util.Collection;
import java.util.List;

public final class RootNode extends CodeNode {
    RootNode(Context... contexts) {
        super(null, contexts);
    }

    @Override
    public List<CodeNode> getChildren() {
        return null;
    }

    public static RootNode getInstance(CodeNode... children) {
        var contexts = new ArrayList<Context>();
        Arrays.asList(children).forEach(c -> {
            contexts.addAll(c.getContexts());
        });
        return new RootNode(contexts.toArray(Context[]::new));
    }

    public static RootNode getInstance(Collection<CodeNode> children) {
        return getInstance(children.toArray(CodeNode[]::new));
    }
}
