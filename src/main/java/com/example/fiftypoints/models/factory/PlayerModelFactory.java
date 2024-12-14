package com.example.fiftypoints.models.factory;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.PlayerModel;
import com.example.fiftypoints.models.abstracts.HandModel;
import com.example.fiftypoints.models.abstracts.HandModelFactory;

/**
 * PlayerModelFactory is a factory class responsible for creating instances of PlayerModel,
 * which is a specific implementation of the HandModel class. This class provides
 * a concrete implementation of the abstract method defined in the HandModelFactory class.
 */
public class PlayerModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new PlayerModel(cards);
    }
}