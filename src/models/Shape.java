package models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class Shape implements Cloneable {
    protected DoubleProperty xStarting;
    protected DoubleProperty yStarting;
    protected DoubleProperty xEnding;
    protected DoubleProperty yEnding;
    protected ObjectProperty<Color> fillColor;
    protected DoubleProperty strokeWidth;
    protected ObjectProperty<Color> strokeColor;

    public int id;

    public Shape(double xStarting, double yStarting, double xEnding, double yEnding, Color fillColor) {
        this.xStarting = new SimpleDoubleProperty(xStarting);
        this.yStarting = new SimpleDoubleProperty(yStarting);
        this.xEnding = new SimpleDoubleProperty(xEnding);
        this.yEnding = new SimpleDoubleProperty(yEnding);
        this.fillColor = new SimpleObjectProperty<>(fillColor);
        strokeWidth = new SimpleDoubleProperty(0);
        strokeColor = new SimpleObjectProperty<>(Color.BLACK);
        changeStyleForUnSelected();
    }

    public void changeStyleForSelected() {
        setStrokeWidth(3);
        setStrokeColor(Color.YELLOW);
    }

    public void changeStyleForUnSelected() {
        setStrokeWidth(2);
        setStrokeColor(Color.BLACK);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor.set(fillColor);
    }

    public double getStrokeWidth() {
        return strokeWidth.get();
    }

    public DoubleProperty strokeWidthProperty() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth.set(strokeWidth);
    }

    public Color getStrokeColor() {
        return strokeColor.get();
    }

    public ObjectProperty<Color> strokeColorProperty() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor.set(strokeColor);
    }

    public double getXStarting() {
        return xStarting.get();
    }

    public DoubleProperty xStartingProperty() {
        return xStarting;
    }

    public double getYStarting() {
        return yStarting.get();
    }

    public DoubleProperty yStartingProperty() {
        return yStarting;
    }

    public double getXEnding() {
        return xEnding.get();
    }

    public DoubleProperty xEndingProperty() {
        return xEnding;
    }

    public double getYEnding() {
        return yEnding.get();
    }

    public DoubleProperty yEndingProperty() {
        return yEnding;
    }

    public void setXStarting(double xStarting) {
        this.xStarting.set(xStarting);
    }

    public void setYStarting(double yStarting) {
        this.yStarting.set(yStarting);
    }

    public void setXEnding(double xEnding) {
        this.xEnding.set(xEnding);
    }

    public void setYEnding(double yEnding) {
        this.yEnding.set(yEnding);
    }

    public Color getFillColor() {
        return fillColor.get();
    }

    public ObjectProperty<Color> fillColorProperty() {
        return fillColor;
    }

    public Object clone() {
        return new Shape(getXStarting(), getYStarting(), getXEnding(), getYEnding(), getFillColor());
    }
}
