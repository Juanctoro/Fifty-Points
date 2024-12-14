package com.example.fiftypoints.models.factory;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.MachineModel;
import com.example.fiftypoints.models.abstracts.HandModel;
import com.example.fiftypoints.models.abstracts.HandModelFactory;

/**
 * MachineModelFactory is a factory class responsible for creating an instance of MachineModel,
 * which is a specific implementation of the HandModel class. This factory provides
 * a concrete implementation of the abstract method defined in the HandModelFactory class.
 */
public class MachineModelFactory extends HandModelFactory {
    @Override
    public HandModel createHandModel(CardModel[] cards) {
        return new MachineModel(cards);
    }
}