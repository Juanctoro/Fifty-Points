package com.example.fiftypoints.controllers.facade;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.GameModel;
import com.example.fiftypoints.views.DrawCard;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameFacade{
    private final GameModel gameModel;
    private final DrawCard drawCard;
    private final Random random;
    private int points;

    public GameFacade(GameModel gameModel) {
        this.gameModel = gameModel;
        this.drawCard = new DrawCard();
        this.random = new Random();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public ArrayList<CardModel[]> machines(){
        ArrayList<CardModel[]> handsList = new ArrayList<>();
        if(gameModel.getMachines() == 1){
            handsList.add(gameModel.machine.getHand());
            return handsList;
        } else if(gameModel.getMachines() == 2){
            handsList.add(gameModel.machine.getHand());
            handsList.add(gameModel.machineTwo.getHand());
            return handsList;
        } else if(gameModel.getMachines() == 3){
            handsList.add(gameModel.machine.getHand());
            handsList.add(gameModel.machineTwo.getHand());
            handsList.add(gameModel.machineThree.getHand());
            return handsList;
        }
        return handsList;
    }

    public CardModel startCard(){
        CardModel startCart = gameModel.startCard();
        if(Objects.equals(startCart.getNumber(), "A")){
            this.points = 10;
        } else if(Objects.equals(startCart.getNumber(), "J") || Objects.equals(startCart.getNumber(), "Q") || Objects.equals(startCart.getNumber(), "K")){
            this.points = -10;
        } else if(Objects.equals(startCart.getNumber(), "9")){
            this.points = 0;
        } else {
            this.points = Integer.parseInt(startCart.getNumber());
        }
        return startCart;
    }

    public int setNumber(String cardNumber, Boolean playerTurn){
        int number;
        if(Objects.equals(cardNumber, "J") || Objects.equals(cardNumber, "Q") || Objects.equals(cardNumber, "K")){
            number = -10;
            return number;
        } else if (Objects.equals(cardNumber, "9")) {
            number = 0;
            return number;
        } else if (Objects.equals(cardNumber, "A")) {
            if(playerTurn) {
                return 11;
            } else {
                return (points + 10 <= 50) ? 10 : 1;
            }

        } else {
            number = Integer.parseInt(cardNumber);
            return number;
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public DrawCard getDrawCard() {
        return drawCard;
    }

    public Random getRandom() {
        return random;
    }

}