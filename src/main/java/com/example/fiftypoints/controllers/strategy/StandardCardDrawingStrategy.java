package com.example.fiftypoints.controllers.strategy;

import com.example.fiftypoints.controllers.GameController;
import com.example.fiftypoints.controllers.facade.GameFacade;
import com.example.fiftypoints.models.CardModel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

/**
 * A card drawing strategy that uses the standard logic for rendering playing cards.
 * This strategy handles the graphical representation of cards including the front and back views,
 * and their placement on a grid-based layout.
 * <p>
 * The class interacts with the game logic through a GameFacade instance to retrieve
 * necessary card drawing utilities and configurations.
 * <p>
 * Constructor:
 * - Initializes an instance of StandardCardDrawingStrategy.
 * - Uses the provided GameController to access the GameFacade, which facilitates card rendering.
 * <p>
 * Methods:
 * - drawCard(CardModel card): Draws the graphical representation of a specific card,
 *   based on its number and suit.
 * - drawCardBack(): Draws the graphical representation of the back side of a card.
 * - addCardToGridPane(Group cardGroup, GridPane grid, int columnIndex, int rowIndex):
 *   Adds a graphical card representation to a specified cell in a GridPane and applies
 *   alignment and span settings.
 */
public class StandardCardDrawingStrategy implements CardDrawingStrategy {
    private final GameFacade gameFacade;

    /**
     * Constructs a StandardCardDrawingStrategy instance, initializing it with the provided
     * GameController to access the GameFacade for card rendering logic.
     *
     * @param gameController the GameController instance used to retrieve the GameFacade,
     *                       which provides the necessary utilities for card drawing and placement.
     */
    public StandardCardDrawingStrategy(GameController gameController) {
        gameFacade = gameController.getGameFacade();
    }

    /**
     * Draws the graphical representation of a card based on its number and suit.
     *
     * @param card the CardModel object containing the number and suit of the card to be drawn.
     * @return a Group object representing the visual elements of the card,
     *         including its background, number, and suit symbol.
     */
    @Override
    public Group drawCard(CardModel card) {
        return gameFacade.getDrawCard().drawCard(card.getNumber(), card.getSuits());
    }

    /**
     * Draws the graphical representation of the back side of a playing card.
     * Utilizes the GameFacade's draw functionalities to create a predefined
     * visual design for the card back.
     *
     * @return a Group object containing the graphical elements representing
     *         the back side of a playing card.
     */
    @Override
    public Group drawCardBack() {
        return gameFacade.getDrawCard().drawCardBack();
    }

    /**
     * Adds a graphical representation of a card to a specific cell in a GridPane layout.
     * Configures the card's alignment and cell spanning within the GridPane.
     *
     * @param cardGroup the Group object representing the graphical elements of the card to be added.
     * @param grid the GridPane instance where the card will be placed.
     * @param columnIndex the column index of the cell in the GridPane.
     * @param rowIndex the row index of the cell in the GridPane.
     */
    @Override
    public void addCardToGridPane(Group cardGroup, GridPane grid, int columnIndex, int rowIndex) {
        grid.add(cardGroup, columnIndex, rowIndex);
        GridPane.setRowSpan(cardGroup, 2);
        GridPane.setHalignment(cardGroup, HPos.CENTER);
        GridPane.setValignment(cardGroup, VPos.CENTER);
    }
}