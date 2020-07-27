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

import ozonelang.ozone.core.runtime.type.OzObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.Entry;

public class FunctionCallNode {
    public static class FormalArgsList {
        private final List<OzObject> args;
        private final Map<String, OzObject> kwargs;

        @SafeVarargs
        public FormalArgsList(List<OzObject> args, Entry<String, OzObject>... kwargs) {
            this.args = args;
            this.kwargs = new HashMap<>();
            for (var kwarg : kwargs) {
                this.kwargs.put(kwarg.getKey(), kwarg.getValue());
            }
        }

        public Collection<OzObject> getArgs() {
            return args;
        }

        public Map<String, OzObject> getKwargs() {
            return kwargs;
        }
    }
}
