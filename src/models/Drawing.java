package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Drawing {
    ObservableList<Shape> shapes;
    public Drawing(){
        shapes = FXCollections.observableArrayList();
    }

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public void removeShape(Shape shape){
        shapes.remove(shape);
    }
}
