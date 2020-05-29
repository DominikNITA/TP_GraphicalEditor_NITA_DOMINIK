package models;

import javafx.scene.paint.Color;

public class Line extends Shape{
    public Line(double xStarting, double yStarting, double xEnding, double yEnding, Color fillColor) {
        super(xStarting, yStarting, xEnding, yEnding, fillColor);
        changeStyleForUnSelected();
    }

    @Override
    public void changeStyleForSelected() {
        setStrokeWidth(5);
    }

    @Override
    public void changeStyleForUnSelected() {
        setStrokeWidth(3);
    }

    @Override
    public Object clone() {
        return new Line(getXStarting(), getYStarting(), getXEnding(), getYEnding(), getFillColor());
    }
}
