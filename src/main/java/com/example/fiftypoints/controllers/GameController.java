package com.example.fiftypoints.controllers;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.GameModel;
import com.example.fiftypoints.models.MachineModel;
import com.example.fiftypoints.views.DrawCard;
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
import java.util.Random;

public class GameController {
    private GameModel gameModel;
    private DrawCard cardDraw;
    private int points;
    private String username;
    private boolean playerTurn;
    private boolean[] machine;
    private final Random random = new Random();
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
    private Button throwCard;

    @FXML
    private RadioButton a1, a10;

    @FXML
    public void initialize(String username, int machines) {
        this.playerTurn = true;
        machinesGrid.getChildren().clear();
        this.cardDraw = new DrawCard();
        this.gameModel = new GameModel(machines);
        this.gameOver = false;
        this.username = username;
        playerUsername.setText(username);
        CardModel[] handPlayer = gameModel.player.getHand();
        setCardsGrid(handPlayer, playerGrid, 0);
        initializeMachines();
        initializeCard();
        setCard();
        this.machine = new boolean[machines];
        this.lossPlayer = new boolean[machines + 1];
        this.toggleGroupA = new ToggleGroup();
        a1.setStyle("-fx-font-size: 16px;");
        a10.setStyle("-fx-font-size: 16px;");
        a1.setToggleGroup(this.toggleGroupA);
        a10.setToggleGroup(this.toggleGroupA);
    }

    public void initializeMachines (){
        int aux = 0;
        int[] index = {0, 5, 10};
        ArrayList<CardModel[]> handsList = new ArrayList<>();
        if(gameModel.getMachines() == 1){
            handsList.add(gameModel.machine.getHand());
        } else if(gameModel.getMachines() == 2){
            handsList.add(gameModel.machine.getHand());
            handsList.add(gameModel.machineTwo.getHand());
        } else if(gameModel.getMachines() == 3){
            handsList.add(gameModel.machine.getHand());
            handsList.add(gameModel.machineTwo.getHand());
            handsList.add(gameModel.machineThree.getHand());
        }

        if(gameModel.getMachines() == 1){
            index = new int[]{5};
        } else if(gameModel.getMachines() == 2){
            index = new int[]{3, 8};
        }
        for (CardModel[] cards : handsList) {
            int indexCard = index[aux];
            for (CardModel ignored : cards) {
                String color = random.nextInt(2) == 0 ? "red" : "black";
                Group cardGroup = cardDraw.drawCardBack(color);
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
            Group cardGroup = cardDraw.drawCard(card.getNumber(), card.getSuits());
            grid.add(cardGroup, index, 0);
            GridPane.setRowSpan(cardGroup, 2);
            GridPane.setHalignment(cardGroup, HPos.CENTER);
            GridPane.setValignment(cardGroup, VPos.CENTER);

            index++;
        }
    }

    public void initializeCard(){
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
        String number = "Points: " + this.points;
        sumOfPoints.setText(number);
        Group cardGroup = cardDraw.drawCard(startCart.getNumber(), startCart.getSuits());
        gameGrid.add(cardGroup, 0, 0);
        GridPane.setRowSpan(cardGroup, 2);
        GridPane.setHalignment(cardGroup, HPos.CENTER);
        GridPane.setValignment(cardGroup, VPos.CENTER);

        String color = random.nextInt(2) == 0 ? "red" : "black";
        Group deck = cardDraw.drawCardBack(color);
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
                if(Objects.equals(this.cardNumber, "A")) {
                    a1.setVisible(true);
                    a10.setVisible(true);
                    throwCard.setDisable(false);
                    RadioButton selected = (RadioButton) toggleGroupA.getSelectedToggle();
                    if (selected != null) {
                        throwCard.setDisable(true);
                    }
                }
                this.group = clickedGroup;
                this.colum = columnIndex;

                if (this.cardNumber != null) {
                    throwCard.setDisable(false);
                }
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
        if(gameOver){
            return;
        }
        if (playerTurn && !lossPlayer[0]) {
            state.setText("Your turn");
            if(playerLoss()){
                gameOver = true;
            }
        } else {
            throwCard.setDisable(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            turns();

            pause.setOnFinished(event -> {
                if (machine[0] && !lossPlayer[1]) {
                    handleMachineTurn(gameModel.machine, 1);
                    if(gameModel.getMachines() > 1){
                        machine[1] = true;
                    } else {
                        playerTurn = true;
                    }
                    machine[0] = false;
                    setCard();
                    turnManagement();
                } else if (machine.length > 1 && machine[1] && !lossPlayer[2]) {
                    handleMachineTurn(gameModel.machineTwo, 2);
                    if(gameModel.getMachines() > 2){
                        machine[2] = true;
                    } else {
                        playerTurn = true;
                    }
                    machine[1] = false;
                    setCard();
                    turnManagement();
                } else if (machine.length > 2  && machine[2] && !lossPlayer[3]) {
                    handleMachineTurn(gameModel.machineThree, 3);
                    playerTurn = true;
                    machine[2] = false;
                    setCard();
                    turnManagement();
                }
            });
            pause.play();
        }
        win();
    }

    private void handleMachineTurn(MachineModel machine, int machineIndex) {
        if(gameOver){
            return;
        }
        CardModel card = machine.throwCard(points);
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
            points += setNumber();
            sumOfPoints.setText("Points: " + points);
            CardModel cardForSet = gameModel.startCard();
            machine.setCard(cardForSet, machine.getIndex());
            CardModel[] aux = {card};
            setCardsGrid(aux, gameGrid, 0);
        }
    }

    public void turns() {
        for (int i = 0; i < machine.length; i++) {
            if (machine[i]) {
                state.setText("Machine " + (i + 1) + " turn");
                if (lossPlayer.length > i + 1 && lossPlayer[i + 1]) {
                    machine[i] = false;
                    if (i + 1 < machine.length) {
                        machine[i + 1] = true;
                    } else {
                        playerTurn = true;
                    }
                    turnManagement();
                    return;
                }
                break;
            }
        }
        if (playerTurn) {
            state.setText("Your turn");
            playerLoss();
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
        throwCard.setDisable(true);
        points += setNumber();
        gameGrid.add(this.group, 0, 0);
        gameModel.player.throwCard(this.colum);
        removeCardHand(this.colum);
        sumOfPoints.setText("Points: " + points);
        setCard();
        this.playerTurn = false;

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            this.machine[0] = true;
            if (gameModel.deck.getDeck().size() == 1) {
                ArrayList<CardModel> cards = gameModel.deck.getDeck();
                gameModel.lastCard = cards.get(0);
            }
            takeCard();
            turnManagement();
        });
        pause.play();
        this.group = null;
    }

    public void takeCard(){
        int num = random.nextInt(gameModel.deck.getDeck().size());
        ArrayList<CardModel> cards = gameModel.deck.getDeck();

        CardModel card = cards.get(num);
        gameModel.player.setCard(card, colum);
        Group cardGroup = cardDraw.drawCard(card.getNumber(), card.getSuits());
        Platform.runLater(() -> {
            playerGrid.add(cardGroup, colum, 0);
            GridPane.setRowSpan(cardGroup, 2);
            GridPane.setHalignment(cardGroup, HPos.CENTER);
            GridPane.setValignment(cardGroup, VPos.CENTER);
        });
        gameModel.deck.subtractCard(card);
        setCard();
    }

    private int setNumber(){
        int number;
        if(Objects.equals(cardNumber, "J") || Objects.equals(cardNumber, "Q") || Objects.equals(cardNumber, "K")){
            number = -10;
            return number;
        } else if (Objects.equals(cardNumber, "9")) {
            number = 0;
            return number;
        } else if (Objects.equals(cardNumber, "A")) {
            if(playerTurn) {
                RadioButton selected = (RadioButton) toggleGroupA.getSelectedToggle();
                number = Integer.parseInt(selected.getText());
                a1.setVisible(false);
                a10.setVisible(false);
                return number;
            } else {
                return (points + 10 <= 50) ? 10 : 1;
            }

        } else {
            number = Integer.parseInt(cardNumber);
            return number;
        }
    }

    private void win() {
        if (gameOver) {
            return;
        }
        boolean allMachinesLost = true;

        for (int i = 1; i <= gameModel.getMachines(); i++) {
            if (!lossPlayer[i]) {
                allMachinesLost = false;
                break;
            }
        }

        if (allMachinesLost && !lossPlayer[0]) {
            gameOver = true;
            Platform.runLater(() -> {
                try {
                    exitView(false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            throwCard.setDisable(true);
        }
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

    private boolean playerLoss(){
        for (CardModel card : gameModel.player.getHand()) {
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
                        number = (this.points + 10 <= 50) ? 10 : 1;
                        break;
                    default:
                        continue;
                }
            }
            if (subtract && this.points > 40) {
                return false;
            } else if (number + this.points <= 50) {
                return false;
            }
        }

        gameOver = true;
        this.lossPlayer[0] = true;
        throwCard.setDisable(true);
        playerTurn = false;
        try {
            exitView(this.gameOver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void exitView(boolean game) throws IOException {
        ExitView exitView = new ExitView();
        exitView.setOnHiding(event -> {
            for (Window window : Stage.getWindows()) {
                if (window.isShowing()) {
                    window.hide();
                }
            }
        });
        exitView.show();
        ExitController exitViewController = exitView.getExitController();
        exitViewController.initialize(game, this.username);
    }
}
