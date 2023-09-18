package dev.projects.sspSoft.javaCore.ec6;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StringConsoleClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) {
        byte[] classBytes = loadClassFromFile(name);

        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadClassFromFile(String fileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                fileName.replace('.', File.separatorChar) + ".class");
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        int nextValue = 0;
        try {
            while ((nextValue = inputStream.read()) != -1) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteStream.toByteArray();
    }
}
