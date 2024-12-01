package com.example.fiftypoints.models.factory;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.MachineModel;
import com.example.fiftypoints.models.abstracts.HandModel;
import com.example.fiftypoints.models.abstracts.HandModelFactory;

public class MachineModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new MachineModel(cards);
    }
}