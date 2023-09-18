package dev.projects.sspSoft.javaCore.ec2.figure;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class AbstractFigure {
    private double size = 0;

    public AbstractFigure(double size) {
        setSize(size);
    }

    public abstract double getPerimeter();

    public abstract double getSquare();

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getPerimeter(), getSquare());
    }
}
