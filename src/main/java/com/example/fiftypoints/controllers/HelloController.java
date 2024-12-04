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
        oneMachine.setStyle("-fx-font-size: 16px;");
        twoMachine.setStyle("-fx-font-size: 16px;");
        threeMachine.setStyle("-fx-font-size: 16px;");
        ToggleGroup toggleGroup = new ToggleGroup();
        oneMachine.setToggleGroup(toggleGroup);
        twoMachine.setToggleGroup(toggleGroup);
        threeMachine.setToggleGroup(toggleGroup);
        buttonPlay.setDisable(true);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updatePlayButtonState(toggleGroup));
        inputUsername.textProperty().addListener((observable, oldValue, newValue) -> updatePlayButtonState(toggleGroup));
    }

    private void updatePlayButtonState(ToggleGroup toggleGroup) {
        boolean isToggleSelected = toggleGroup.getSelectedToggle() != null;
        boolean isUsernameNotEmpty = inputUsername != null && !inputUsername.getText().trim().isEmpty();
        buttonPlay.setDisable(!(isToggleSelected && isUsernameNotEmpty));

        if (isToggleSelected) {
            RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
            machine = Integer.parseInt(selected.getText());
        }
    }

    @FXML
    public void onHelloButtonPlay(Event event) throws IOException {
        Node source = (Node) event.getSource();
        Stage actualStage = (Stage) source.getScene().getWindow();
        actualStage.close();

        GameView gameView = GameView.getInstance();
        gameView.show();
        GameController gameController = gameView.getGameController();
        gameController.initialize(inputUsername.getText(), machine);
    }

    public void onActionButtonInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("""
                Objective of the Game: The goal is to be the last player remaining in the game, avoiding having the total on the table exceed 50.
                
                Setup:
                
                Each player is dealt 4 cards from the deck.
                A random card is placed on the table to start the total.
                Card Rules:
                
                Cards numbered 2 to 8 and 10 add their value to the total on the table.
                Cards with the number 9 do not add or subtract.
                J, Q, K cards subtract 10 from the total.
                The Ace (A) can add either 1 or 10, depending on what's best.
                Gameplay:
                
                On your turn, select a card from your hand and place it on the table, ensuring the total does not exceed 50.
                After playing a card, draw a card from the deck to always have 4 cards in hand.
                If you cannot play any card because it would exceed the sum of 50, you are eliminated.
                End of the Game:
                
                The game continues until only one player remains, who is declared the winner.
                If the Deck Runs Out:
                
                The cards played on the table (except the last one) are shuffled and placed back in the deck.
                Elimination:
                
                When a player is eliminated, their cards are placed at the end of the deck and will be available for other players to draw.
                Follow these rules to enjoy the game and avoid exceeding the sum of 50 on the table!
                """);
        alert.showAndWait();
    }
}
