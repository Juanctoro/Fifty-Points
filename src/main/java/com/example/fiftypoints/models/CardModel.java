package com.example.fiftypoints.models;

public class CardModel {
    String number;
    String suit;

    public CardModel(String number, String suit) {
        this.number = number;
        this.suit = suit;
    }

    public String getNumber() {
        return number;
    }

    public String getSuits() {
        return suit;
    }
}
