package com.example.fiftypoints.interfaces;

import com.example.fiftypoints.models.CardModel;

import java.util.ArrayList;

public interface IDeck {
    void generateDeck();

    void subtractCard(CardModel card);

    ArrayList<CardModel> getDeck();

    ArrayList<CardModel> getNewDeck();

    void setDeck(ArrayList<CardModel> deck);
}
