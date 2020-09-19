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

package ozonelang.ozone.core.runtime.classloader;

import ozonelang.ozone.core.lexer.Context;

import java.lang.reflect.Method;
import java.util.HashMap;

public class MethodResolver {
    private String packagePath;
    private final HashMap<String, Method> methods;
    private final HashMap<String, Class<?>> classes;
    private final Context[] declarations;

    public MethodResolver(Context... decls) {
        this.declarations = decls;
        this.methods = new HashMap<>();
        this.classes = new HashMap<>();
    }

    public void addClass(Class<?> clazz) {
        this.classes.put(clazz.getName(), clazz);
    }

    public void addMethod(Method method) {
        this.methods.put(method.getName(), method);
    }
}
