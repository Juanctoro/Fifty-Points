package com.example.fiftypoints.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class DeckModelTest {

    /**
     * Tests for DeckModel class which is responsible for generating and managing a deck of cards.
     * The generateDeck method generates a new deck with 52 cards consisting of four suits
     * (Hearts, Diamonds, Clubs, Spades) and 13 numbers (2-10, J, Q, K, A) each.
     */

    @Test
    public void testGenerateDeck() {
        DeckModel.resetInstance();
        DeckModel deckModel = DeckModel.getInstance();
        ArrayList<CardModel> deck = deckModel.getNewDeck();

        assertEquals(52, deck.size(), "Deck should have 52 cards after generation.");
    }

    @Test
    public void testNoDuplicateCardsInGeneratedDeck() {
        DeckModel.resetInstance();
        DeckModel deckModel = DeckModel.getInstance();
        ArrayList<CardModel> deck = deckModel.getNewDeck();

        assertEquals(deck.stream().distinct().count(), deck.size(), "Deck should not contain duplicate cards.");
    }

    @Test
    public void testValidCardsAfterDeckGeneration() {
        DeckModel.resetInstance();
        DeckModel deckModel = DeckModel.getInstance();
        ArrayList<CardModel> validDeck = deckModel.getDeck();

        assertEquals(52, validDeck.size(), "Valid cards should contain 52 cards after generation.");
    }
}