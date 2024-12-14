package com.example.fiftypoints.models;

import com.example.fiftypoints.models.abstracts.HandModelFactory;
import com.example.fiftypoints.models.factory.MachineModelFactory;
import com.example.fiftypoints.models.factory.PlayerModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Represents the core model of the game, managing the interaction between players,
 * machines, and decks of cards. This class provides functionality for initializing
 * the game state, distributing hands to players and machine opponents, and handling
 * the deck of cards throughout the game flow.
 */
public class GameModel {
    private final int machines;
    public PlayerModel player;
    public MachineModel machine;
    public MachineModel machineTwo;
    public MachineModel machineThree;
    public DeckModel deck;
    public CardModel lastCard;
    private final boolean[] machinesLoss = {false, false, false};

    /**
     * Constructs a GameModel object with the specified number of machines, initializes
     * the game's deck, and sets up the player's hand and machines accordingly.
     *
     * @param machines the number of machine players in the game; the value must be between
     *                 1 and 3 to define the number of machine players correctly in the game
     */
    public GameModel(int machines) {
        this.machines = machines;
        DeckModel.resetInstance();
        this.deck = DeckModel.getInstance();
        player = (PlayerModel) new PlayerModelFactory().createHandModel(setHands());
        setMachines();
    }

    /**
     * Assigns a set of four random cards from the deck to form a hand.
     * The method selects cards randomly from the deck, ensures they are removed
     * from the deck after being added to the hand, and returns the resulting hand.
     *
     * @return an array of four randomly selected CardModel objects representing the hand
     */
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

    /**
     * Configures the game's machine players based on the number of machines specified.

     * This method initializes one or more machine players by creating their hands using
     * a MachineModelFactory instance. The number of machine players is determined by
     * the value of the `machines` variable:
     *  - If `machines` is 1, only one machine player is initialized.
     *  - If `machines` is 2, two machine players are initialized.
     *  - If `machines` is 3, three machine players are initialized.

     * Each machine player is assigned a hand consisting of cards, which are created using
     * the `setHands` method and passed through the `createHandModel` method of the factory.

     * This method modifies the state of the `machine`, `machineTwo`, and `machineThree` fields
     * in the GameModel class to reflect the configured machine players.
     */
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

    /**
     * Retrieves the number of machine players configured in the game.
     *
     * @return the number of machine players as an integer
     */
    public int getMachines(){
        return machines;
    }

    /**
     * Selects a random card from the deck to start the game or round. If the deck
     * is empty, it resets the deck to ensure there are cards available for selection.
     * The selected card is then removed from the deck.
     *
     * @return the selected CardModel object representing the starting card
     */
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

    /**
     * Resets the game deck, ensuring it excludes all cards currently held by the player
     * and machine players, as well as the last played card, if applicable.
     * <p>
     * The method retrieves a complete new deck from the DeckModel instance, then removes
     * any cards that are currently in the hands of the human player or machine players.
     * Additionally, cards are only excluded if the respective machine is considered
     * "in play" (based on the machinesLoss array). The following logic is applied:
     * <p>
     * - If the player's hand is not null, those cards are removed from the new deck.
     * - If a machine's hand is not null, and it has not been marked as "lost" (via the machinesLoss array),
     *   those cards are removed from the new deck.
     * - For games with multiple machine players, the logic applies similarly to all machines,
     *   provided their presence is defined by the `machines` number field.
     * - If a `lastCard` is defined, it is removed from the deck as it is no longer valid for use.
     * <p>
     * After removing all necessary cards, the remaining cards form the updated deck,
     * which is saved back into the game's deck state.
     */
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

    /**
     * Updates the game state to mark a machine player as having lost the game.
     * This method:
     * - Retrieves the hand of cards for the specified machine.
     * - Updates the deck with the cards from the machine's hand to ensure their validity in the game.
     * - Marks the machine as "lost" in the game's state using the `machinesLoss` array.
     *
     * @param machine the MachineModel instance representing the machine player whose state is being updated
     * @param index the index of the machine in the game, used to update the corresponding entry in the `machinesLoss` array
     */
    public void lossMachine(MachineModel machine, int index) {
        CardModel[] hand =  machine.getHand();
        deck.setValidCards(hand);
        machinesLoss[index - 1] = true;
    }
}