package dev.projects.sspSoft.javaCore.ec2.figure;

public final class BoxFigure extends AbstractFigure implements Figure {
    public BoxFigure(double size) {
        super(size);
    }

    @Override
    public double getPerimeter() {
        return 4.0 * getSize();
    }

    @Override
    public double getSquare() {
        return getSize() * getSize();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if (!(obj instanceof BoxFigure)) return false;

        BoxFigure figure = (BoxFigure) obj;

        return (getSize() == figure.getSize())
                && (getPerimeter() == figure.getPerimeter())
                && (getSquare() == figure.getSquare());
    }
}
