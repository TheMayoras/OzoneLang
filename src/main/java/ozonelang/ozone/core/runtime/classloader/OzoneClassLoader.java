package ozonelang.ozone.core.runtime.classloader;

import ozonelang.ozone.core.AST.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
