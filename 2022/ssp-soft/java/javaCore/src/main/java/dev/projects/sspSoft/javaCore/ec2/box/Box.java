package dev.projects.sspSoft.javaCore.ec2.box;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Box implements Comparable<Box> {
    private int size = 0;
    private BoxColor color = BoxColor.RED;

    public Box(int size, BoxColor color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public int compareTo(Box object) {
        Objects.requireNonNull(object);

        return (getSize() > object.getSize()) ? (-1) : ((getSize() < object.getSize()) ? (1) : (compareByColor(object)));
    }

    private int compareByColor(Box object) {
        int currentValue = getColor().getValue(),
                otherValue = object.getColor().getValue();

        return Integer.compare(currentValue, otherValue);
    }
}
