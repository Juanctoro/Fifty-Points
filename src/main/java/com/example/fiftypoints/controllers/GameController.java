package com.example.fiftypoints.controllers;

import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.GameModel;
import com.example.fiftypoints.models.MachineModel;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class GameController {
    private GameModel gameModel;
    private final DrawCard cardDraw = new DrawCard();
    private int points;
    private boolean playerTurn = true;
    private boolean[] machine;
    private final Random random = new Random();
    private int colum = 0;
    private Group group;
    private String cardNumber;
    private boolean[] lossPlayer = new boolean[4];
    private boolean gameOver = false;

    @FXML
    private Label playerUsername;
    @FXML
    private Label sumOfPoints;
    @FXML
    public Label state;
    @FXML
    private Label machineLoss;

    @FXML
    private GridPane playerGrid, machinesGrid, gameGrid, deckGrid;

    @FXML
    private Button throwCard;

    @FXML
    public void initialize(String username, int machines) {
        this.gameModel = new GameModel(machines);
        this.gameOver = false;
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
        for (CardModel ignored : cards) {
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

                // Restablecer los estilos de todas las cartas
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

                // Aplicar estilo a la carta seleccionada
                for (Node child : clickedGroup.getChildren()) {
                    if (child instanceof Rectangle) {
                        ((Rectangle) child).setStroke(Color.web("#2dff28"));
                        ((Rectangle) child).setStrokeWidth(3);
                    }
                }

                // Obtener el número de carta de la carta seleccionada
                this.cardNumber = getCardNumberFromGroup(clickedGroup);
                this.group = clickedGroup;
                this.colum = columnIndex;

                // Habilitar el botón si se ha seleccionado una carta
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
        } else {
            throwCard.setDisable(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
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
            machine.out();
            lossPlayer[machineIndex] = true;
            if(machineIndex <=2 ){
                this.machine[machineIndex-1] = true;
            } else {
                this.playerTurn = true;
            }
            System.out.println(Arrays.toString(lossPlayer));
            turnManagement();
            isLoss();
        } else {
            this.cardNumber = card.getNumber();
            points += setNumber();
            sumOfPoints.setText("Points: " + points);
            CardModel cardForSet = gameModel.startCard();
            machine.setCards(cardForSet, machine.getIndex());
            CardModel[] aux = {card};
            setCardsGrid(aux, gameGrid, 0);
        }
    }

    public void setTurnMachines(){
        int machines = gameModel.getMachines();
        this.machine = new boolean[machines];
        this.lossPlayer = new boolean[machines + 1];
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
        System.out.println(points);
        points += setNumber();
        System.out.println(points);
        gameGrid.add(this.group, 0, 0);
        gameModel.player.throwCard(this.colum);
        removeCardHand(this.colum);
        sumOfPoints.setText("Points: " + points);
        setCard();
        this.playerTurn = false;

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            this.machine[0] = true;
            if (gameModel.deck.getDeck().size() == 1) {
                ArrayList<CardModel> cards = gameModel.deck.getDeck();
                gameModel.lastCard = cards.get(0);
                System.out.println("last card: " + gameModel.lastCard);
            }
            takeCard();
            turnManagement();
        });
        pause.play();
        this.group = null;
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Victory!");
                alert.setHeaderText(null);
                alert.setContentText("Congratulations! You have won the game!");
                alert.showAndWait();
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
}
