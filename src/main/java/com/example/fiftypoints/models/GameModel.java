package com.example.fiftypoints.models;

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

    public GameModel(int machines) {
        this.machines = machines;
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
            System.out.println("last card: " + lastCard);
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

        if (machine.getHand() != null) {
            remainingCards.removeAll(new ArrayList<>(Arrays.asList(machine.getHand())));
        }

        if (machines > 2) {
            if (machineTwo.getHand() != null) {
                remainingCards.removeAll(new ArrayList<>(Arrays.asList(machineTwo.getHand())));
            }
            if (machineThree.getHand() != null) {
                remainingCards.removeAll(new ArrayList<>(Arrays.asList(machineThree.getHand())));
            }
        } else if (machines > 1) {
            if (machineTwo.getHand() != null) {
                remainingCards.removeAll(new ArrayList<>(Arrays.asList(machineTwo.getHand())));
            }
        }

        if (lastCard != null) {
            remainingCards.remove(lastCard);
        }

        deck.setDeck(remainingCards);
    }
}