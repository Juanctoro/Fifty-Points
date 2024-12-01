package com.example.fiftypoints.models;

public class PlayerModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new PlayerModel(cards);
    }
}