package com.example.fiftypoints.models;

import com.example.fiftypoints.models.abstracts.HandModelFactory;
import com.example.fiftypoints.models.factory.MachineModelFactory;
import com.example.fiftypoints.models.factory.PlayerModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameModel {
    private final int machines;
    public PlayerModel player;
    public MachineModel machine;
    public MachineModel machineTwo;
    public MachineModel machineThree;
    public DeckModel deck;
    public CardModel lastCard;
    private final boolean[] machinesLoss = {false, false, false};

    public GameModel(int machines) {
        this.machines = machines;
        DeckModel.resetInstance();
        this.deck = DeckModel.getInstance();
        player = (PlayerModel) new PlayerModelFactory().createHandModel(setHands());
        setMachines();
    }

    public CardModel[] setHands(){
        CardModel[] hand = new CardModel[4];
        for(int i = 0; i < 4; i++){
            Random random = new Random();
            int num = random.nextInt(deck.getDeck().size());
            ArrayList<CardModel> cards = deck.getDeck();
            hand[i] = (cards.get(num));
            deck.subtractCard(cards.get(num));
        }
        return hand; 
    }

    public void setMachines() {
        HandModelFactory factory = new MachineModelFactory();
        if (machines == 1) {
            machine = (MachineModel) factory.createHandModel(setHands());
        } else if (machines == 2) {
            machine = (MachineModel) factory.createHandModel(setHands());
            machineTwo = (MachineModel) factory.createHandModel(setHands());
        } else if (machines == 3) {
            machine = (MachineModel) factory.createHandModel(setHands());
            machineTwo = (MachineModel) factory.createHandModel(setHands());
            machineThree = (MachineModel) factory.createHandModel(setHands());
        }
    }

    public int getMachines(){
        return machines;
    }

    public CardModel startCard() {
        ArrayList<CardModel> cards = deck.getDeck();
        if (cards.isEmpty()) {
            resetDeck();
            cards = deck.getDeck();
        }
        if (cards.size() == 1) {
            lastCard = cards.get(0);
        }

        Random random = new Random();
        int num = random.nextInt(cards.size());
        CardModel selectedCard = cards.get(num);

        deck.subtractCard(selectedCard);
        if (cards.contains(selectedCard)) {
            cards.remove(num);
        }

        cards = deck.getDeck();

        if (cards.isEmpty()) {
            resetDeck();
        }

        return selectedCard;
    }

    public void resetDeck() {
        ArrayList<CardModel> fullDeck = deck.getNewDeck();
        ArrayList<CardModel> remainingCards = new ArrayList<>(fullDeck);

        if (player.getHand() != null) {
            remainingCards.removeAll(new ArrayList<>(Arrays.asList(player.getHand())));
        }

        if (machine.getHand() != null && machinesLoss[0]) {
            remainingCards.removeAll(new ArrayList<>(Arrays.asList(machine.getHand())));
        }

        if (machines > 2) {
            if (machineTwo.getHand() != null && machinesLoss[1]) {
                remainingCards.removeAll(new ArrayList<>(Arrays.asList(machineTwo.getHand())));
            }
            if (machineThree.getHand() != null && machinesLoss[2]) {
                remainingCards.removeAll(new ArrayList<>(Arrays.asList(machineThree.getHand())));
            }
        } else if (machines > 1) {
            if (machineTwo.getHand() != null && machinesLoss[1]) {
                remainingCards.removeAll(new ArrayList<>(Arrays.asList(machineTwo.getHand())));
            }
        }

        if (lastCard != null) {
            remainingCards.remove(lastCard);
        }

        deck.setDeck(remainingCards);
    }

    public void lossMachine(MachineModel machine, int index) {
        CardModel[] hand =  machine.getHand();
        deck.setValidCards(hand);
        machinesLoss[index - 1] = true;
    }

}