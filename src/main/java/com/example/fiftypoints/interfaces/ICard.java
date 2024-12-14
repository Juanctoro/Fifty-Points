package com.example.fiftypoints.interfaces;

/**
 * The ICard interface represents the abstraction of a playing card.
 * It provides methods to retrieve the card's number (or rank) and suit.
 * This interface can be implemented to define the specific behavior or structure
 * of a card in a card game.
 */
public interface ICard {

    /**
     * Retrieves the number or rank associated with the card.
     *
     * @return a String representing the rank of the card,
     *         such as "2", "10", "J", "Q", "K", or "A".
     */
    String getNumber();

    /**
     * Retrieves the suit associated with the card.
     *
     * @return a String representing the suit of the card,
     *         such as "Hearts", "Diamonds", "Clubs", or "Spades".
     */
    String getSuits();
}
