package com.example.fiftypoints.models.factoryModels;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.MachineModel;
import com.example.fiftypoints.models.abstractModels.HandModel;
import com.example.fiftypoints.models.abstractModels.HandModelFactory;

public class MachineModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new MachineModel(cards);
    }
}