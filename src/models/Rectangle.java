package models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {
    public Rectangle(double xStarting, double yStarting, double xEnding, double yEnding, Color fillColor) {
        super(xStarting, yStarting, xEnding, yEnding, fillColor);
        width = new SimpleDoubleProperty(0);
        height = new SimpleDoubleProperty(0);
    }

    protected DoubleProperty width;
    protected DoubleProperty height;

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    @Override
    public void setxEnding(double xEnding) {
        super.setxEnding(xEnding);
        setWidth(getxEnding()-getxStarting());
    }

    @Override
    public void setyEnding(double yEnding) {
        super.setyEnding(yEnding);
        setHeight(getyEnding()-getyStarting());
    }
}
