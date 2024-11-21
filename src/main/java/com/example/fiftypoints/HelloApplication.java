package com.example.fiftypoints;

import com.example.fiftypoints.views.HelloView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public void start(Stage stage) throws IOException {
        HelloView.getInstance();
    }

    public static void main(String[] args) {
        launch();
    }
}