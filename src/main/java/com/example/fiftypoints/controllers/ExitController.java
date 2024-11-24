package com.example.fiftypoints.controllers;

import com.example.fiftypoints.views.GameView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitController {
    @FXML
    private Label lossOrWin, machinesLabel;

    @FXML
    private RadioButton oneMachine, twoMachine, threeMachine;

    @FXML
    public Button play;

    private String username;
    private ToggleGroup toggleGroup;
    private RadioButton selected;

    public void initialize(Boolean winOrLoss, String username) {
        this.username = username;
        if (winOrLoss) {
            lossOrWin.setText("Your loss");
        } else {
            lossOrWin.setText("You win");
        }
    }

    public void playAgain() {
        oneMachine.setStyle("-fx-font-size: 18px;");
        twoMachine.setStyle("-fx-font-size: 18px;");
        threeMachine.setStyle("-fx-font-size: 18px;");
        oneMachine.setVisible(true);
        twoMachine.setVisible(true);
        threeMachine.setVisible(true);
        toggleGroup = new ToggleGroup();
        oneMachine.setToggleGroup(toggleGroup);
        twoMachine.setToggleGroup(toggleGroup);
        threeMachine.setToggleGroup(toggleGroup);
        machinesLabel.setVisible(true);
        play.setVisible(true);
        play.setDisable(true);
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            play.setDisable(newValue == null);
            selected = (RadioButton) toggleGroup.getSelectedToggle();
            if(selected != null) {
                play.setDisable(false);
            }
        });

    }
    public void exit(Event event) {
        Node source = (Node) event.getSource();
        Stage actualStage = (Stage) source.getScene().getWindow();
        actualStage.close();
    }

    public void play() throws IOException {
        int machine = Integer.parseInt(selected.getText());
        Stage stage = (Stage) play.getScene().getWindow();
        stage.close();

        GameView gameView = GameView.getInstance();
        gameView.show();
        GameController gameController = gameView.getGameController();
        gameController.initialize(username, machine);
    }
}
