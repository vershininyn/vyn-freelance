package dev.projects.sspSoft.javaCore.ec2.figure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * Создать два иммутабельных класса Круг и Квадрат. Для каждой фигуры необходимо задавать размер
 * и выполнять расчет периметра и площади фигуры.
 * Также необходимо учесть, что такие фигуры может потребоваться хранить
 * в коллекции типа Set.
 */
public class BoxFigureTests {
    @Test
    public void boxTest() {
        BoxFigure box = new BoxFigure(3.0);

        Assertions.assertEquals(4.0*3.0, box.getPerimeter());
        Assertions.assertEquals(3.0*3.0, box.getSquare());
    }

    @Test
    public void boxDuplicateTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Set.of(new BoxFigure(1.0),
                    new BoxFigure(1.0),
                    new BoxFigure(2.0),
                    new BoxFigure(3.0));
        });
    }
}
