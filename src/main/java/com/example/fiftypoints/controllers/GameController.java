package com.example.fiftypoints.controllers;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.GameModel;
import com.example.fiftypoints.models.MachineModel;
import com.example.fiftypoints.views.ExitView;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameController{
    private GameFacade gameFacade;
    private String username;
    private boolean playerTurn;
    private boolean[] machine;
    private int colum = 0;
    private Group group;
    private String cardNumber;
    private boolean[] lossPlayer = new boolean[4];
    private boolean gameOver = false;
    private ToggleGroup toggleGroupA;

    @FXML
    private Label playerUsername, state, sumOfPoints, machineLoss;

    @FXML
    private GridPane playerGrid, machinesGrid, gameGrid, deckGrid;

    @FXML
    public Button throwCard;

    @FXML
    private RadioButton a1, a10;

    @FXML
    public void initialize(String username, int machines) {
        machineLoss.setText("");
        playerUsername.setText(username);
        machinesGrid.getChildren().clear();
        this.gameFacade = new GameFacade(new GameModel(machines));
        this.playerTurn = true;
        this.gameOver = false;
        this.username = username;
        this.machine = new boolean[machines];
        this.lossPlayer = new boolean[machines + 1];
        this.toggleGroupA = new ToggleGroup();

        setCardsGrid(gameFacade.getGameModel().player.getHand(), playerGrid, 0);
        initializeMachines();
        initializeCard();
        setCard();

        a1.setStyle("-fx-font-size: 16px;");
        a10.setStyle("-fx-font-size: 16px;");
        a1.setToggleGroup(this.toggleGroupA);
        a10.setToggleGroup(this.toggleGroupA);
    }

    public void initializeMachines (){
        int aux = 0;
        int[] index = {0, 5, 10};
        ArrayList<CardModel[]> handsList = gameFacade.machines();
        if(gameFacade.getGameModel().getMachines() == 1){
            index = new int[]{5};
        } else if(gameFacade.getGameModel().getMachines() == 2){
            index = new int[]{3, 8};
        }
        for (CardModel[] cards : handsList) {
            int indexCard = index[aux];
            for (CardModel ignored : cards) {
                String color = gameFacade.getRandom().nextInt(2) == 0 ? "red" : "black";
                Group cardGroup = gameFacade.getDrawCard().drawCardBack(color);
                machinesGrid.add(cardGroup, indexCard, 0);
                GridPane.setRowSpan(cardGroup, 2);
                GridPane.setHalignment(cardGroup, HPos.CENTER);
                GridPane.setValignment(cardGroup, VPos.CENTER);
                indexCard++;
            }
            aux++;
        }
    }

    public void setCardsGrid (CardModel[] cards, GridPane grid, int index){
        for (CardModel card : cards) {
            Group cardGroup = gameFacade.getDrawCard().drawCard(card.getNumber(), card.getSuits());
            grid.add(cardGroup, index, 0);
            GridPane.setRowSpan(cardGroup, 2);
            GridPane.setHalignment(cardGroup, HPos.CENTER);
            GridPane.setValignment(cardGroup, VPos.CENTER);

            index++;
        }
    }

    public void initializeCard(){
        CardModel startCart = gameFacade.startCard();
        String number = "Points: " + gameFacade.getPoints();
        sumOfPoints.setText(number);
        Group cardGroup = gameFacade.getDrawCard().drawCard(startCart.getNumber(), startCart.getSuits());
        gameGrid.add(cardGroup, 0, 0);
        GridPane.setRowSpan(cardGroup, 2);
        GridPane.setHalignment(cardGroup, HPos.CENTER);
        GridPane.setValignment(cardGroup, VPos.CENTER);

        String color = gameFacade.getRandom().nextInt(2) == 0 ? "red" : "black";
        Group deck = gameFacade.getDrawCard().drawCardBack(color);
        deckGrid.add(deck, 0, 0);
        GridPane.setRowSpan(deck, 2);
        GridPane.setHalignment(deck, HPos.CENTER);
        GridPane.setValignment(deck, VPos.CENTER);
    }

    private void setCard() {
        if (playerTurn) {
            throwCard.setDisable(true);
        }

        for (Node node : playerGrid.getChildren()) {
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (!playerTurn) {
                    return;
                }
                Group clickedGroup = (Group) event.getSource();
                Integer columnIndex = GridPane.getColumnIndex(clickedGroup);

                for (Node n : playerGrid.getChildren()) {
                    if (n instanceof Group) {
                        for (Node child : ((Group) n).getChildren()) {
                            if (child instanceof Rectangle) {
                                ((Rectangle) child).setStroke(Color.BLACK);
                                ((Rectangle) child).setStrokeWidth(1);
                            }
                        }
                    }
                }

                for (Node child : clickedGroup.getChildren()) {
                    if (child instanceof Rectangle) {
                        ((Rectangle) child).setStroke(Color.web("#2dff28"));
                        ((Rectangle) child).setStrokeWidth(3);
                    }
                }

                this.cardNumber = getCardNumberFromGroup(clickedGroup);
                if (Objects.equals(this.cardNumber, "A")) {
                    a1.setVisible(true);
                    a10.setVisible(true);
                    throwCard.setDisable(true);

                    toggleGroupA.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            throwCard.setDisable(false);
                        }
                    });
                } else {
                    throwCard.setDisable(false);
                }

                this.group = clickedGroup;
                this.colum = columnIndex;
            });

            node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                if (!playerTurn) {
                    return;
                }
                Group group = (Group) node;
                group.setScaleX(1.1);
                group.setScaleY(1.1);
            });

            node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                if (!playerTurn) {
                    return;
                }
                Group group = (Group) node;
                group.setScaleX(1.0);
                group.setScaleY(1.0);
            });
        }
    }

    private String getCardNumberFromGroup(Group group) {
        for (Node child : group.getChildren()) {
            if (child instanceof Text text) {
                return text.getText();
            }
        }
        return null;
    }

    public void turnManagement() {
        TurnsThread turns = new TurnsThread(this);
        turns.start();
        if (playerTurn) {
            Platform.runLater(() -> state.setText("Your turn"));
            state.setDisable(false);
        } else {
            throwCard.setDisable(true);
            if(machine[0]){
                Platform.runLater(() -> state.setText("Machine's 1 turn"));
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
            pause.setOnFinished(event -> {
                if (machine[0] && !lossPlayer[1]) {
                    state.setText("Machine's 2 turn");
                    handleMachineTurn(gameFacade.getGameModel().machine, 1);
                    if(gameFacade.getGameModel().getMachines() > 1){
                        machine[1] = true;
                    } else {
                        playerTurn = true;
                    }
                    machine[0] = false;
                    setCard();
                    turnManagement();
                } else if (machine.length > 1 && machine[1] && !lossPlayer[2]) {
                    state.setText("Machine's 3 turn");
                    handleMachineTurn(gameFacade.getGameModel().machineTwo, 2);
                    if(gameFacade.getGameModel().getMachines() > 2){
                        machine[2] = true;
                    } else {
                        playerTurn = true;
                    }
                    machine[1] = false;
                    setCard();
                    turnManagement();
                } else if (machine.length > 2  && machine[2] && !lossPlayer[3]) {
                    handleMachineTurn(gameFacade.getGameModel().machineThree, 3);
                    playerTurn = true;
                    machine[2] = false;
                    setCard();
                    turnManagement();
                }
            });
            pause.play();
        }
        WinOrLossThread winOrLoss = new WinOrLossThread(this);
        winOrLoss.start();
    }

    private void handleMachineTurn(MachineModel machine, int machineIndex) {
        if(gameOver){
            return;
        }
        CardModel card = machine.throwCard(gameFacade.getPoints());
        if(card == null){
            lossPlayer[machineIndex] = true;
            if(machineIndex <=2 ){
                this.machine[machineIndex-1] = true;
            } else {
                this.playerTurn = true;
            }
            turnManagement();
            isLoss();
        } else {
            this.cardNumber = card.getNumber();
            gameFacade.setPoints(gameFacade.setNumber(cardNumber, false));
            sumOfPoints.setText("Points: " + gameFacade.getPoints());
            CardModel cardForSet = gameFacade.getGameModel().startCard();
            machine.setCard(cardForSet, machine.getIndex());
            CardModel[] aux = {card};
            setCardsGrid(aux, gameGrid, 0);
        }
    }

    public void removeCardHand(int colum){
        for (Node node : playerGrid.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);

            if (columnIndex != null && columnIndex == colum) {
                if (node instanceof Group groupNode) {
                    groupNode.getChildren().clear();
                }
                break;
            }
        }
    }

    public void throwCard(){
        this.playerTurn = false;
        throwCard.setDisable(true);
        state.setDisable(true);
        if(gameFacade.setNumber(cardNumber, true) == 11){
            RadioButton selected = (RadioButton) toggleGroupA.getSelectedToggle();
            int number = Integer.parseInt(selected.getText());
            a1.setVisible(false);
            a10.setVisible(false);
            gameFacade.setPoints(number);
        } else {
            gameFacade.setPoints(gameFacade.setNumber(cardNumber, true));
        }
        gameGrid.add(this.group, 0, 0);
        gameFacade.getGameModel().player.throwCard(this.colum);
        removeCardHand(this.colum);
        sumOfPoints.setText("Points: " + gameFacade.getPoints());
        setCard();

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            this.machine[0] = true;
            if (gameFacade.getGameModel().deck.getDeck().size() == 1) {
                ArrayList<CardModel> cards = gameFacade.getGameModel().deck.getDeck();
                gameFacade.getGameModel().lastCard = cards.get(0);
            }
            takeCard();
            turnManagement();
        });
        pause.play();
        this.group = null;
    }

    public void takeCard(){
        int num = gameFacade.getRandom().nextInt(gameFacade.getGameModel().deck.getDeck().size());
        ArrayList<CardModel> cards = gameFacade.getGameModel().deck.getDeck();

        CardModel card = cards.get(num);
        gameFacade.getGameModel().player.setCard(card, colum);
        Group cardGroup = gameFacade.getDrawCard().drawCard(card.getNumber(), card.getSuits());
        Platform.runLater(() -> {
            playerGrid.add(cardGroup, colum, 0);
            GridPane.setRowSpan(cardGroup, 2);
            GridPane.setHalignment(cardGroup, HPos.CENTER);
            GridPane.setValignment(cardGroup, VPos.CENTER);
        });
        gameFacade.getGameModel().deck.subtractCard(card);
        setCard();
    }

    public void isLoss(){
        int index = 0;
        StringBuilder text = new StringBuilder();
        for(boolean player : lossPlayer){
            if(player){
                text.append("Machine ").append(index).append(" loss ");
            }
            index++;
        }
        machineLoss.setText(text.toString());
    }

    public void playerLoss(){
        throwCard.setDisable(true);
        playerTurn = false;
        Platform.runLater(() -> {
            try {
                exitView(this.gameOver);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void exitView(boolean game) throws IOException {
        ExitView exitView = ExitView.getInstance();
        exitView.setOnHiding(event -> {
            for (Window window : Stage.getWindows()) {
                if (window.isShowing()) {
                    window.hide();
                }
            }
        });
        ExitController exitViewController = exitView.getExitController();
        exitViewController.initialize(game, this.username);
    }

    public synchronized boolean[] getMachineState() {
        return this.machine;
    }

    public synchronized void setMachineState(boolean[] machine) {
        this.machine = machine;
    }

    public synchronized boolean[] getLossPlayerState() {
        return this.lossPlayer;
    }

    public synchronized void setPlayerTurn(boolean isPlayerTurn) {
        this.playerTurn = isPlayerTurn;
    }
    public synchronized boolean getPlayerTurn() {
        return this.playerTurn;
    }

    public synchronized void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public synchronized GameFacade getGameFacade() {
        return gameFacade;
    }

    public synchronized void setLossPlayer(boolean lossPlayer){
        this.lossPlayer[0] = lossPlayer;
    }
}
