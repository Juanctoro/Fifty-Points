package com.example.fiftypoints.controllers;

import com.example.fiftypoints.views.GameView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The ExitController class serves as a controller for handling user interactions in the exit screen of the application.
 * It manages the interface elements to allow the player to exit, play again, or proceed to the main game window
 * based on their selection.
 */
public class ExitController {
    @FXML
    private Label lossOrWin, machinesLabel, typeLoss;

    @FXML
    private RadioButton oneMachine, twoMachine, threeMachine;

    @FXML
    public Button play;

    private String username;
    private ToggleGroup toggleGroup;
    private RadioButton selected;

    /**
     * Initializes the state of the user interface based on the outcome of the game and user information.
     *
     * @param winOrLoss a Boolean value indicating the game result; true for loss and false for win
     * @param username the username of the player
     * @param message the message to display on the loss information label
     */
    public void initialize(Boolean winOrLoss, String username, String message) {
        reset();
        typeLoss.setText(message);
        this.username = username;
        if (winOrLoss) {
            lossOrWin.setText("Your loss!!");
        } else {
            lossOrWin.setText("You win!!");
        }
    }

    /**
     * Resets the user interface elements to their initial state, enabling the user
     * to play the game again.
     * <p>
     * This method performs the following tasks:
     * - Updates the font size of radio buttons representing machine options.
     * - Makes the machine selection radio buttons and corresponding label visible.
     * - Assigns these radio buttons to a new ToggleGroup for mutual exclusivity.
     * - Ensures the "Play" button is initially disabled until the user selects an option.
     * - Adds a listener to handle enabling or disabling the "Play" button based on the
     *   user's selection of a machine option.
     */
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
    /**
     * Closes the current application window.
     *
     * @param event the event that triggered the method, providing context for the source of the action
     */
    public void exit(Event event) {
        Node source = (Node) event.getSource();
        Stage actualStage = (Stage) source.getScene().getWindow();
        actualStage.close();
    }

    /**
     * Initiates the game's main window and closes the current stage.
     * <p>
     * This method captures the selected machine option from the user interface
     * and uses it to transition to the game's main view. It retrieves the
     * Singleton instance of the GameView class, displays the game window, and
     * initializes the game through the GameController by passing the player's
     * username and selected machine type.
     *
     * @throws IOException if an error occurs while loading the game view resources
     */
    public void play() throws IOException {
        int machine = Integer.parseInt(selected.getText());
        Stage stage = (Stage) play.getScene().getWindow();
        stage.close();

        GameView gameView = GameView.getInstance();
        gameView.show();
        GameController gameController = gameView.getGameController();
        gameController.initialize(username, machine);
    }

    public void reset() {
        typeLoss.setText("");
        lossOrWin.setText("");
        oneMachine.setVisible(false);
        twoMachine.setVisible(false);
        threeMachine.setVisible(false);
        machinesLabel.setVisible(false);
        play.setVisible(false);
        play.setDisable(true);
        if (toggleGroup != null) {
            toggleGroup.getToggles().clear();
        }
        selected = null;
    }
}
