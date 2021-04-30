module org.example {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jfoenix;
    requires webcam.capture;
    requires java.desktop;
    requires javafx.swing;
    requires webcam.capture.driver.ipcam;
    requires javafx.web;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml, javafx.base;
    opens org.example.model to javafx.base;
}