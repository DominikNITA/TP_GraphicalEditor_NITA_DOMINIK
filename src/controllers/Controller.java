package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
    double offsetX = 0;
    double offsetY = 0;
    double offsetEndX = 0;
    double offsetEndY = 0;

    @FXML
    public void initialize() {
        //Initial setup
        ColorSelection.setValue(Color.BLACK);
        Drawing drawing = new Drawing();

        //DrawingArea.setClip(new Rectangle(DrawingArea.getWidth(), DrawingArea.getHeight()));
        DrawingArea.setViewOrder(2);

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
                startingX = event.getX();
                startingY = event.getY();
                if (LineRadio.isSelected()) {
                    currentShape = new Line(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
                    drawing.addShape(currentShape);
                    DrawingArea.getChildren().add(createViewFromShape(currentShape));
                } else if (RectangleRadio.isSelected()) {
                    currentShape = new Rectangle(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
                    drawing.addShape(currentShape);
                    DrawingArea.getChildren().add(createViewFromShape(currentShape));
                } else if (EllipseRadio.isSelected()) {
                    currentShape = new Ellipse(event.getX(), event.getY(), event.getX(), event.getY(), ColorSelection.getValue());
                    drawing.addShape(currentShape);
                    DrawingArea.getChildren().add(createViewFromShape(currentShape));
                }
            }
        });

        DrawingArea.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (currentShape != null && !SelectMoveRadio.isSelected()) {
                    if (currentShape instanceof Line) {
                        currentShape.setxEnding(event.getX());
                        currentShape.setyEnding(event.getY());
                        return;
                    }
                    double dx = event.getX() - startingX;
                    double dy = event.getY() - startingY;
                    System.out.println("dx: " + dx);
                    //TODO: check for overflow
                    if (dx < 0) {
                        currentShape.setxEnding(startingX);
                        currentShape.setxStarting(event.getX());
                    } else {
                        currentShape.setxEnding(event.getX());
                    }
                    if (dy < 0) {
                        currentShape.setyEnding(startingY);
                        currentShape.setyStarting(event.getY());
                    } else {
                        currentShape.setyEnding(event.getY());
                    }
                }
            }
        });

        DrawingArea.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!SelectMoveRadio.isSelected()){
                    currentShape = null;
                    startingX = 0;
                    startingY = 0;
                    drawing.setAllShapesStylesToUnselected();
                }
            }
        });

        DrawingArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(SelectMoveRadio.isSelected()){
                    currentShape = null;
                    startingX = 0;
                    startingY = 0;
                    drawing.setAllShapesStylesToUnselected();
                    System.out.println("In leaving!");
                }
            }
        });

        ColorSelection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(currentShape != null){
                    currentShape.setFillColor(ColorSelection.getValue());
                }
            }
        });
    }

    private javafx.scene.shape.Shape createViewFromShape(Shape shape) {
        javafx.scene.shape.Shape view = null;
        if (shape instanceof Line) {
            javafx.scene.shape.Line line = new javafx.scene.shape.Line(shape.getxStarting(), shape.getyStarting(), shape.getxEnding(), shape.getyEnding());
            line.endXProperty().bind(shape.xEndingProperty());
            line.endYProperty().bind(shape.yEndingProperty());
            line.startXProperty().bind(shape.xStartingProperty());
            line.startYProperty().bind(shape.yStartingProperty());
            line.strokeProperty().bind(shape.fillColorProperty());
            line.strokeWidthProperty().bind(shape.strokeWidthProperty());
            view = line;
        } else if (shape instanceof Rectangle) {
            javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(shape.getxStarting(), shape.getyStarting(), shape.getxEnding(), shape.getyEnding());
            rectangle.widthProperty().bind(((Rectangle) shape).widthProperty());
            rectangle.heightProperty().bind(((Rectangle) shape).heightProperty());
            rectangle.xProperty().bind(shape.xStartingProperty());
            rectangle.yProperty().bind(shape.yStartingProperty());
            rectangle.strokeProperty().bind(shape.strokeColorProperty());
            rectangle.strokeWidthProperty().bind(shape.strokeWidthProperty());
            view = rectangle;
        } else if (shape instanceof Ellipse) {
            javafx.scene.shape.Ellipse ellipse = new javafx.scene.shape.Ellipse(shape.getxStarting(), shape.getyStarting(), 0, 0);
            ellipse.radiusXProperty().bind(((Ellipse) shape).widthProperty());
            ellipse.radiusYProperty().bind(((Ellipse) shape).heightProperty());
            ellipse.centerXProperty().bind(shape.xStartingProperty());
            ellipse.centerYProperty().bind(shape.yStartingProperty());
            ellipse.strokeProperty().bind(shape.strokeColorProperty());
            ellipse.strokeWidthProperty().bind(shape.strokeWidthProperty());
            view = ellipse;
        }
        view.fillProperty().bind(shape.fillColorProperty());
        view.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (SelectMoveRadio.isSelected()) {
                    currentShape = shape;
                    shape.changeStyleForSelected();
                    startingX = event.getX();
                    startingY = event.getY();
                    offsetX = startingX - shape.getxStarting();
                    offsetY = startingY - shape.getyStarting();
                    offsetEndX = startingX - shape.getxEnding();
                    offsetEndY = startingY - shape.getyEnding();
                }
            }
        });
        view.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(SelectMoveRadio.isSelected()){
                    shape.setxStarting(event.getX()-offsetX);
                    shape.setyStarting(event.getY()-offsetY);
                    if(currentShape instanceof Line){
                        shape.setxEnding(event.getX()-offsetEndX);
                        shape.setyEnding(event.getY()-offsetEndY);
                    }

                }
            }
        });
        return view;
    }
}
