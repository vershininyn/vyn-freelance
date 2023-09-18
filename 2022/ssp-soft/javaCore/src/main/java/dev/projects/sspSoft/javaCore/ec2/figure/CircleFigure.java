package dev.projects.sspSoft.javaCore.ec2.figure;

public final class CircleFigure extends AbstractFigure implements Figure {
    public CircleFigure(double size) {
        super(size);
    }

    @Override
    public double getPerimeter() {
        return 2.0 * Math.PI * getSize();
    }

    @Override
    public double getSquare() {
        return Math.PI * getSize() * getSize();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof CircleFigure)) return false;

        CircleFigure figure = (CircleFigure) obj;

        return (getSize() == figure.getSize())
                && (getPerimeter() == figure.getPerimeter())
                && (getSquare() == figure.getSquare());
    }
}
