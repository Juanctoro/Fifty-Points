package com.example.fiftypoints.models;

import java.util.ArrayList;

public class DeckModel {
    private final ArrayList<CardModel> cards;
    private ArrayList<CardModel> validCards;

    public DeckModel() {
        this.cards = new ArrayList<>();
        this.validCards = new ArrayList<>();
        generateDeck();
    }

    public void generateDeck(){
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] numbers = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for(String suit : suits) {
            for(String number : numbers) {
                CardModel card = new CardModel(number, suit);
                cards.add(card);
                validCards.add(card);
            }
        }
    }

    public void subtractCard(CardModel card) {
        validCards.remove(card);
    }

    public ArrayList<CardModel> getDeck() {
        return validCards;
    }

    public ArrayList<CardModel> getNewDeck() {
        return cards;
    }

    public void setDeck(ArrayList<CardModel> deck) {
        this.validCards = deck;
    }
}
