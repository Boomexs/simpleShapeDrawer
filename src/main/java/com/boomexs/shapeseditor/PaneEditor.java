package com.boomexs.shapeseditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Popup;

/**
 * The {@code PaneEditor} class provides the functionality for editing a pane in a graphical
 * shapes editor application. It handles user interactions, such as mouse events and tool changes,
 * and manages entities within the pane.
 */
public class PaneEditor {
    static Tool tool = Tool.EDIT;
    private final TabStructure tabStructure;
    private final Scene scene;
    private final Controller controller;
    private final ColorPicker colorPicker = new ColorPicker();
    private final Popup popup = new Popup();
    private Entity focusedEntity;
    private Point2D clickPosition = null;
    private Point2D currentPostition = null;
    private Point2D lastPosition = null;

    /**
     * Constructs a new {@code PaneEditor} with the specified scene, controller, and tab structure.
     *
     * @param scene        the scene to which this editor is attached
     * @param controller   the controller managing the editor
     * @param tabStructure the tab structure containing the pane to be edited
     */
    public PaneEditor(Scene scene, Controller controller, TabStructure tabStructure) {
        this.scene = scene;
        this.controller = controller;
        this.tabStructure = tabStructure;
        initPopup();
        addPaneClickEventHandlers();
        System.out.println("created new PaneEditor");
    }

    /**
     * Adds a listener to update the current tool when the selected toggle changes.
     *
     * @param controller the controller containing the tool selection toggle group
     */
    public static void addToolChangeUpdater(Controller controller) {
        controller.tool.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData() instanceof Tool) {
                    tool = (Tool) newValue.getUserData();
                }
            }
        });
    }

    /**
     * Retrieves the pane from the specified tab structure.
     *
     * @param tabStructure the tab structure containing the pane
     * @return the pane within the tab structure
     * @throws IllegalArgumentException if the content of the tab structure is not a {@code Pane}
     */
    private static Pane getPaneFromTabStructure(TabStructure tabStructure) {
        Node node = tabStructure.tab.getContent();
        ScrollPane scrollPane;
        if (node instanceof ScrollPane) {
            scrollPane = (ScrollPane) node;
        } else {
            throw new IllegalArgumentException("Loaded file is incorrect");
        }
        node = scrollPane.getContent();
        Pane pane;
        if (node instanceof Pane) {
            pane = (Pane) node;
        } else {
            throw new IllegalArgumentException("Loaded file is incorrect");
        }
        return pane;
    }

    /**
     * Clears the right sidebar in the GUI.
     */
    public void killGUI() {
        controller.rightSidebar.getChildren().clear();
    }

    /**
     * Populates the right sidebar in the GUI with buttons representing the entities in the pane.
     */
    public void addGUI() {
        tabStructure.elf.entityList.getEntities().forEach(entity -> {
            Button button = new Button(entity.toString());
            button.setOnAction(event -> {
                removeEntity(entity);
                killGUI();
                addGUI();
            });
            controller.rightSidebar.getChildren().add(button);
        });

    }

    /**
     * Adds the specified entity to the pane and the entity list.
     *
     * @param entity the entity to be added
     */
    public void addEntity(Entity entity) {
        Pane pane = getPaneFromTabStructure(tabStructure);
        tabStructure.elf.entityList.addEntity(entity);
        pane.getChildren().add(entity.reference);
    }

    /**
     * Removes the specified entity from the pane and the entity list.
     *
     * @param entity the entity to be removed
     */
    public void removeEntity(Entity entity) {
        Pane pane = getPaneFromTabStructure(tabStructure);
        tabStructure.elf.entityList.removeEntity(entity);
        pane.getChildren().remove(entity.reference);
    }

    /**
     * Initializes the color picker popup.
     */
    private void initPopup() {
        popup.getContent().add(colorPicker);
        colorPicker.setOnAction(this::onActionColorPicker);
    }

    /**
     * Handles the action event when the color picker value changes.
     *
     * @param actionEvent the action event
     */
    private void onActionColorPicker(ActionEvent actionEvent) {
        if (focusedEntity != null) {
            focusedEntity.setColor(colorPicker.getValue());
        }
        popup.hide();
    }

    /**
     * Adds mouse event handlers to the pane for handling mouse presses, drags, releases, and scrolls.
     *
     * @throws IllegalArgumentException if the pane cannot be retrieved from the tab structure
     */
    private void addPaneClickEventHandlers() throws IllegalArgumentException {
        Pane pane = getPaneFromTabStructure(tabStructure);
        pane.setOnMousePressed(this::handleMousePressed);
        pane.setOnMouseDragged(this::handleMouseDragged);
        pane.setOnMouseReleased(this::handleMouseReleased);
        pane.setOnScroll(this::handleScroll);
    }

    /**
     * Handles scroll events on the pane.
     *
     * @param scrollEvent the scroll event
     */
    private void handleScroll(ScrollEvent scrollEvent) {
        if (focusedEntity == null) {
            return;
        }
        focusedEntity.setRotation(focusedEntity.rotation += scrollEvent.getDeltaY() * 0.15);
        focusedEntity.changeSizeBy(scrollEvent.getDeltaX() * 0.1);
        scrollEvent.consume();
    }

    /**
     * Handles mouse press events on the pane.
     *
     * @param event the mouse event
     */
    private void handleMousePressed(MouseEvent event) {
        popup.hide();
        if (event.getButton() == MouseButton.PRIMARY) {
            clickPosition = new Point2D(event.getX(), event.getY());
            switch (tool) {
                case RECTANGLE -> {
                    focusedEntity = new ERectangle(0, 0, 0, event.getX(), event.getY(), 0, 0);
                    addEntity(focusedEntity);
                }
                case CIRCLE -> {
                    focusedEntity = new ECircle(0, 0, 0, event.getX(), event.getY(), 0);
                    addEntity(focusedEntity);
                }
                case TRIANGLE -> {
                    focusedEntity = new ETriangle(0, 0, 0, 0, 0, 0, 0, 0, 0);
                    addEntity(focusedEntity);
                }
                case EDIT -> focusedEntity = findEntity(event.getTarget());

            }
        }
        if (event.getButton() == MouseButton.SECONDARY) {
            if (event.getTarget() instanceof Shape) {
                focusedEntity = findEntity(event.getTarget());
                popup.setX(event.getScreenX());
                popup.setY(event.getScreenY());
                colorPicker.setValue(Color.color(focusedEntity.r, focusedEntity.g, focusedEntity.b));
                popup.show(scene.getWindow());
            } else {
                popup.hide();
            }
        }
        if (event.getButton() == MouseButton.MIDDLE) {
            if (event.getTarget() instanceof Shape) {
                focusedEntity = findEntity(event.getTarget());
                removeEntity(focusedEntity);
                focusedEntity = null;
                killGUI();
                addGUI();
            }
        }
    }

    /**
     * Handles mouse drag events on the pane.
     *
     * @param event the mouse event
     */
    private void handleMouseDragged(MouseEvent event) {
        currentPostition = new Point2D(event.getX(), event.getY());
        if (event.getButton() == MouseButton.PRIMARY && tool != Tool.EDIT && focusedEntity != null) {
            focusedEntity.drawFromPoints(clickPosition, currentPostition, 0);
        }

        if (event.getButton() == MouseButton.PRIMARY && tool == Tool.EDIT && focusedEntity != null && lastPosition != null) {
            focusedEntity.moveBy(currentPostition.getX() - lastPosition.getX(), currentPostition.getY() - lastPosition.getY());
        }
        lastPosition = new Point2D(currentPostition.getX(), currentPostition.getY());

    }

    /**
     * Handles mouse release events on the pane.
     *
     * @param event the mouse event
     */
    private void handleMouseReleased(MouseEvent event) {
        clickPosition = null;
        currentPostition = null;
        lastPosition = null;
        killGUI();
        addGUI();
    }

    /**
     * Finds the entity corresponding to the specified target.
     *
     * @param target the event target
     * @return the corresponding entity, or {@code null} if no entity is found
     */
    private Entity findEntity(EventTarget target) {
        for (Entity entity : tabStructure.elf.entityList.getEntities()) {
            if (entity.reference == target) {
                return entity;
            }
        }
        return null;
    }
}
