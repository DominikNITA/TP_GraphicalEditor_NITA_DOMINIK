package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import models.Drawing;
import models.Line;
import models.Rectangle;
import models.Shape;

public class Controller {
    @FXML
    RadioButton SelectMoveRadio;
    @FXML
    RadioButton EllipseRadio;
    @FXML
    RadioButton RectangleRadio;
    @FXML
    RadioButton LineRadio;
    @FXML
    ColorPicker ColorSelection;
    @FXML
    Button DeleteButton;
    @FXML
    Button CloneButton;
    @FXML
    Pane DrawingArea;
    @FXML
    ToggleGroup groupAction;

    Shape currentShape = null;

    @FXML
    public void initialize() {
        //Initial setup
        ColorSelection.setValue(Color.BLACK);
        Drawing drawing = new Drawing();

        //DrawingArea.setClip(new Rectangle(DrawingArea.getWidth(), DrawingArea.getHeight()));

        groupAction.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue == SelectMoveRadio) {
                    DeleteButton.setDisable(false);
                    CloneButton.setDisable(false);
                } else {
                    DeleteButton.setDisable(true);
                    CloneButton.setDisable(true);
                }
            }
        });
        EllipseRadio.setSelected(true);

        DrawingArea.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (LineRadio.isSelected()) {
                    currentShape = new Line(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
                    drawing.addShape(currentShape);
                    DrawingArea.getChildren().add(createViewFromShape(currentShape));
                }else if(RectangleRadio.isSelected()){
                    currentShape = new Rectangle(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
                    drawing.addShape(currentShape);
                    DrawingArea.getChildren().add(createViewFromShape(currentShape));
                }
            }
        });

        DrawingArea.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentShape != null) {
                    currentShape.setxEnding(event.getX());
                    currentShape.setyEnding(event.getY());
                }
            }
        });

        DrawingArea.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentShape = null;
            }
        });

    }

    private javafx.scene.shape.Shape createViewFromShape(Shape shape) {
        javafx.scene.shape.Shape view = null;
        if (shape instanceof Line) {
            javafx.scene.shape.Line line = new javafx.scene.shape.Line(shape.getxStarting(), shape.getyStarting(), shape.getxEnding(), shape.getyEnding());
            line.endXProperty().bind(shape.xEndingProperty());
            line.endYProperty().bind(shape.yEndingProperty());
            line.setStrokeWidth(3);
            line.strokeProperty().bind(shape.fillColorProperty());
            view = line;
        } else if (shape instanceof Rectangle) {
            javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(shape.getxStarting(), shape.getyStarting(), shape.getxEnding(), shape.getyEnding());
            rectangle.widthProperty().bind(((Rectangle)shape).widthProperty());
            rectangle.heightProperty().bind(((Rectangle)shape).heightProperty());
//            rectangle.xProperty().bind(shape.xStartingProperty());
//            rectangle.yProperty().bind(shape.yStartingProperty());
            view = rectangle;
        }
        view.fillProperty().bind(shape.fillColorProperty());
        return view;
    }
}
