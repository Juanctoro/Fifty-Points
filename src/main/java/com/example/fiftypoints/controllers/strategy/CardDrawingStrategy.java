package com.example.fiftypoints.controllers.strategy;

import com.example.fiftypoints.models.CardModel;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

/**
 * The CardDrawingStrategy interface defines the contract for rendering and managing
 * the visual representation of playing cards in a graphical application. It includes
 * methods for drawing individual cards, drawing the reverse side of a card, and placing
 * cards within a grid layout.
 * <p>
 * Methods:
 * - drawCard(CardModel card): Handles the rendering of the front side of a given card
 *   based on its number and suit.
 * - drawCardBack(): Creates a graphical representation of the back side of a card,
 *   typically used when the card is faced own.
 * - addCardToGridPane(Group cardGroup, GridPane grid, int columnIndex, int rowIndex):
 *   Places a graphical card representation in a specific location within a GridPane layout,
 *   with support for alignment and spacing configurations.
 */
public interface CardDrawingStrategy {
    /**
     * Draws the graphical representation of a card based on its number and suit.
     *
     * @param card the CardModel object containing the number and suit of the card to be drawn
     * @return a Group object representing the visual elements of the card,
     *         including its background, number, and suit symbol
     */
    Group drawCard(CardModel card);
    /**
     * Draws the graphical representation of the back side of a playing card.
     * This method is typically used to render the faced own view of a card in the game interface.
     *
     * @return a Group object containing the visual elements representing the standard back design
     *         of a playing card.
     */
    Group drawCardBack();
    /**
     * Places a graphical representation of a card into a specified cell within a GridPane layout.
     * This method configures the card's alignment and cell spanning to ensure consistency
     * in its visual positioning.
     *
     * @param cardGroup the Group object encapsulating the visual elements of the card to be added.
     * @param grid the GridPane instance where the card will be inserted.
     * @param columnIndex the column index of the target cell in the GridPane.
     * @param rowIndex the row index of the target cell in the GridPane.
     */
    void addCardToGridPane(Group cardGroup, GridPane grid, int columnIndex, int rowIndex);
}