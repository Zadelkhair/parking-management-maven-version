module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jfoenix;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml, javafx.base;
    opens org.example.model to javafx.base;

}