package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.ICard;

public class CardModel implements ICard {
    String number;
    String suit;

    public CardModel(String number, String suit) {
        this.number = number;
        this.suit = suit;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getSuits() {
        return suit;
    }
}
