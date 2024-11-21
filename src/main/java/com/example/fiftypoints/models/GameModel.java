package com.example.fiftypoints.models;

import java.util.ArrayList;
import java.util.Random;

public class GameModel {
    DeckModel deck;
    PlayerModel player;
    MachineModel machine;

    public GameModel() {
        this.deck = new DeckModel();
        player = new PlayerModel(setHands());
        machine = new MachineModel(setHands());
    }

    public CardModel[] setHands(){
        CardModel[] hand = new CardModel[4];
        for(int i = 0; i <= 4; i++){
            Random random = new Random();
            int num = random.nextInt(52);
            ArrayList<CardModel> cards = deck.getDeck();
            hand[i] = (cards.get(num));
            deck.subtractLetter(cards.get(num));
        }
        return hand; 
    }
}
