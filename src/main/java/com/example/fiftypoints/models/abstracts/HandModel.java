package com.example.fiftypoints.models.abstracts;

import com.example.fiftypoints.models.CardModel;

/**
 * The HandModel class represents an abstract base class for managing a collection of cards.
 * It provides constructors and common methods to manipulate the hand of cards, such as
 * retrieving, setting, and removing cards from the hand.
 * Classes that represent specific types of hands (e.g., player or machine hands) can extend
 * this class and implement additional logic.
 */
public abstract class HandModel {
    protected CardModel[] cards;

    /**
     * Constructs a HandModel object initialized with an array of cards.
     *
     * @param cards an array of CardModel objects representing the cards to be managed
     *              by the HandModel instance
     */
    public HandModel(CardModel[] cards) {
        this.cards = cards;
    }

    /**
     * Retrieves the current hand of cards managed by this HandModel instance.
     *
     * @return an array of CardModel objects representing the cards in the hand.
     */
    public CardModel[] getHand() {
        return cards;
    }

    /**
     * Removes a card from the hand at the specified index by setting its value to null.
     *
     * @param index the position of the card to be removed in the hand, must be within
     *              the valid range of the hand array (0 to cards.length - 1)
     */
    public void removeCard(int index) {
        if (index >= 0 && index < cards.length) {
            cards[index] = null;
        }
    }

    /**
     * Sets a card at a specified index in the hand of cards.
     * If the provided index is valid (within the range of the hand array),
     * the card at the specified index is replaced with the given card.
     *
     * @param card the CardModel object to be set at the specified index in the hand
     * @param index the position in the hand where the card should be set;
     *              must be within the valid range of the hand array (0 to cards.length - 1)
     */
    public void setCard(CardModel card, int index) {
        if (index >= 0 && index < cards.length) {
            cards[index] = card;
        }
    }
}
