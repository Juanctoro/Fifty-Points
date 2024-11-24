package com.example.fiftypoints.interfaces;

import com.example.fiftypoints.models.CardModel;

public interface IPlayer {

     CardModel[] getCards();

     void throwCard(int Colum);

     void setCards(CardModel card, int colum);
}
