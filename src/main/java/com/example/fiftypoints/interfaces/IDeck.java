package com.example.fiftypoints.interfaces;

import com.example.fiftypoints.models.CardModel;

import java.util.ArrayList;

/**
 * The IDeck interface represents a blueprint for managing a deck of cards
 * in a card game. It provides methods for creating, manipulating, retrieving,
 * and updating the deck.
 */
public interface IDeck {
    /**
     * Initializes and populates the deck with all possible combinations of cards.
     * The method generates a deck of 52 unique cards by combining each suit
     * ("Hearts", "Diamonds", "Clubs", "Spades") with every rank ("2" through "10", "J", "Q", "K", "A").
     * Each generated card is added to both the primary card collection and the valid cards collection.
     * This method ensures the deck is complete and ready for use.
     */
    void generateDeck();

    /**
     * Removes the specified card from the collection of valid cards.
     *
     * @param card the card to be subtracted from the current collection
     *             of valid cards in the deck.
     */
    void subtractCard(CardModel card);

    /**
     * Retrieves the current collection of valid cards in the deck.
     * This includes all the cards that are currently available for use in the game.
     *
     * @return an ArrayList of CardModel objects representing the valid cards in the deck.
     */
    ArrayList<CardModel> getDeck();

    /**
     * Creates and returns a new complete deck of cards.
     * This deck is initialized with all possible unique combinations
     * of card ranks ("2" through "10", "J", "Q", "K", "A") and suits
     * ("Hearts", "Diamonds", "Clubs", "Spades").
     *
     * @return an ArrayList of CardModel objects representing a complete new deck of cards.
     */
    ArrayList<CardModel> getNewDeck();

    /**
     * Updates the collection of valid cards in the deck with the specified list of cards.
     *
     * @param deck the ArrayList of CardModel objects to set as the new collection of valid cards.
     */
    void setDeck(ArrayList<CardModel> deck);
}
