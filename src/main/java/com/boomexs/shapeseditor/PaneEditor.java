package com.boomexs.shapeseditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PaneEditor {
    static Tool tool = Tool.EDIT;
    private TabStructure tabStructure;
    private Scene scene;
    private Controller controller;

    public PaneEditor(Scene scene, Controller controller, TabStructure tabStructure) {
        this.scene = scene;
        this.controller = controller;
        this.tabStructure = tabStructure;
        addPaneClickEventHandlers();
        System.out.println("created new PaneEditor");
    }

    public static void addToolChangeUpdater(Controller controller) {
        controller.tool.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData() instanceof Tool) {
                    tool = (Tool) newValue.getUserData();
                }
                ;
            }
        });
    }

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

    public void killGUI() {
        controller.rightSidebar.getChildren().clear();
    }

    public void addGUI() {
        tabStructure.elf.entityList.getEntities().forEach(entity -> {
            controller.rightSidebar.getChildren().add(new Button(entity.toString()));
        });

    }

    public void addEntity(Entity entity) {
        Pane pane = getPaneFromTabStructure(tabStructure);
        tabStructure.elf.entityList.addEntity(entity);
        pane.getChildren().add(entity.reference);
    }

    public void removeEntity(Entity entity) {
        Pane pane = getPaneFromTabStructure(tabStructure);
        tabStructure.elf.entityList.removeEntity(entity);
        pane.getChildren().remove(entity.reference);
    }

    private void addPaneClickEventHandlers() throws IllegalArgumentException {
        Pane pane = getPaneFromTabStructure(tabStructure);

        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("clicked");
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("primary button pressed");
                toolRouter(event);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("secondary button pressed");
                for (Entity entity : tabStructure.elf.entityList.getEntities()) {
                    System.out.println(entity.reference);
                    System.out.println(event.getTarget());
                    if (entity.reference == event.getTarget()) {

                        removeEntity(entity);
                        killGUI();
                        addGUI();
                        break;
                    }
                }
            }
        });
    }

    private void toolRouter(MouseEvent event) {
//        System.out.println(event.getTarget() == tabStructure.elf.entityList.getEntities().get(0).reference);
        Pane pane = getPaneFromTabStructure(tabStructure);

        if (pane.getWidth() < event.getX() || pane.getHeight() < event.getY() || 0 > event.getX() || 0 > event.getY()) {
            System.out.println("OUT OF BOUNDS");
            return;
        }
        Entity entity;
        switch (tool) {
            case EDIT:
                System.out.println("EDIT");
                break;
            case CIRCLE:
                System.out.println("CIRCLE");
                entity = new ECircle((short) 0, (short) 0, (short) 0, event.getX(), event.getY(), 16);
                addEntity(entity);
                System.out.println(entity.reference);
                break;
            case RECTANGLE:
                entity = new ERectangle((short) 0, (short) 0, (short) 0, event.getX(), event.getY(), 16, 16);
                addEntity(entity);
                System.out.println(entity.reference);
                break;
            case TRIANGLE:
                entity = new ETriangle((short) 0, (short) 0, (short) 0,
                        event.getX() - 16, event.getY() + 16,
                        event.getX(), event.getY() - 16,
                        event.getX() + 16, event.getY() + 16);
                addEntity(entity);
                System.out.println(entity.reference);
                break;
            default:
                System.out.println("Unknown tool");
        }
        killGUI();
        addGUI();
    }
}
