package com.boomexs.shapeseditor;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

import java.io.File;

public class FileManager {
    private Scene scene;
    private Controller controller;
    private FileChooser fileChooser = new FileChooser();
    private TabManager tabManager;
    private EntityListFile entityListFile;

    public FileManager(Scene scene, Controller controller, TabManager tabManager) {
        this.scene = scene;
        this.controller = controller;
        this.tabManager = tabManager;
        test();
        addFileButtonEventHandlers();
    }

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
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private void test() {
        this.entityListFile = new EntityListFile();
        this.entityListFile.entityList.addEntity(new ECircle((short) 255, (short) 255, (short) 0, 64, 64, 32));
        tabManager.createTabFromELF(this.entityListFile);
    }
}
