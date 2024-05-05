package com.boomexs.shapeseditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class app extends Application {
    private TabManager tabManager;
    private FileManager fileManager;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(app.class.getResource("app-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Controller controller = fxmlLoader.getController();
        tabManager = new TabManager(scene, controller);
        fileManager = new FileManager(scene, controller, tabManager);
        addButtonUserData(controller);
        stage.setTitle("shapeseditor");
        stage.setScene(scene);
        stage.show();
    }

    private void addButtonUserData(Controller controller) {
        controller.rectangleTool.setUserData(Tool.RECTANGLE);
        controller.circleTool.setUserData(Tool.CIRCLE);
        controller.triangleTool.setUserData(Tool.TRIANGLE);
        controller.editTool.setUserData(Tool.EDIT);
    }
}