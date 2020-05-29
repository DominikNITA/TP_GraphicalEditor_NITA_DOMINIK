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
    public void setXEnding(double xEnding) {
        super.setXEnding(xEnding);
        setWidth(Math.abs(getXEnding()- getXStarting()));
    }

    @Override
    public void setYEnding(double yEnding) {
        super.setYEnding(yEnding);
        setHeight(Math.abs(getYEnding()- getYStarting()));
    }

    @Override
    public Object clone() {
        return new Rectangle(getXStarting(), getYStarting(), getXEnding(), getYEnding(), getFillColor());
    }
}