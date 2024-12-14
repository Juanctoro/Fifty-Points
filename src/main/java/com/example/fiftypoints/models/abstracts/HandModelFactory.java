package com.example.fiftypoints.models.abstracts;

import com.example.fiftypoints.models.CardModel;

/**
 * HandModelFactory is an abstract factory class responsible for creating instances of
 * HandModel, which represents a player's hand of cards in a card-based application.
 * This class defines a single abstract method, which must be implemented by concrete
 * factory subclasses to create specific implementations of HandModel.
 * <p>
 * Subclasses of HandModelFactory are designed to provide custom logic for creating
 * instances of HandModel, such as configuring the hand with specific cards or applying
 * specific rules for the creation process.
 */
public abstract class HandModelFactory {
    public abstract HandModel createHandModel(CardModel[] cards);
}
