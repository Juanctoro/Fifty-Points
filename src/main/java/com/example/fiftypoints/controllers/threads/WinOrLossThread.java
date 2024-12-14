package com.example.fiftypoints.controllers.threads;

import com.example.fiftypoints.controllers.GameController;
import com.example.fiftypoints.models.CardModel;
import javafx.application.Platform;

import java.io.IOException;

/**
 * The WinOrLossThread class extends the Thread class to run game state checks
 * in a concurrent context. It determines whether a player wins or loses based
 * on the current state of the game and performs the corresponding actions such
 * as flag modifications and UI updates.
 */
public class WinOrLossThread extends Thread {
    private final GameController gameController;

    /**
     * Initializes a WinOrLossThread instance used to handle win or loss logic for a game.
     *
     * @param gameController the GameController instance to manage the game's win or loss state
     */
    public WinOrLossThread(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Executes the win or loss logic for the game. This method performs checks to determine
     * if all machines or the player have lost, and subsequently handles the resulting game states.
     * <p>
     * The method first invokes the `win()` method to assess if all machines are in a lost state.
     * If all machines have lost, the game is marked over, and the game view is exited.
     * <p>
     * Next, the method calls the `loss()` method to evaluate whether the player has no valid moves left
     * due to their hand of cards. If all cards are deemed invalid and the player cannot progress,
     * the player is marked as lost, the game is terminated, and appropriate loss handling is triggered.
     */
    public void run() {
        win();
        loss();
    }
    /**
     * Checks the win condition for the game by evaluating the loss state of all machine players
     * and the player. If all machines have lost and the player has not lost, the game is marked
     * as over and the appropriate post-game logic is executed.
     * <p>
     * The method iterates through all machine players to verify whether each machine is in a
     * lost state. If any machine has not yet lost, the method terminates further checks and
     * continues the game.
     * <p>
     * If all machines are confirmed to have lost and the player has not lost, the game is marked
     * as over, the game UI updates to reflect the end state, and additional steps for exiting
     * the game are triggered. This includes running the exit view logic on the JavaFX application
     * thread and disabling further game actions.
     * <p>
     * If an IOException occurs during the exit view initialization, it is wrapped in a
     * RuntimeException and re-thrown to signal unexpected termination.
     */
    private void win() {
        boolean allMachinesLost = true;

        for (int i = 1; i <= gameController.getGameFacade().getGameModel().getMachines(); i++) {
            if (!gameController.getLossPlayerState()[i]) {
                allMachinesLost = false;
                break;
            }
        }

        if (allMachinesLost && !gameController.getLossPlayerState()[0]) {
            gameController.setGameOver(true);
            Platform.runLater(() -> {
                try {
                    gameController.exitView(false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            gameController.throwCard.setDisable(true);
        }
    }

    /**
     * Evaluates if the player's hand of cards leads to a loss condition. If the player cannot make
     * a valid move based on their hand, the game is marked as over, and loss logic is executed.
     * <p>
     * The method iterates through the player's hand, evaluating the numerical or symbolic value of
     * each card. Based on the game logic:
     * - The card values are processed and adjusted (e.g., replacing "9" with "0", handling "A" as 10 or 1).
     * - Checks are made against the game's points threshold (e.g., 50 points) to determine the validity
     *   of the player's hand.
     * - If all cards are deemed invalid for play, the player is declared as having lost.
     * <p>
     * Key points of execution:
     * - If the player has valid cards to play, the game continues.
     * - If all cards are invalid, the player loses the game, the game state is marked as over,
     *   and the game transitions to an appropriate "loss" state.
     * - This method ensures that game rules regarding card values and points rules are upheld.
     * <p>
     * The method considers specific cases:
     * - Symbolic cards like "J", "Q", "K" are treated as 10 points but may apply a penalty
     *   if the game exceeds certain thresholds.
     * - The value of "A" is dynamically determined based on the current point total.
     * - Invalid cards result in setting a failure state for the player's ability to make further moves.
     * <p>
     * If a loss is confirmed, the method invokes loss handling mechanisms in the game controller:
     * - The player is flagged as*/
    public void loss (){
        boolean allCards =  false;
        if(gameController.getPlayerTurn() && !gameController.getGameOver()){
            for (CardModel card : gameController.getGameFacade().getGameModel().player.getHand()) {
                int number;
                boolean subtract = false;

                try {
                    number = Integer.parseInt(card.getNumber());
                    if(number == 9) {
                        number = 0;
                    }
                } catch (NumberFormatException ignored) {
                    switch (card.getNumber()) {
                        case "J":
                        case "Q":
                        case "K":
                            number = 10;
                            subtract = true;
                            break;
                        case "A":
                            number = (gameController.getGameFacade().getPoints() + 10 <= 50) ? 10 : 1;
                            break;
                        default:
                            continue;
                    }
                }
                if (subtract && gameController.getGameFacade().getPoints() > 40) {
                    allCards = false;
                    break;
                } else if (number + gameController.getGameFacade().getPoints() <= 50) {
                    allCards = false;
                    break;
                } else {
                    gameController.invalidCards = true;
                    allCards = true;
                }
            }

        }

        if (allCards) {
            gameController.setLossPlayer(true);
            gameController.setGameOver(true);
            gameController.playerLoss();
        }
    }
}
