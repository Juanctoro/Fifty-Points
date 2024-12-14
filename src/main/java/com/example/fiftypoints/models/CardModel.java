package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.ICard;

/**
 * The CardModel class represents a playing card with a specific number (or rank)
 * and suit. It implements the ICard interface.
 */
public class CardModel implements ICard {
    String number;
    String suit;

    /**
     * Constructs a CardModel object with the given number and suit.
     *
     * @param number the number or rank of the card (e.g., "2", "K", "A")
     * @param suit the suit of the card (e.g., "Hearts", "Spades", "Diamonds", "Clubs")
     */
    public CardModel(String number, String suit) {
        this.number = number;
        this.suit = suit;
    }

    /**
     * Retrieves the number associated with the card.
     *
     * @return a String representing the number of the card.
     */
    @Override
    public String getNumber() {
        return number;
    }

    /**
     * Retrieves the suit associated with the card.
     *
     * @return a String representing the suit of the card.
     */
    @Override
    public String getSuits() {
        return suit;
    }
}
