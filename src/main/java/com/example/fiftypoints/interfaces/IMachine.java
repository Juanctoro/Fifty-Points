package com.example.fiftypoints.interfaces;

import com.example.fiftypoints.models.CardModel;

public interface IMachine {
    CardModel throwCard(int position);

    CardModel[] getHand();

    int getIndex();
}
