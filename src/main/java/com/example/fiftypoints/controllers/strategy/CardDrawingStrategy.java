package com.example.fiftypoints.controllers.strategy;

import com.example.fiftypoints.models.CardModel;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

public interface CardDrawingStrategy {
    Group drawCard(CardModel card);
    Group drawCardBack();
    void addCardToGridPane(Group cardGroup, GridPane grid, int columnIndex, int rowIndex);
}