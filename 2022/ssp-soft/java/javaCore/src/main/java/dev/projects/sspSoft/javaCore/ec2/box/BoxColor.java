package dev.projects.sspSoft.javaCore.ec2.box;

import lombok.Getter;
import lombok.Setter;

public enum BoxColor {
    RED(1),
    GREEN(2),
    BLUE(3);

    @Getter
    @Setter
    private int value = -1;

    BoxColor(int value) {
        setValue(value);
    }
}
