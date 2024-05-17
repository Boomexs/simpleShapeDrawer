package com.boomexs.shapeseditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code TabManager} class is responsible for managing the tabs within the shapes editor application.
 * It provides functionality to add, remove, and update tabs, each associated with an {@code EntityListFile}.
 */
public class TabManager {
    private final Scene scene;
    private final Controller controller;
    public List<TabStructure> tabs = new ArrayList<>();
    private EntityListFile currentFile; // it is actually used

    /**
     * Constructs a new {@code TabManager} with the specified scene and controller.
     *
     * @param scene      the scene to which this tab manager is attached
     * @param controller the controller managing the tab manager
     */
    public TabManager(Scene scene, Controller controller) {
        this.scene = scene;
        this.controller = controller;

        addNewTabButton();
        PaneEditor.addToolChangeUpdater(controller);

        System.out.println("TabManager created");
    }

    /**
     * Adds a button to create new tabs. When the button is selected, a new tab is created and opened.
     */
    private void addNewTabButton() {
        Tab addTab = new Tab("Create Tab"); // You can replace the text with an icon
        addTab.setClosable(false);
        controller.tabBar.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            for (TabStructure tab : tabs) {
                if (oldTab == tab.tab) {
                    try {
                        tab.getPaneEditor().killGUI();
                    } catch (Exception e) {
                        // Do nothing
                    }
                }
            }
            for (TabStructure tab : tabs) {
                if (newTab == tab.tab) {
                    try {
                        tab.getPaneEditor().addGUI();
                    } catch (Exception e) {
                        // Do nothing
                    }
                }
            }
            if (newTab == addTab) {
                createTabFromELF(new EntityListFile());
                controller.tabBar.getSelectionModel().select(controller.tabBar.getTabs().size() - 2); // Selecting the tab before the button, which is the newly created one
            }
        });
        controller.tabBar.getTabs().add(addTab);
    }

    /**
     * Adds a {@code TabStructure} to the list of tabs and inserts it into the tab bar.
     *
     * @param tabStructure the {@code TabStructure} to be added
     */
    private void addTabStructure(TabStructure tabStructure) {
        tabs.add(tabStructure);
        controller.tabBar.getTabs().add(controller.tabBar.getTabs().size() - 1, tabStructure.tab);
    }

    /**
     * Removes a {@code TabStructure} from the list of tabs and the tab bar.
     *
     * @param tabStructure the {@code TabStructure} to be removed
     */
    private void removeTabStructure(TabStructure tabStructure) {
        tabs.remove(tabStructure);
        controller.tabBar.getTabs().remove(tabStructure.tab);
    }

    /**
     * Creates a new tab from the specified {@code EntityListFile}.
     *
     * @param elf the {@code EntityListFile} from which to create the new tab
     */
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
        tabStructure.setPaneEditor(new PaneEditor(scene, controller, tabStructure));

        // Add event handler to call remove tab structure
        tab.addEventHandler(Tab.TAB_CLOSE_REQUEST_EVENT, non -> {
            removeTabStructure(tabStructure);
        });
        // Finally add the tab to the tabs
        //
        addTabStructure(tabStructure);
    }

    /**
     * Updates the file associated with the specified {@code TabStructure}.
     *
     * @param tabStructure the {@code TabStructure} to be updated
     * @param file         the new file to associate with the tab structure
     */
    public void updateTabStructureFile(TabStructure tabStructure, File file) {
        for (TabStructure tab : tabs) {
            if (tab == tabStructure) {
                tabStructure.elf.file = file;
                tabStructure.tab.setText(tabStructure.elf.file.getName());
            }
        }
    }

    /**
     * Retrieves the {@code TabStructure} associated with the specified tab.
     *
     * @param tab the tab for which to retrieve the associated {@code TabStructure}
     * @return the {@code TabStructure} associated with the specified tab, or null if none is found
     */
    private TabStructure getTabStructureFromTab(Tab tab) {
        for (TabStructure tabStructure : tabs) {
            if (tabStructure.tab == tab) {
                return tabStructure;
            }
        }
        return null;
    }
}
