package com.example.fiftypoints.controllers;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.GameModel;
import com.example.fiftypoints.views.DrawCard;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameController {
    private GameModel gameModel;
    private final DrawCard cardDraw = new DrawCard();
    private int points;
    private boolean playerTurn = true;
    private boolean machine, machine2, machine3;
    private final Random random = new Random();
    private int colum = 0;
    private Group group;
    private String cardNumber;
    private boolean[] lossPlayer = {false, false, false, false};

    @FXML
    private Label playerUsername, sumOfPoints, state;

    @FXML
    private GridPane playerGrid, machinesGrid, gameGrid, deckGrid;

    @FXML
    private Button throwCard, takeCard;

    @FXML
    public void initialize(String username, int machines) {
        this.gameModel = new GameModel(machines);
        playerUsername.setText(username);
        CardModel[] handPlayer = gameModel.player.getCards();
        setCardsGrid(handPlayer, playerGrid, 0);
        initializeMachines();
        initializeCard();
        setCard();
        setTurnMachines();
    }

    public void initializeMachines (){
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
        int aux = 0;
        int[] index = {0, 5, 10};
        if(gameModel.getMachines() == 1){
            index = new int[]{5};
        } else if(gameModel.getMachines() == 2){
            index = new int[]{3, 8};
        }
        for (CardModel[] cards : handsList) {
            setCardsGridMachine(cards, machinesGrid, index[aux]);
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

    public void setCardsGridMachine (CardModel[] cards, GridPane grid, int index){
        for (CardModel card : cards) {
            int num = random.nextInt(2);
            String color;
            if(num == 0){
                color  = "red";
            } else {
                color  = "black";
            }
            Group cardGroup = cardDraw.drawCardBack(color);
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
            Random random = new Random();
            this.points = (random.nextInt(2) == 0) ? 1 : 10;
        } else if(Objects.equals(startCart.getNumber(), "J") || Objects.equals(startCart.getNumber(), "Q") || Objects.equals(startCart.getNumber(), "K")){
            this.points = -10;
        } else if(Objects.equals(startCart.getNumber(), "9")){
            this.points = 0;
        } else {
            this.points = Integer.parseInt(startCart.getNumber());
        }
        String number = "POINTS " + this.points;
        sumOfPoints.setText(number);
        Group cardGroup = cardDraw.drawCard(startCart.getNumber(), startCart.getSuits());
        gameGrid.add(cardGroup, 0, 0);
        GridPane.setRowSpan(cardGroup, 2);
        GridPane.setHalignment(cardGroup, HPos.CENTER);
        GridPane.setValignment(cardGroup, VPos.CENTER);

        int num = random.nextInt(2);
        String color;
        if(num == 0){
            color  = "red";
        } else {
            color  = "black";
        }
        Group deck = cardDraw.drawCardBack(color);
        deckGrid.add(deck, 0, 0);
        GridPane.setRowSpan(deck, 2);
        GridPane.setHalignment(deck, HPos.CENTER);
        GridPane.setValignment(deck, VPos.CENTER);
    }

    private void setCard() {
        for (Node node : playerGrid.getChildren()) {
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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
                this.group = clickedGroup;
                this.colum = columnIndex;
            });

            node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                Group group = (Group) node;
                group.setScaleX(1.1);
                group.setScaleY(1.1);
            });

            node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
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

    public void turnManagement(){
        if(gameModel.deck.getDeck() == null){
            state.setText("Shuffle the cards");
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                gameModel.resetDeck();
            });
            pause.play();
        }
        if(playerTurn && !lossPlayer[0]){
            takeCard.setDisable(false);
            throwCard.setDisable(false);
            state.setText("Your turn");
        } else{
            takeCard.setDisable(true);
            throwCard.setDisable(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            if(machine){
                state.setText("Machine 1 turn");
            } else if (machine2){
                state.setText("Machine 2 turn");
            } else if (machine3){
                state.setText("Machine 3 turn");
            }

            pause.setOnFinished(event -> {
                if(machine && !lossPlayer[1]){
                    CardModel card = gameModel.machine.throwCard(points);
                    this.cardNumber = card.getNumber();
                    points += setNumber();
                    sumOfPoints.setText("Points: " + points);
                    CardModel[] cardForSet = {gameModel.startCard()};
                    gameModel.machine.setCards(cardForSet[0], gameModel.machine.getIndex());
                    setCardsGrid(cardForSet, gameGrid,0);
                    System.out.println("Carta 1");
                    if(loss(machine)){
                        lossPlayer[1] = true;
                    }
                    if(gameModel.getMachines() > 1){
                        machine2 = true;
                    } else {
                        playerTurn = true;
                    }
                    machine = false;
                    setCard();
                    turnManagement();
                } else if (machine2 && !lossPlayer[2]) {
                    CardModel card = gameModel.machineTwo.throwCard(points);
                    this.cardNumber = card.getNumber();
                    points += setNumber();
                    sumOfPoints.setText("Points: " + points);
                    CardModel[] cardForSet = {gameModel.startCard()};
                    gameModel.machineTwo.setCards(cardForSet[0], gameModel.machineTwo.getIndex());
                    setCardsGrid(cardForSet, gameGrid,0);
                    System.out.println("Carta 2");
                    if(loss(machine2)){
                        lossPlayer[2] = true;
                    }
                    if(gameModel.getMachines() > 2){
                        machine3 = true;
                    } else {
                        playerTurn = true;
                    }
                    machine2 = false;
                    setCard();
                    turnManagement();
                } else if (machine3 && !lossPlayer[3]) {
                    CardModel card = gameModel.machineThree.throwCard(points);
                    this.cardNumber = card.getNumber();
                    points += setNumber();
                    sumOfPoints.setText("Points: " + points);
                    CardModel[] cardForSet = {gameModel.startCard()};
                    gameModel.machineThree.setCards(cardForSet[0], gameModel.machineThree.getIndex());
                    setCardsGrid(cardForSet, gameGrid,0);
                    System.out.println("Carta 3");
                    playerTurn = true;
                    machine3 = false;
                    setCard();
                    if(loss(machine3)){
                        lossPlayer[3] = true;
                    }
                    turnManagement();
                }
            });
            pause.play();
        }
    }

    public void setTurnMachines(){
        int machines = gameModel.getMachines();
        if (machines == 1){
            machine = false;
        } else if(machines == 2){
            machine = false;
            machine2 = false;
        } else if(machines == 3){
            machine = false;
            machine2 = false;
            machine3 = false;
        }
    }

    public void removeCardHand(int Colum){
        colum = Colum;
        int num = random.nextInt(2);
        String color;
        if(num == 0){
            color  = "red";
        } else {
            color  = "black";
        }
        Group deck = cardDraw.drawCardBack(color);
        playerGrid.add(deck, colum, 0);
        GridPane.setRowSpan(deck, 2);
        GridPane.setHalignment(deck, HPos.CENTER);
        GridPane.setValignment(deck, VPos.CENTER);
        setCard();
    }

    public void throwCard(){
        points += setNumber();
        gameGrid.add(this.group, 0, 0);
        gameModel.player.throwCard(this.colum);
        removeCardHand(this.colum);
        sumOfPoints.setText("Points: " + points);
        setCard();
        this.playerTurn = false;

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            this.machine = true;
            turnManagement();
        });
        pause.play();
    }

    public void takeCard(){
        Random random = new Random();
        int num = random.nextInt(gameModel.deck.getDeck().size());
        ArrayList<CardModel> cards = gameModel.deck.getDeck();

        CardModel card = cards.get(num);
        gameModel.player.setCards(card, colum);
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
            number = 1;
            return number;
        } else {
            number = Integer.parseInt(cardNumber);
            return number;
        }
    }

    private boolean loss(boolean player){
        if(points > 50){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Hola" + player);
            alert.showAndWait();

            return true;
        }
        return false;
    }
}
