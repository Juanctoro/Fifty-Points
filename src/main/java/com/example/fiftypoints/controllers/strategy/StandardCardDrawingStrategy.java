package com.example.fiftypoints.controllers.strategy;

import com.example.fiftypoints.controllers.GameController;
import com.example.fiftypoints.controllers.facade.GameFacade;
import com.example.fiftypoints.models.CardModel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

public class StandardCardDrawingStrategy implements CardDrawingStrategy {
    private final GameFacade gameFacade;

    public StandardCardDrawingStrategy(GameController gameController) {
        gameFacade = gameController.getGameFacade();
    }

    @Override
    public Group drawCard(CardModel card) {
        return gameFacade.getDrawCard().drawCard(card.getNumber(), card.getSuits());
    }

    @Override
    public Group drawCardBack() {
        String color = gameFacade.getRandom().nextInt(2) == 0 ? "red" : "black";
        return gameFacade.getDrawCard().drawCardBack(color);
    }

    @Override
    public void addCardToGridPane(Group cardGroup, GridPane grid, int columnIndex, int rowIndex) {
        grid.add(cardGroup, columnIndex, rowIndex);
        GridPane.setRowSpan(cardGroup, 2);
        GridPane.setHalignment(cardGroup, HPos.CENTER);
        GridPane.setValignment(cardGroup, VPos.CENTER);
    }
}