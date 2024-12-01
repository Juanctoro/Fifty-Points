module com.example.fiftypoints {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.fiftypoints to javafx.fxml;
    exports com.example.fiftypoints.models;
    exports com.example.fiftypoints;
    exports com.example.fiftypoints.views;
    opens com.example.fiftypoints.views to javafx.fxml;
    exports com.example.fiftypoints.controllers;
    opens com.example.fiftypoints.controllers to javafx.fxml;
    exports com.example.fiftypoints.models.abstracts;
    exports com.example.fiftypoints.models.factory;
    exports com.example.fiftypoints.controllers.strategy;
    opens com.example.fiftypoints.controllers.strategy to javafx.fxml;
    exports com.example.fiftypoints.controllers.threads;
    opens com.example.fiftypoints.controllers.threads to javafx.fxml;
    exports com.example.fiftypoints.controllers.facade;
    opens com.example.fiftypoints.controllers.facade to javafx.fxml;
}