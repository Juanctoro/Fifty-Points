package com.example.fiftypoints.models;

public class PlayerModel {
    CardModel[] cards;

    public PlayerModel(CardModel[] cards) {
        this.cards = cards;
    }
    public CardModel[] getCards() {
        return cards;
    }
    public CardModel throwCard(CardModel card) {
        return card;
    }
}
