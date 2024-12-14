package com.example.fiftypoints.controllers.threads;

import com.example.fiftypoints.controllers.GameController;

/**
 * The TurnsThread class, extending the {@code Thread} class, is responsible for
 * managing and executing background game turn calculations independently from
 * the main application thread. It ensures that the turn operations do not
 * interfere with the user interface or other critical application processes.
 * <p>
 * Instances of this class work with a shared {@code GameController} object
 * to manipulate game state data in a synchronized manner, thus preventing
 * concurrent modification issues.
 */
public class TurnsThread extends Thread {
    private final GameController gameController;

    /**
     * Constructs a new TurnsThread object that operates with the provided game controller
     * to handle and manage game turns in a separate thread.
     *
     * @param gameController the GameController instance used to manage and modify
     *                        the state of the game during thread execution
     */
    public TurnsThread(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Executes the sequence of operations for the thread, performing background
     * calculations related to the management of game turns. This method represents
     * the main entry point of the thread's execution logic and ensures that game
     * state updates are processed in a dedicated thread to maintain application
     * responsiveness.
     * <p>
     * The method internally invokes {@code executeBackgroundCalculations}, which
     * handles the turn-based logic for transitioning machine and player states,
     * ensuring proper synchronization with the associated {@code GameController}.
     */
    public void run() {
        executeBackgroundCalculations();
    }

    /**
     * Executes background calculations to manage the turn-based logic between machine
     * players and the human player in the game. This method is responsible for
     * advancing the game state in response to specific conditions while ensuring proper
     * synchronization to prevent concurrent modification issues.
     * <p>
     * Functional Details:
     * - Iterates over the machine players' state to determine the active machine involved in the current turn.
     * - Checks if the next machine player has been defeated (loss state) based on the player loss state array.
     * - Modifies the machine state to progress the turn to the next machine player*/
    private void executeBackgroundCalculations() {
        synchronized (gameController) {
            boolean[] machine = gameController.getMachineState();
            boolean[] lossPlayer = gameController.getLossPlayerState();

            for (int i = 0; i < machine.length; i++) {
                if (machine[i]) {
                    if (lossPlayer.length > i + 1 && lossPlayer[i + 1]) {
                        machine[i] = false;
                        if (i + 1 < machine.length) {
                            if (gameController.getGameFacade().getGameModel().getMachines() > i + 1){
                                machine[i + 1] = true;
                                gameController.setMachineState(machine);
                            } else{
                                gameController.setPlayerTurn(true);
                            }
                        } else {
                            gameController.setPlayerTurn(true);
                        }
                        gameController.turnManagement();
                        return;
                    }
                    break;
                }
            }
        }
    }
}
