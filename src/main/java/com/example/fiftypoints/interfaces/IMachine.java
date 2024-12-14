package com.example.fiftypoints.interfaces;

import com.example.fiftypoints.models.CardModel;

/**
 * Defines the behavior of a machine player in a card game.
 * This interface provides methods for handling actions like throwing a card
 * based on the game's rules and retrieving the state information of the machine player.
 */
public interface IMachine {

    /**
     * Throws a card from the current hand that satisfies specific conditions based on its value
     * and the provided position. The method assesses each card in the hand and attempts to find
     * a card whose number aligns with the game's scoring rules. If found, the card is removed
     * from the hand and returned.
     *
     * @param position the current position in the game, used to determine whether a card can be
     *                 played based on its value
     * @return the card that satisfies the conditions and is removed from the hand,
     *         or null if no suitable card is found
     */
    CardModel throwCard(int position);

    /**
     * Retrieves the current index associated with the machine player.
     *
     * @return the current index as an integer
     */
    int getIndex();
}
