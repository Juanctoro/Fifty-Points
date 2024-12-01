package com.example.fiftypoints.models.abstractModels;

import com.example.fiftypoints.models.CardModel;

public abstract class HandModel {
    protected CardModel[] cards;

    public HandModel(CardModel[] cards) {
        this.cards = cards;
    }

    public CardModel[] getHand() {
        return cards;
    }

    public void removeCard(int index) {
        if (index >= 0 && index < cards.length) {
            cards[index] = null;
        }
    }

    public void setCard(CardModel card, int index) {
        if (index >= 0 && index < cards.length) {
            cards[index] = card;
        }
    }
}
