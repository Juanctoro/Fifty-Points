package com.example.fiftypoints.models.factoryModels;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.PlayerModel;
import com.example.fiftypoints.models.abstractModels.HandModel;
import com.example.fiftypoints.models.abstractModels.HandModelFactory;

public class PlayerModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new PlayerModel(cards);
    }
}