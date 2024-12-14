package com.example.fiftypoints.controllers;

import com.example.fiftypoints.views.GameView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.Event;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The HelloController class is the controller for a JavaFX application.
 * It manages the interactive elements of the UI such as text fields, radio buttons,
 * and buttons to control user input and responses.
 */
public class HelloController {
    private int machine;

    @FXML
    public TextField inputUsername;

    @FXML
    public RadioButton oneMachine, twoMachine, threeMachine;

    @FXML
    public Button buttonPlay;

    /**
     * Initializes the UI components and configures the required settings for interaction.
     * <p>
     * This method is automatically invoked when the controller is loaded and performs the following actions:
     * - Sets the font size of the radio button options (oneMachine, twoMachine, threeMachine).
     * - Groups the radio buttons into a single ToggleGroup to allow only one selection at a time.
     * - Disables the "Play" button by default to enforce proper input validation before enabling it.
     * - Adds listeners to monitor changes in the selected toggle button and the input field for username.
     *   This ensures the "Play" button is enabled only when a valid username is provided and a radio button is selected.
     */
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

    /**
     * Updates the state of the "Play" button based on the current selections and input.
     * <p>
     * This method enables the "Play" button when both of the following conditions are met:
     * 1. A toggle (radio button) is selected within the provided ToggleGroup.
     * 2. The username input field is not empty.
     * <p>
     * If a toggle is selected, the machine number is updated based on the selected toggle's text.
     *
     * @param toggleGroup The ToggleGroup containing the radio buttons that represent the selectable machines.
     */
    private void updatePlayButtonState(ToggleGroup toggleGroup) {
        boolean isToggleSelected = toggleGroup.getSelectedToggle() != null;
        boolean isUsernameNotEmpty = inputUsername != null && !inputUsername.getText().trim().isEmpty();
        buttonPlay.setDisable(!(isToggleSelected && isUsernameNotEmpty));

        if (isToggleSelected) {
            RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
            machine = Integer.parseInt(selected.getText());
        }
    }

    /**
     * Handles the "Play" button click event and initiates the game by closing the
     * current stage and opening the game view.
     * <p>
     * This method performs the following actions:
     * - Closes the current window (stage) associated with the "Play" button.
     * - Retrieves the singleton instance of the game view and makes it visible.
     * - Initializes the game controller with the username input and selected machine.
     *
     * @param event The event triggered by clicking the "Play" button.
     * @throws IOException If an error occurs while loading the game view.
     */
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

    /**
     * Displays an informational alert containing the rules and objectives of the game.
     * <p>
     * This method creates and shows an information dialog using a JavaFX Alert. The dialog provides detailed
     * instructions about the gameplay, rules, and objectives of the game, guiding players on how to play and
     * win effectively. The alert does not require any user input except acknowledgment by closing the dialog.
     * <p>
     * The content of the alert includes:
     * - Objective of the game: To remain the last player by ensuring the total value on the table does not exceed 50.
     * - Setup: Explanation of initial card distribution and starting conditions.
     * - Card rules: Descriptions of how each card affects the total value on the table.
     * - Gameplay: Turn-based instructions for selecting and playing cards, as well as rules for drawing cards.
     * - End-game conditions: Criteria for winning, losing, and what happens if the deck runs out of cards.
     * - Special scenarios: Handling of elimination mechanics and reshuffling*/
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
