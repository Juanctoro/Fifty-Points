package com.example.fiftypoints.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for CardModel.
 * Tests the getNumber method which returns the number of the card.
 */
public class CardModelTest {

    @Test
    public void testGetNumberWithValidNumber() {
        CardModel card = new CardModel("5", "Hearts");
        assertEquals("5", card.getNumber());
    }

    @Test
    public void testGetNumberWithK() {
        CardModel card = new CardModel("K", "Spades");
        assertEquals("K", card.getNumber());
    }

    @Test
    public void testGetSuit() {
        CardModel card = new CardModel("A", "Clubs");
        assertEquals("Clubs", card.getSuits());
    }
}