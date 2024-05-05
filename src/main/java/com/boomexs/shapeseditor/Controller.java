package com.boomexs.shapeseditor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Controller {
    @FXML
    public MenuItem fileOpen;

    @FXML
    public MenuItem fileSave;

    @FXML
    public TabPane tabBar;

    @FXML
    public ToggleGroup tool;

    @FXML
    public ToolBar topBar;

    @FXML
    public AnchorPane appwindow;

    @FXML
    public VBox leftSidebar;

    @FXML
    public VBox rightSidebar;

    @FXML
    public RadioButton rectangleTool;

    @FXML
    public RadioButton circleTool;

    @FXML
    public RadioButton triangleTool;

    @FXML
    public RadioButton editTool;

}