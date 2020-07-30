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

package ozonelang.ozone.core.ast.fragment;

import ozonelang.ozone.core.runtime.type.OzObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static ozonelang.ozone.core.runtime.type.OzNothing.ensureNonNull;

public class FormalArgsList {
    private final List<OzObject> args;
    private final Map<String, OzObject> kwargs;

    public FormalArgsList(List<OzObject> args, List<Entry<String, OzObject>> kwargs) {
        for (var arg : args)
            ensureNonNull(arg);
        this.args = args;
        this.kwargs = new HashMap<>();
        for (var kwarg : kwargs) {
            ensureNonNull(kwarg.getValue());
            this.kwargs.put(kwarg.getKey(), kwarg.getValue());
        }
    }

    public FormalArgsList(OzObject... args) {
        this(
            Arrays.asList(args),
            new ArrayList<>()
        );
    }

    public void add(OzObject... args) {
        this.args.addAll(Arrays.asList(args));
    }

    public void add(List<Entry<String, OzObject>> kwargs) {
        for (var kw : kwargs)
            this.kwargs.put(kw.getKey(), kw.getValue());
    }

    public Collection<OzObject> getArgs() {
        return Collections.unmodifiableCollection(args);
    }

    public Map<String, OzObject> getKwargs() {
        return Collections.unmodifiableMap(kwargs);
    }

    public int length() {
        return kwargs.size() + args.size();
    }
}
