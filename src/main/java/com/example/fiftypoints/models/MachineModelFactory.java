package com.example.fiftypoints.models;

public class MachineModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new MachineModel(cards);
    }
}