package com.example.fiftypoints.controllers;

import javafx.application.Platform;

public class Turns extends Thread {
    private final GameController gameController;

    public Turns(GameController gameController) {
        this.gameController = gameController;
    }

    public void run() {
        while (!gameController.getGameOver()) {
            executeBackgroundCalculations();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeBackgroundCalculations() {
        synchronized (gameController) {
            boolean[] machine = gameController.getMachineState();
            boolean[] lossPlayer = gameController.getLossPlayerState();
            boolean playerTurn = gameController.getPlayerTurn();

            for (int i = 0; i < machine.length; i++) {
                if (machine[i]) {
                    if (lossPlayer.length > i + 1 && lossPlayer[i + 1]) {
                        machine[i] = false;
                        if (i + 1 < machine.length) {
                            machine[i + 1] = true;
                            gameController.setMachineState(machine);
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
