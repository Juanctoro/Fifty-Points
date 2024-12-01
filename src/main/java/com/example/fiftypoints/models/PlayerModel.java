package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.IPlayer;

public class PlayerModel extends HandModel implements IPlayer {

    public PlayerModel(CardModel[] cards) {
        super(cards);
    }

    public void throwCard(int Colum) {
        cards[Colum] = null;
    }

}
