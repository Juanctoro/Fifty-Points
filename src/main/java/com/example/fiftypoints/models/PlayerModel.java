package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.IPlayer;
import com.example.fiftypoints.models.abstracts.HandModel;

/**
 * Represents a player in the game, extending functionality from the HandModel class
 * and implementing the IPlayer interface. This class models a player's hand and
 * provides the ability to throw or remove a specific card from the player's hand.
 */
public class PlayerModel extends HandModel implements IPlayer {

    /**
     * Constructs a PlayerModel object representing a player's hand in the game.
     * The player's hand is initialized with an array of CardModel objects.
     *
     * @param cards an array of CardModel objects that represents the initial
     *              cards in the player's hand
     */
    public PlayerModel(CardModel[] cards) {
        super(cards);
    }

    /**
     * Removes a card at the specified column index from the player's hand by setting
     * the associated card reference to null.
     *
     * @param Colum the index of the card in the player's hand to remove;
     *              must correspond to a valid position within the hand array
     */
    public void throwCard(int Colum) {
        cards[Colum] = null;
    }

}
