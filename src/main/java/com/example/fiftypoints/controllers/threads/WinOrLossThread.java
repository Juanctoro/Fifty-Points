package com.example.fiftypoints.controllers.threads;

import com.example.fiftypoints.controllers.GameController;
import com.example.fiftypoints.models.CardModel;
import javafx.application.Platform;

import java.io.IOException;

public class WinOrLossThread extends Thread {
    private final GameController gameController;

    public WinOrLossThread(GameController gameController) {
        this.gameController = gameController;
    }

    public void run() {
        win();
        loss();
    }
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
