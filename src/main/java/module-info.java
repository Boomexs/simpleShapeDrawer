module com.boomexs.shapeseditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.xml.dom;


    opens com.boomexs.shapeseditor to javafx.fxml;
    exports com.boomexs.shapeseditor;
}