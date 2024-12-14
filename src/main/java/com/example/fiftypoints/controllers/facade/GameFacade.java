package com.example.fiftypoints.controllers.facade;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.GameModel;
import com.example.fiftypoints.views.DrawCard;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * The GameFacade class serves as a high-level interface for the game's core operations,
 * simplifying interactions with game components and encapsulating complex logic.
 */
public class GameFacade{
    private final GameModel gameModel;
    private final DrawCard drawCard;
    private final Random random;
    private int points;

    /**
     * Constructs a GameFacade instance with the specified GameModel.
     * Initializes components for card drawing and random number generation.
     *
     * @param gameModel the GameModel instance representing the core state and logic of the game
     */
    public GameFacade(GameModel gameModel) {
        this.gameModel = gameModel;
        this.drawCard = new DrawCard();
        this.random = new Random();
    }

    /**
     * Retrieves the current instance of the GameModel, which represents the core state
     * and logic of the game being managed in the GameFacade class.
     *
     * @return the GameModel instance representing the current state and logic of the game
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * Retrieves a list of arrays of CardModel objects representing the hands of the machine players
     * in the game. The number of machine players is determined by the game model. For each machine
     * player, their respective hand of cards is added to the list.
     *
     * @return an ArrayList containing arrays of CardModel objects, where each array represents
     *         the hand of a machine player. The list size corresponds to the number of machine players.
     */
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

    /**
     * Initiates the starting card of the game and adjusts the player's points based on
     * the card's number. Special point values are assigned for specific card numbers:
     * - "A": Adds 10 points.
     * - "J", "Q", "K": Subtracts 10 points.
     * - "9": No points are added or subtracted.
     * - Other numeric values: Points are based on the numeric value of the card.
     * <p>
     * Delegates the card selection to the gameModel's startCard method, retrieves the
     * selected card, and evaluates it's number to update the player's score.
     *
     * @return the selected CardModel object representing the starting card.
     */
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

    /**
     * Sets and returns a number corresponding to the specified card and the player's turn status.
     * The numeric value depends on the card type:
     * - "J", "Q", "K": Returns -10.
     * - "9": Returns 0.
     * - "A": Returns 11 if it's the player's turn. If it's not, returns either 10 or 1
     *   based on the current game points.
     * - Otherwise: Parses and returns the numeric value of the card.
     *
     * @param cardNumber the string representation of the card (e.g., "A", "9", "J").
     * @param playerTurn a boolean indicating whether it's the player's turn.
     * @return the integer value associated with the card as determined by the game rules.
     */
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

    /**
     * Retrieves the current points of the player in the game.
     *
     * @return the integer value representing the player's current points in the game.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Updates the current points by adding the specified value to them.
     *
     * @param points the integer value to be added to the current points
     */
    public void setPoints(int points) {
        this.points += points;
    }

    /**
     * Retrieves the instance of the DrawCard class associated with the game.
     * This instance provides methods for creating graphical representations
     * of playing cards, including their front and back views.
     *
     * @return the DrawCard instance used for rendering card visuals in the game.
     */
    public DrawCard getDrawCard() {
        return drawCard;
    }

    /**
     * Retrieves the instance of the Random class used for generating random numbers
     * within the game logic. This instance facilitates operations that require
     * randomness, such as selecting a random card from the deck.
     *
     * @return the Random instance used for random number generation in the game.
     */
    public Random getRandom() {
        return random;
    }

}