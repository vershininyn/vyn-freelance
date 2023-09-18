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
public class CircleFigureTests {
    @Test
    public void circleTest() {
        CircleFigure circle = new CircleFigure(3.0);

        Assertions.assertEquals(2.0*Math.PI*3.0, circle.getPerimeter());
        Assertions.assertEquals(Math.PI*3.0*3.0, circle.getSquare());
    }

    @Test
    public void circleDuplicateTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Set.of(new CircleFigure(1.0),
                    new CircleFigure(1.0),
                    new CircleFigure(2.0),
                    new CircleFigure(3.0));
        });
    }
}
