package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Drawing {
    ObservableList<Shape> shapes;
    public Drawing(){
        shapes = FXCollections.observableArrayList();
    }

    public void addShape(Shape shape){
        shape.id = shapes.size()-1;
        shapes.add(shape);
    }

    public void removeShape(Shape shape){
        shapes.remove(shape);
    }

    public void setAllShapesStylesToUnselected(){
        for (Shape shape: shapes){
            shape.changeStyleForUnSelected();
        }
    }

    public ObservableList<Shape> getShapes() {
        return shapes;
    }
}
