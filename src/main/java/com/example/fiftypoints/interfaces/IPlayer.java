package com.example.fiftypoints.interfaces;

/**
 * Represents behavior for a player in a card game, providing
 * the ability to perform actions like throwing a card.
 */
public interface IPlayer {

     /**
      * Removes a card at the specified column index from the player's hand.
      * The method internally sets the card at the given index to null, indicating
      * its removal from the player's hand.
      *
      * @param Colum the index of the card in the player's hand to remove;
      *              must correspond to a valid position within the player's hand
      */
     void throwCard(int Colum);

}
