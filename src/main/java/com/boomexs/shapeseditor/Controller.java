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

    @FXML
    public Button infoButton;

    @FXML
    public void onInfoButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("Instructions:\n" +
                "1. Choose a tool and draw by left-clicking.\n" +
                "2. Right-click to select a color.\n" +
                "3. In edit mode, left-click to make edits.\n" +
                "4. While holding the left-click, drag the mouse to move objects.\n" +
                "5. Use the scroll wheel to rotate.\n" +
                "6. Hold Shift and scroll to resize.\n" +
                "7. Save and load using the buttons under the 'File' menu.\n" +
                "8. You can open multiple tabs for different projects.\n" +
                "9. To delete an item, click on it with the right or middle mouse button.\n");

        alert.showAndWait();
    }

}