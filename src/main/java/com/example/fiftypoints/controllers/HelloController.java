package com.example.fiftypoints.controllers;

import com.example.fiftypoints.views.GameView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.Event;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    public TextField inputUsername;


    @FXML
    public void onHelloButtonPlay(Event event) throws IOException {
        if (inputUsername == null || inputUsername.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter your username!");
            alert.showAndWait();
        } else {
            Node source = (Node) event.getSource();
            Stage actualStage = (Stage) source.getScene().getWindow();
            actualStage.close();

            GameView gameView = GameView.getInstance();
            gameView.show();
            GameController gameController = gameView.getGameController();
            gameController.initialize(inputUsername.getText());
        }
    }

    public void onActionButtonInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Hola");
        alert.showAndWait();
    }
}
