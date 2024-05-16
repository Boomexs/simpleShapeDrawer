package com.boomexs.shapeseditor;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * The {@code FileManager} class provides functionality for handling file operations
 * in the shapes editor application, including opening and saving files.
 */
public class FileManager {
    private final Scene scene;
    private final Controller controller;
    private final FileChooser fileChooser = new FileChooser();
    private final TabManager tabManager;
    private EntityListFile entityListFile;

    /**
     * Constructs a new {@code FileManager} with the specified scene, controller, and tab manager.
     *
     * @param scene      the scene to which this file manager is attached
     * @param controller the controller managing the file manager
     * @param tabManager the tab manager handling tabs in the application
     */
    public FileManager(Scene scene, Controller controller, TabManager tabManager) {
        this.scene = scene;
        this.controller = controller;
        this.tabManager = tabManager;
        addFileButtonEventHandlers();
        addELFobserver();

        //Run this manually at startup cuz weird bug:
        for (TabStructure tabStructure : tabManager.tabs) {
            if (tabStructure.tab == controller.tabBar.getSelectionModel().getSelectedItem()) {
                this.entityListFile = tabStructure.elf;
            }
        }
    }

    /**
     * Adds an observer to update the current {@code EntityListFile} when the selected tab changes.
     */
    private void addELFobserver() {
        controller.tabBar.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            for (TabStructure tabStructure : tabManager.tabs) {
                if (tabStructure.tab == newTab) {
                    this.entityListFile = tabStructure.elf;
                }
            }
        });
    }

    /**
     * Adds event handlers for the file open and save buttons.
     */
    private void addFileButtonEventHandlers() {
        controller.fileOpen.addEventHandler(ActionEvent.ACTION, event -> {
            fileChooser.setTitle("Open File");
            File file = fileChooser.showOpenDialog(scene.getWindow());
            if (file != null) {
                try {
                    this.entityListFile = new EntityListFile(file);
                    tabManager.createTabFromELF(this.entityListFile);
                } catch (Exception e) {
                    System.out.println("Can't open that file for some reason");
                }
            }
        });

        controller.fileSave.addEventHandler(ActionEvent.ACTION, event -> {
            fileChooser.setTitle("Save File");
            File file = fileChooser.showSaveDialog(scene.getWindow());
            if (file != null) {
                try {
                    EntityListFile elf = new EntityListFile(this.entityListFile.entityList);
                    elf.file = file;
                    elf.Save(elf.file, elf.entityList);
                    for (TabStructure tabStructure : tabManager.tabs) {
                        if (tabStructure.elf == this.entityListFile) {
                            tabManager.updateTabStructureFile(tabStructure, file);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
