package controllers;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import models.*;

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
    double startingX = 0;
    double startingY = 0;
    //offsets used for all movements
    double offsetX = 0;
    double offsetY = 0;
    //offsets used for Line movement
    double offsetEndX = 0;
    double offsetEndY = 0;

    @FXML
    public void initialize() {
        //Initial setup
        ColorSelection.setValue(Color.BLANCHEDALMOND);
        Drawing drawing = new Drawing();
        //Overflow
        DrawingArea.setViewOrder(2);

        groupAction.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == SelectMoveRadio) {
                DeleteButton.setDisable(false);
                CloneButton.setDisable(false);
            } else {
                DeleteButton.setDisable(true);
                CloneButton.setDisable(true);
            }
        });
        EllipseRadio.setSelected(true);

        DrawingArea.setOnMousePressed(event -> {
            startingX = event.getX();
            startingY = event.getY();
            if (LineRadio.isSelected()) {
                currentShape = new Line(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
            } else if (RectangleRadio.isSelected()) {
                currentShape = new Rectangle(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
            } else if (EllipseRadio.isSelected()) {
                currentShape = new Ellipse(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
            }
            if(!SelectMoveRadio.isSelected()){
                drawing.addShape(currentShape);
            }
        });

        DrawingArea.setOnMouseDragged(event -> {
            if (currentShape != null && !SelectMoveRadio.isSelected()) {
                if (currentShape instanceof Line) {
                    currentShape.setXEnding(event.getX());
                    currentShape.setYEnding(event.getY());
                    return;
                }
                double dx = event.getX() - startingX;
                double dy = event.getY() - startingY;
                if (dx < 0) {
                    currentShape.setXEnding(startingX);
                    currentShape.setXStarting(event.getX());
                } else {
                    currentShape.setXEnding(event.getX());
                }
                if (dy < 0) {
                    currentShape.setYEnding(startingY);
                    currentShape.setYStarting(event.getY());
                } else {
                    currentShape.setYEnding(event.getY());
                }
            }
        });

        DrawingArea.setOnMouseReleased(event -> {
            if(!SelectMoveRadio.isSelected()){
                currentShape = null;
                startingX = 0;
                startingY = 0;
                drawing.setAllShapesStylesToUnselected();
            }
        });

        DrawingArea.setOnMouseClicked(event -> {
            if(SelectMoveRadio.isSelected()){
                currentShape = null;
                startingX = 0;
                startingY = 0;
                drawing.setAllShapesStylesToUnselected();
            }
        });

        ColorSelection.setOnAction(event -> {
            if(currentShape != null){
                currentShape.setFillColor(ColorSelection.getValue());
            }
        });

        DeleteButton.setOnMouseClicked(event -> {
            if(currentShape != null){
                //TODO: Stop event bubbling........
                drawing.setAllShapesStylesToUnselected();
                drawing.removeShape(currentShape);
            }
        });

        CloneButton.setOnMouseClicked(event -> {
            if(currentShape != null){
                //TODO: Cloning not working properly
                Shape clonedShape = (Shape)currentShape.clone();
                clonedShape.setXStarting(clonedShape.getXStarting()+10);
                clonedShape.setYStarting(clonedShape.getYStarting()+10);
                drawing.addShape(clonedShape);
            }
        });

        drawing.getShapes().addListener((ListChangeListener<Shape>) event -> {
            if(event.next()){
                if(event.wasRemoved()){
                    for(Shape deletedShape: event.getRemoved()){
                        DrawingArea.getChildren().remove(deletedShape.id);
                    }
                }
                if(event.wasAdded()){
                    for(Shape addedShape: event.getAddedSubList()){
                        DrawingArea.getChildren().add(createViewFromShape(addedShape));
                    }
                }
            }
        }
        );
    }

    private javafx.scene.shape.Shape createViewFromShape(Shape shape) {
        javafx.scene.shape.Shape view;
        if (shape instanceof Line) {
            javafx.scene.shape.Line line = new javafx.scene.shape.Line(shape.getXStarting(), shape.getYStarting(), shape.getXEnding(), shape.getYEnding());
            line.endXProperty().bind(shape.xEndingProperty());
            line.endYProperty().bind(shape.yEndingProperty());
            line.startXProperty().bind(shape.xStartingProperty());
            line.startYProperty().bind(shape.yStartingProperty());
            line.strokeProperty().bind(shape.fillColorProperty());
            line.strokeWidthProperty().bind(shape.strokeWidthProperty());
            view = line;
        } else if (shape instanceof Rectangle) {
            javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(shape.getXStarting(), shape.getYStarting(), shape.getXEnding(), shape.getYEnding());
            rectangle.widthProperty().bind(((Rectangle) shape).widthProperty());
            rectangle.heightProperty().bind(((Rectangle) shape).heightProperty());
            rectangle.xProperty().bind(shape.xStartingProperty());
            rectangle.yProperty().bind(shape.yStartingProperty());
            rectangle.strokeProperty().bind(shape.strokeColorProperty());
            rectangle.strokeWidthProperty().bind(shape.strokeWidthProperty());
            view = rectangle;
        } else if (shape instanceof Ellipse) {
            javafx.scene.shape.Ellipse ellipse = new javafx.scene.shape.Ellipse(shape.getXStarting(), shape.getYStarting(), 0, 0);
            ellipse.radiusXProperty().bind(((Ellipse) shape).widthProperty());
            ellipse.radiusYProperty().bind(((Ellipse) shape).heightProperty());
            ellipse.centerXProperty().bind(shape.xStartingProperty());
            ellipse.centerYProperty().bind(shape.yStartingProperty());
            ellipse.strokeProperty().bind(shape.strokeColorProperty());
            ellipse.strokeWidthProperty().bind(shape.strokeWidthProperty());
            view = ellipse;
        }
        else{
            return null;
        }
        view.fillProperty().bind(shape.fillColorProperty());
        view.setOnMousePressed(event -> {
            if (SelectMoveRadio.isSelected()) {
                currentShape = shape;
                shape.changeStyleForSelected();
                startingX = event.getX();
                startingY = event.getY();
                offsetX = startingX - shape.getXStarting();
                offsetY = startingY - shape.getYStarting();
                offsetEndX = startingX - shape.getXEnding();
                offsetEndY = startingY - shape.getYEnding();
            }
        });
        view.setOnMouseDragged(event -> {
            if(SelectMoveRadio.isSelected()){
                shape.setXStarting(event.getX()-offsetX);
                shape.setYStarting(event.getY()-offsetY);
                if(currentShape instanceof Line){
                    shape.setXEnding(event.getX()-offsetEndX);
                    shape.setYEnding(event.getY()-offsetEndY);
                }
            }
        });
        return view;
    }
}
