package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.IDeck;

import java.util.ArrayList;
import java.util.List;

/**
 * DeckModel represents a singleton class that models a deck of cards.
 * It provides methods to generate, manage, and retrieve the cards in the deck.
 * The class ensures only one instance of the deck is created during the application lifecycle.
 */
public class DeckModel implements IDeck {
    private static DeckModel instance;
    private final ArrayList<CardModel> cards;
    private ArrayList<CardModel> validCards;

    /**
     * Constructs a DeckModel instance.
     * This constructor is private to enforce the Singleton design pattern
     * and prevent direct instantiation of the class.
     * <p>
     * Initializes the deck by allocating memory for the card collections
     * and populates them using the generateDeck method.
     */
    private DeckModel() {
        this.cards = new ArrayList<>();
        this.validCards = new ArrayList<>();
        generateDeck();
    }

    /**
     * Provides the singleton instance of the DeckModel class.
     * Ensures that only one instance of DeckModel is created
     * and returns the same instance throughout the application lifecycle.
     *
     * @return the single instance of the DeckModel class
     */
    public static DeckModel getInstance() {
        if (instance == null) {
            instance = new DeckModel();
        }
        return instance;
    }

    /**
     * Resets the singleton instance of the DeckModel class.
     * This method sets the static instance variable to null, allowing
     * for the creation of a new DeckModel instance when the getInstance
     * method is next called. It is typically used to explicitly clear
     * the current instance in scenarios where a fresh singleton instance
     * is required.
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Initializes and populates the deck with all possible combinations of cards.
     * The method generates a deck of 52 unique cards by combining each suit
     * ("Hearts", "Diamonds", "Clubs", "Spades") with every rank ("2" through "10", "J", "Q", "K", "A").
     * Each generated card is added to both the primary card collection and the valid cards collection.
     * This method ensures the deck is complete and ready for use.
     */
    public void generateDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] numbers = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String number : numbers) {
                CardModel card = new CardModel(number, suit);
                cards.add(card);
                validCards.add(card);
            }
        }
    }

    /**
     * Removes the specified card from the collection of valid cards.
     *
     * @param card the card to be removed from the valid cards collection.
     */
    public void subtractCard(CardModel card) {
        validCards.remove(card);
    }

    /**
     * Retrieves the current collection of valid cards from the deck.
     *
     * @return an ArrayList containing CardModel objects representing the valid cards in the deck.
     */
    public ArrayList<CardModel> getDeck() {
        return validCards;
    }

    /**
     * Retrieves a new deck of cards. This method returns a collection
     * of CardModel objects representing a complete set of cards.
     *
     * @return an ArrayList containing CardModel objects representing the new deck of cards.
     */
    public ArrayList<CardModel> getNewDeck() {
        return cards;
    }

    /**
     * Updates the collection of valid cards in the deck with the specified list of cards.
     *
     * @param deck the ArrayList of CardModel objects to set as the new collection of valid cards.
     */
    public void setDeck(ArrayList<CardModel> deck) {
        this.validCards = deck;
    }

    /**
     * Updates the collection of valid cards in the deck by adding the specified array of cards.
     *
     * @param validCards an array of CardModel objects to be added to the collection of valid cards.
     */
    public void setValidCards(CardModel[] validCards) {
        this.validCards.addAll(List.of(validCards));
    }
}
