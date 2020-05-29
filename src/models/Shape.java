package models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class Shape {
    protected DoubleProperty xStarting;
    protected DoubleProperty yStarting;
    protected DoubleProperty xEnding;
    protected DoubleProperty yEnding;
    protected ObjectProperty<Color> fillColor;

    public Shape(double xStarting, double yStarting, double xEnding, double yEnding, Color fillColor) {
        this.xStarting = new SimpleDoubleProperty(xStarting);
        this.yStarting = new SimpleDoubleProperty(yStarting);
        this.xEnding = new SimpleDoubleProperty(xEnding);
        this.yEnding = new SimpleDoubleProperty(yEnding);
        this.fillColor = new SimpleObjectProperty<>(fillColor);
    }

    public double getxStarting() {
        return xStarting.get();
    }

    public DoubleProperty xStartingProperty() {
        return xStarting;
    }

    public double getyStarting() {
        return yStarting.get();
    }

    public DoubleProperty yStartingProperty() {
        return yStarting;
    }

    public double getxEnding() {
        return xEnding.get();
    }

    public DoubleProperty xEndingProperty() {
        return xEnding;
    }

    public double getyEnding() {
        return yEnding.get();
    }

    public DoubleProperty yEndingProperty() {
        return yEnding;
    }

    public void setxStarting(double xStarting) {
        this.xStarting.set(xStarting);
    }

    public void setyStarting(double yStarting) {
        this.yStarting.set(yStarting);
    }

    public void setxEnding(double xEnding) {
        this.xEnding.set(xEnding);
    }

    public void setyEnding(double yEnding) {
        this.yEnding.set(yEnding);
    }

    public Color getFillColor() {
        return fillColor.get();
    }

    public ObjectProperty<Color> fillColorProperty() {
        return fillColor;
    }
}
