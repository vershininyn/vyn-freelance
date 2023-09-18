package dev.projects.sspSoft.javaCore.ec2.box;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BoxTests {
    /**
     * Создть класс Коробка, которая имеет два поля - цвет и размер.
     * Коробки могут иметь 3 возможных цвета: красный, зеленый, синий.
     * Реализуйте класс таким образом, чтобы при выполнении сортировки массива с коробками
     * с помощью метода Arrays.sort(), коробки сортировались от большей к меньшей.
     * Если коробки имеют одинаковый размер, то сортируются по цвету Red > Green > Blue;
     */
    @Test
    public void boxTest() {
        Box[] boxes = new Box[]{new Box(1, BoxColor.RED),
                new Box(2 , BoxColor.BLUE),
                new Box(2 , BoxColor.GREEN),
                new Box(2 , BoxColor.RED),
                new Box(3, BoxColor.RED)};

        Arrays.sort(boxes);

        Assertions.assertEquals(3 , boxes[0].getSize());
        Assertions.assertEquals(BoxColor.RED , boxes[0].getColor());

        Assertions.assertEquals(2 , boxes[1].getSize());
        Assertions.assertEquals(BoxColor.RED , boxes[1].getColor());

        Assertions.assertEquals(2 , boxes[2].getSize());
        Assertions.assertEquals(BoxColor.GREEN , boxes[2].getColor());

        Assertions.assertEquals(2 , boxes[3].getSize());
        Assertions.assertEquals(BoxColor.BLUE , boxes[3].getColor());

        Assertions.assertEquals(1 , boxes[4].getSize());
        Assertions.assertEquals(BoxColor.RED , boxes[4].getColor());
    }
}
