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

import java.lang.reflect.Method;
import java.util.HashMap;

import ozonelang.ozone.core.lexer.Context;
import ozonelang.ozone.core.runtime.exception.ErrorConstants;
import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.runtime.exception.StackTrace;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isInterface;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;

public class MethodResolver {
    private String packagePath;
    private HashMap<String, Method> methods;
    private HashMap<String, Class<?>> classes;
    private Context[] declarations;

    public MethodResolver(Context... decls) {
        this.declarations = decls;
        this.methods = new HashMap<>();
        this.classes = new HashMap<>();
    }

    public void addClass(Class<?> clazz) {
        if (isAbstract(clazz.getModifiers()) || isInterface(clazz.getModifiers())) {
            raiseEx(new OzoneException(String.format(
                    "class '%s' cannot be abstract or an interface",
                    clazz.getCanonicalName()),
                    ErrorConstants.CLASSLOADER_E,
                    new StackTrace(declarations), true
            ));
        } else {
            this.classes.put(clazz.getName(), clazz);
        }
    }

    public void addMethod(Method method) {
        this.methods.put(method.getName(), method);
    }
}
