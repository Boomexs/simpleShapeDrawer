package com.boomexs.shapeseditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class TabManager {
    private Scene scene;
    private Controller controller;

    private List<TabStructure> tabs = new ArrayList<>();

    private PaneEditor paneEditor;

    // CURRENT TAB DEPENDENT
    private EntityListFile currentFile;

    public TabManager(Scene scene, Controller controller) {
        this.scene = scene;
        this.controller = controller;

        addSelectionChangeListener(controller.tabBar);
        PaneEditor.addToolChangeUpdater(controller);

        System.out.println("TabManager created");
    }

    /*private void addPaneEditor() throws IllegalArgumentException {
        Tab currenttab = controller.tabBar.getSelectionModel().getSelectedItem();
        Node node = currenttab.getContent();
        ScrollPane scrollPane;
        if(node instanceof ScrollPane) {
            scrollPane = (ScrollPane) node;
        }
        else{throw new IllegalArgumentException("Loaded file is incorrect");}
        node = scrollPane.getContent();
        if(node instanceof Pane) {
            //currentPane = (Pane) node;
        }
        else{throw new IllegalArgumentException("Loaded file is incorrect");}
    }*/
    private void addSelectionChangeListener(TabPane tabPane) {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                System.out.println("Tab changed");
                if (paneEditor != null) {
                    paneEditor.killGUI();
                }
                TabStructure mother = getTabStructureFromTab(newValue);
                if (mother == null) {
                    return;
                }

                paneEditor = new PaneEditor(scene, controller, mother);
                paneEditor.addGUI();
            }
        });
    }


    private void addTabStructure(TabStructure tabStructure) {
        tabs.add(tabStructure);
        controller.tabBar.getTabs().add(tabStructure.tab);
    }

    private void removeTabStructure(TabStructure tabStructure) {
        tabs.remove(tabStructure);
        controller.tabBar.getTabs().remove(tabStructure.tab);
    }

    public void createTabFromELF(EntityListFile elf) {
        Tab tab;
        if (elf.file != null) {
            tab = new Tab(elf.file.getName());
        } else {
            tab = new Tab("Unsaved");
        }// name the tab after the file
        ScrollPane scrollPane = new ScrollPane();
        Pane pane = new Pane();
        pane.setPrefWidth(elf.entityList.width);
        pane.setPrefHeight(elf.entityList.height);
        pane.setClip(new Rectangle(elf.entityList.width, elf.entityList.height));
        pane.setStyle( // CSS START
                "    -fx-border-width: 1px;\n" +
                        "    -fx-border-style: dashed;\n" +
                        "    -fx-border-color: red;"
        );// CSS END
        scrollPane.setContent(pane);
        tab.setContent(scrollPane);
        elf.entityList.getEntities().forEach(entity -> {
            pane.getChildren().add(entity.reference);
        });
        TabStructure tabStructure = new TabStructure(elf, tab);

        // Add event handler to call remove tab structure
        tab.addEventHandler(Tab.TAB_CLOSE_REQUEST_EVENT, non -> {
            removeTabStructure(tabStructure);
        });
        // Finally add the tab to the tabs
        //
        addTabStructure(tabStructure);
    }

    private TabStructure getTabStructureFromTab(Tab tab) {
        for (TabStructure tabStructure : tabs) {
            if (tabStructure.tab == tab) {
                return tabStructure;
            }
        }
        return null;
    }
}
