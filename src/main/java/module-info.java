module com.example.fiftypoints {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.fiftypoints to javafx.fxml;
    exports com.example.fiftypoints;
    exports com.example.fiftypoints.views;
    opens com.example.fiftypoints.views to javafx.fxml;
    exports com.example.fiftypoints.controllers;
    opens com.example.fiftypoints.controllers to javafx.fxml;
}