package com.example.fiftypoints.controllers;

import com.example.fiftypoints.views.GameView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.Event;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {
    private int machine;

    @FXML
    public TextField inputUsername;

    @FXML
    public RadioButton oneMachine, twoMachine, threeMachine;

    @FXML
    public Button buttonPlay;

    public void initialize() {
        ToggleGroup toggleGroup = new ToggleGroup();
        oneMachine.setToggleGroup(toggleGroup);
        twoMachine.setToggleGroup(toggleGroup);
        threeMachine.setToggleGroup(toggleGroup);
        buttonPlay.setDisable(true);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            buttonPlay.setDisable(newValue == null);
            RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
            if (selected != null) {
                machine = Integer.parseInt(selected.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select an option before continuing.");
                alert.showAndWait();
            }
        });
    }

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
            gameController.initialize(inputUsername.getText(), machine);
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
