package com.example.fiftypoints.interfaces;

import com.example.fiftypoints.models.CardModel;

public interface IMachine {
    CardModel throwCard(int position);

    CardModel[] getHand();

    void removeCard(int Colum);

    void setCards(CardModel card, int colum);

    int getIndex();
}
