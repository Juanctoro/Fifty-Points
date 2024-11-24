package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.IPlayer;

public class PlayerModel implements IPlayer {
    CardModel[] cards;

    public PlayerModel(CardModel[] cards) {
        this.cards = cards;
    }

    public CardModel[] getCards() {
        return cards;
    }

    public void throwCard(int Colum) {
        this.cards[Colum] = null;
    }

    public void setCards(CardModel card, int colum) {
        this.cards[colum] = card;
    }
}
