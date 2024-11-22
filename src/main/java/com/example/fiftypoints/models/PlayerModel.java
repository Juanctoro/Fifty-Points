package com.example.fiftypoints.models;

public class PlayerModel {
    CardModel[] cards;

    public PlayerModel(CardModel[] cards) {
        this.cards = cards;
    }
    public CardModel[] getCards() {
        return cards;
    }
    public CardModel[] throwCard(int Colum) {
        this.cards[Colum] = null;
        return cards;
    }

    public void setCards(CardModel card, int colum) {
        this.cards[colum] = card;
    }
}
