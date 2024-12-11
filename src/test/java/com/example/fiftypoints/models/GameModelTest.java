package com.example.fiftypoints.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameModelTest {

    /**
     * Test the startCard method in GameModel.
     * The startCard method should pick a random card from the deck and
     * remove it, returning the selected card.
     */

    @Test
    public void testStartCard_DeckNotEmpty() {
        // Initialize the GameModel instance with a known state
        GameModel gameModel = new GameModel(1);
        ArrayList<CardModel> initialDeck = new ArrayList<>(gameModel.deck.getDeck());

        // Ensure deck is not empty initially
        assertFalse(initialDeck.isEmpty());

        // Invoke startCard method
        CardModel selectedCard = gameModel.startCard();

        // Verify that the card is removed from the deck and returned
        assertNotNull(selectedCard);
        assertFalse(gameModel.deck.getDeck().contains(selectedCard));
        assertEquals(initialDeck.size() - 1, gameModel.deck.getDeck().size());
    }

    @Test
    public void testStartCard_DeckBecomesEmpty() {
        // Initialize the GameModel instance with a machine
        GameModel gameModel = new GameModel(1);

        // Invoke startCard method
        CardModel selectedCard = gameModel.startCard();

        // Check if the letter starts
        assertNotNull(selectedCard);

        // Check the deck size after dealing the cards to the machine and the player.
        assertEquals(43, gameModel.deck.getDeck().size());
    }

    @Test
    public void testStartCard_ResetsEmptyDeck() {
        // Initialize the GameModel and empty the deck
        GameModel gameModel = new GameModel(1);
        gameModel.deck.setDeck(new ArrayList<>());

        // Invoke startCard method to force a deck reset
        gameModel.startCard();

        // Verify that the deck is reset (not empty) after the resetDeck method is called
        assertFalse(gameModel.deck.getDeck().isEmpty());
    }
}