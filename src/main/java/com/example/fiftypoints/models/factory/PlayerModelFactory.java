package com.example.fiftypoints.models.factory;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.PlayerModel;
import com.example.fiftypoints.models.abstracts.HandModel;
import com.example.fiftypoints.models.abstracts.HandModelFactory;

public class PlayerModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new PlayerModel(cards);
    }
}