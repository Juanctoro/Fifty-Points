package com.example.fiftypoints.controllers;

import com.example.fiftypoints.models.DeckModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        DeckModel deckModel = new DeckModel();
        deckModel.printDeck();
    }
}