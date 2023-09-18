package dev.projects.sspSoft.javaCore.ec6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.InvocationTargetException;

public class StringConsoleObjectTest {
    @ParameterizedTest
    @ValueSource(strings = {"dev.projects.sspSoft.javaCore.ec6.StringConsoleObject"})
    public void loadFromFileClassTest(String name) {
        try {
            StringConsoleClassLoader classLoader = new StringConsoleClassLoader();

            Class<?> clazz = classLoader.findClass(name);

            Object object = clazz.getConstructor().newInstance();

            Assertions.assertEquals("message", object.toString());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
