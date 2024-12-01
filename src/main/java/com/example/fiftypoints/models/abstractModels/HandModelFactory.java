package com.example.fiftypoints.models.abstractModels;

import com.example.fiftypoints.models.CardModel;

public abstract class HandModelFactory {
    public abstract HandModel createHandModel(CardModel[] cards);
}
