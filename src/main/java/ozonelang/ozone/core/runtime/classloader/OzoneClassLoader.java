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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;


public class OzoneClassLoader extends ClassLoader {
    @Override
    public Class<?> findClass(String name) {
        var bt = loadClassData(name);
        return defineClass(name, bt, 0, bt.length);
    }

    public byte[] loadClassData(String className, Context... decls) {
        var stream =
                        getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                                className.replace(".", "/") + ".class"
                        );
        var byteStream = new ByteArrayOutputStream();
        var len = 0;
        try {
            while ((len = ((InputStream) stream).read()) != -1) {
                byteStream.write(len);
            }
        } catch (IOException e) {
            raiseEx(e, true, decls);
        }
        return byteStream.toByteArray();
    }
}
