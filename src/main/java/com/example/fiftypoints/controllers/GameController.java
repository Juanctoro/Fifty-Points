package com.example.fiftypoints.controllers;

import com.example.fiftypoints.controllers.facade.GameFacade;
import com.example.fiftypoints.controllers.strategy.CardDrawingStrategy;
import com.example.fiftypoints.controllers.strategy.StandardCardDrawingStrategy;
import com.example.fiftypoints.controllers.threads.TurnsThread;
import com.example.fiftypoints.controllers.threads.WinOrLossThread;
import com.example.fiftypoints.models.CardModel;
import com.example.fiftypoints.models.GameModel;
import com.example.fiftypoints.models.MachineModel;
import com.example.fiftypoints.views.ExitView;
import com.example.fiftypoints.models.lossTypes;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

/**
 * The GameController class is responsible for managing the core mechanics and user interface
 * of the game. It handles player interactions, machine actions, card management, and game state.
 * This class facilitates the communication between the frontend (UI) and the backend (game logic).
 */
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
    private CardDrawingStrategy cardDrawingStrategy;
    private int indexRemove;
    private boolean passFiftyPoints = false;
    public boolean invalidCards = false;

    @FXML
    private Label playerUsername, state, sumOfPoints, machineLoss;

    @FXML
    private GridPane playerGrid, machinesGrid, gameGrid, deckGrid;

    @FXML
    public Button throwCard;

    @FXML
    private RadioButton a1, a10;

    /**
     * Initializes the game interface and game state with the given username and number of machines.
     * Sets up the game facade, player hand, machines, and game UI elements.
     *
     * @param username the username of the player
     * @param machines the number of machines in the game
     */
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
        this.cardDrawingStrategy = new StandardCardDrawingStrategy(this);

        setCardsGrid(gameFacade.getGameModel().player.getHand(), playerGrid, 0);
        initializeMachines();
        initializeCard();
        setCard();

        a1.setStyle("-fx-font-size: 16px;");
        a10.setStyle("-fx-font-size: 16px;");
        a1.setToggleGroup(this.toggleGroupA);
        a10.setToggleGroup(this.toggleGroupA);
    }

    /**
     * Sets up the initial state of the machines' hands and places their card representations
     * on the game grid. The method retrieves a list of the machines' hands, determines the
     * column indices for placing the cards on the grid, and updates the visual representation
     * of each machine's hand by adding card backs to the grid.
     * <p>
     * The method performs the following operations:
     * - Retrieves the card indices for each machine using the calculateMachineIndices method.
     * - Fetches the hands of all machines as a list of arrays of CardModel.
     * - Iterates over the machines' hands and updates the corresponding positions in the
     *   machinesGrid by placing card representations using the cardDrawingStrategy.
     * - Ensures each card in the machine's hand is visually represented in sequential grid positions.
     */
    public void initializeMachines (){
        int aux = 0;
        int[] index = calculateMachineIndices();
        ArrayList<CardModel[]> handsList = gameFacade.machines();
        for (CardModel[] cards : handsList) {
            int indexCard = index[aux];
            for (CardModel ignored : cards) {
                Group cardGroup = cardDrawingStrategy.drawCardBack();
                cardDrawingStrategy.addCardToGridPane(cardGroup, machinesGrid, indexCard,0);
                indexCard++;
            }
            aux++;
        }
    }

    /**
     * Arranges an array of cards in the specified grid at consecutive positions starting from the given index.
     * Each card is visually represented as a group and added to the grid using the cardDrawingStrategy.
     *
     * @param cards the array of CardModel objects to be displayed on the grid
     * @param grid the GridPane where the cards will be arranged
     * @param index the starting column index in the grid for placing the first card
     */
    public void setCardsGrid (CardModel[] cards, GridPane grid, int index){
        for (CardModel card : cards) {
            Group cardGroup = cardDrawingStrategy.drawCard(card);
            cardDrawingStrategy.addCardToGridPane(cardGroup, grid, index,0);
            index++;
        }
    }

    /**
     * Initializes the player's card and updates the game interface with the starting state.
     * <p>
     * This method performs the following actions:
     * - Retrieves the starting card using the gameFacade's startCard method.
     * - Updates the score display with the current points retrieved from the gameFacade.
     * - Creates a visual representation of the starting card using the cardDrawingStrategy
     *   and places it on the game grid at the first position.
     * - Draws the card back to represent the deck visually and places it in the deck grid.
     */
    public void initializeCard(){
        CardModel startCart = gameFacade.startCard();
        String number = "Points: " + gameFacade.getPoints();
        sumOfPoints.setText(number);
        Group cardGroup = cardDrawingStrategy.drawCard(startCart);
        cardDrawingStrategy.addCardToGridPane(cardGroup, gameGrid, 0,0);

        Group deck = cardDrawingStrategy.drawCardBack();
        cardDrawingStrategy.addCardToGridPane(deck, deckGrid,0,0);
    }

    /**
     * Configures the player's card selection and interaction functionality within the player grid.
     * <p>
     * This method is responsible for enabling player interactions with the card grid during their turn.
     * It highlights selected cards, handles special cases for certain card types (e.g., "A"), and
     * visually scales cards when hovered. The method ensures proper UI feedback and updates associated
     * properties when a card is clicked or hovered.
     * <p>
     * Functional details:
     * - Disables the throwCard button if it's not the player's turn.
     * - Registers mouse event handlers for each card in the `playerGrid`:
     *   - Clicking a card:
     *     - Highlights the clicked card with a green border.
     *     - Resets the stroke color and width of other cards.
     *     - Updates the `cardNumber` and triggers specific logic for the "A" card type, managing its
     *       visibility and enabling/disabling the `throwCard` button accordingly.
     *     - Assigns the clicked card's group and column index to corresponding class-level variables.
     *   - Hovering over a card:
     *     - Scales the card up slightly while the mouse is over it.
     *     - Resets the scale when the mouse exits the card area.
     * <p>
     * Additional notes:
     * - The "A" card type allows a toggle selection between two card representations (e.g., "a1" and "a10"),
     *   managed by a toggle group (`toggleGroupA`).
     * - Any interaction depends on the `playerTurn` flag, ensuring that the player can only interact with
     *   the grid during their turn.
     * <p>
     * Preconditions:
     * - `playerGrid` must be initialized and populated with card elements.
     * - Necessary UI elements like `throwCard`, `a1`, `a10`, and `toggleGroupA` must be properly initialized.
     * <p>
     * Post conditions:
     * - The UI updates dynamically based on the player's card interactions.
     * - The selected card's details and state changes are reflected within the game logic.
     */
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
                    toggleGroupA.selectToggle(null);
                    a1.setVisible(true);
                    a10.setVisible(true);
                    throwCard.setDisable(true);

                    toggleGroupA.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            throwCard.setDisable(false);
                        }
                    });
                } else {
                    a1.setVisible(false);
                    a10.setVisible(false);
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

    /**
     * Extracts and returns the card number from the given group by checking the text content
     * of its children nodes.
     *
     * @param group the Group object whose children will be inspected to find a Text node containing the card number
     * @return the text content of the first Text node found in the group's children, or null if no such node exists
     */
    private String getCardNumberFromGroup(Group group) {
        for (Node child : group.getChildren()) {
            if (child instanceof Text text) {
                return text.getText();
            }
        }
        return null;
    }

    /**
     * Manages the execution of turns in the game for the player and machine players.
     * <p>
     * This method handles the turn-based logic by alternating between the player and machine players.
     * It updates the game state, displays relevant UI changes, and ensures proper sequencing of player
     * and machine turns.
     * <p>
     * Functional Details:
     * - If the game is over, the method exits immediately.
     * <p>
     * - A new thread (`TurnsThread`) is created and started to handle certain turn computations in the background.
     * <p>
     * - Handles the player's turn:
     *   - Displays the "Your turn" message and enables the appropriate UI components to allow the player
     *     to make their move.
     * <p>
     * - Handles machine turns:
     *   - Disables the player's interaction controls while the machine takes a turn.
     *   - Sets up a brief pause (`PauseTransition`) before executing the machine's turn logic to create
     *     a delay.
     *   - Iterates through the list of machines to determine the active machine.
     *     - Updates the UI to show the machine's turn progress.
     *     - Executes the `handleMachineTurn` method for the machine taking its turn.
     *     - Adjusts turn assignments for the next machine or the player based on the turn sequence.
     *     - Recursively calls `turnManagement()` to continue the flow of the game.
     * <p>
     * - Starts a new thread (`WinOrLossThread`) to check for win or loss conditions after each turn.
     * <p>
     * Preconditions:
     * - The game must be initialized and ready for turn-based progression.
     * - All relevant UI components, game objects, and state variables must be properly set up.
     * <p>
     * Post conditions:
     * - The player or machine turn is executed, and the next turn is prepared.
     * - The UI components are updated to reflect the current turn.
     * - The game progress is checked for potential win or loss conditions.
     */
    public void turnManagement() {
        if (gameOver) {
            return;
        }
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
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(event -> {
                for (int i = 0; i < gameFacade.getGameModel().getMachines(); i++) {
                    if (machine[i] && !lossPlayer[i + 1]) {
                        state.setText("Machine's " + (i + 2) + " turn");
                        if(i==2){
                            state.setText("Machine's " + (i + 1) + " turn");
                        }
                        if (gameFacade.getGameModel().getMachines() > i + 1) {
                            machine[i + 1] = true;
                        } else {
                            playerTurn = true;
                        }
                        handleMachineTurn(getMachineByIndex(i), i + 1);
                        machine[i] = false;
                        setCard();
                        turnManagement();
                        break;
                    }
                }
            });
            pause.play();
        }
        WinOrLossThread winOrLoss = new WinOrLossThread(this);
        winOrLoss.start();
    }

    /**
     * Retrieves a specific machine model based on the given index.
     * <p>
     * The method allows you to access one of the available machine models in the game by specifying
     * its index. Depending on the index provided, it returns the appropriate machine instance from
     * the current game model or throws an exception if the index is invalid.
     *
     * @param index the index of the machine to retrieve (0 for the first machine, 1 for the second, etc.)
     * @return the MachineModel corresponding to the provided index
     * @throws IllegalArgumentException if the index is not within the valid range of machine indices
     */
    private MachineModel getMachineByIndex(int index) {
        return switch (index) {
            case 0 -> gameFacade.getGameModel().machine;
            case 1 -> gameFacade.getGameModel().machineTwo;
            case 2 -> gameFacade.getGameModel().machineThree;
            default -> throw new IllegalArgumentException("Invalid machine index: " + index);
        };
    }

    /**
     * Handles the turn for a machine player in the game. This method determines the actions the machine takes
     * based on the current state of the game and updates the game state accordingly. Actions include throwing
     * a card, detecting loss conditions, and preparing the game for the next turn.
     *
     * @param machine       The machine player model that is executing the turn.
     * @param machineIndex  The index of the machine player, used to distinguish between multiple machine instances.
     */
    private void handleMachineTurn(MachineModel machine, int machineIndex) {
        if(gameOver){
            return;
        }
        CardModel card = machine.throwCard(gameFacade.getPoints());
        if(card == null){
            lossPlayer[machineIndex] = true;
            gameFacade.getGameModel().lossMachine(machine, machineIndex);
            removeCardsMachines(machineIndex - 1);
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
            int[] index = calculateMachineIndices();
            int indexCard = machine.getIndex();
            indexRemove = index[machineIndex-1] + indexCard;
            removeCardHand(machinesGrid, indexRemove);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                Group cardSet = cardDrawingStrategy.drawCardBack();
                cardDrawingStrategy.addCardToGridPane(cardSet, machinesGrid, indexRemove,0);
                if(machineIndex == 3){
                    playerTurn = true;
                    turnManagement();
                }
            });
            pause.play();
            CardModel[] aux = {card};
            setCardsGrid(aux, gameGrid, 0);
        }
    }

    /**
     * Removes a card from the specified column in the given GridPane.
     *
     * @param gridPane the GridPane from which the card will be removed
     * @param colum the column index of the card to be removed
     */
    public void removeCardHand(GridPane gridPane, int colum){
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);

            if (columnIndex != null && columnIndex == colum) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
    }

    /**
     * Removes the specified machine and its associated cards from the grid.
     *
     * @param machineIndex the index of the machine to be removed, corresponding
     *                     to the position in the machine indices array. Must
     *                     be within the valid range of indices.
     * @throws IllegalArgumentException if the provided machineIndex is out of bounds.
     */
    public void removeCardsMachines(int machineIndex) {
        int[] index = calculateMachineIndices();
        if (machineIndex < 0 || machineIndex >= index.length) {
            throw new IllegalArgumentException("Invalid machine index: " + machineIndex);
        }
        int startIndex = index[machineIndex];
        for (int i = 0; i < 4; i++) {
            int currentIndex = startIndex + i;

            machinesGrid.getChildren().removeIf(node -> {
                Integer column = GridPane.getColumnIndex(node);
                Integer row = GridPane.getRowIndex(node);
                return column != null && column == currentIndex && row != null && row == 0;
            });
        }
    }

    /**
     * Calculates and returns the machine indices based on the number of machines
     * available in the game model. The indices vary depending on the number of
     * machines retrieved from the game model.
     *
     * @return an array of integers representing the machine indices. For 1 machine,
     * returns {5}. For 2 machines, returns {3, 8}. For any other number of machines,
     * returns {0, 5, 10}.
     */
    private int[] calculateMachineIndices() {
        return switch (gameFacade.getGameModel().getMachines()) {
            case 1 -> new int[]{5};
            case 2 -> new int[]{3, 8};
            default -> new int[]{0, 5, 10};
        };
    }

    /**
     * Handles the action of throwing a card during the player's turn in the game.
     * This method performs various operations associated with the game logic,
     * including updating the game state, modifying UI components, and managing
     * the player's actions and points.
     * <p>
     * Functionalities include:
     * - Disabling the player's turn and related controls.
     * - Setting the number associated with the card and updating scores.
     * - Hiding and updating UI components for the player's card actions.
     * - Managing the player's card grid and the overall game state.
     * - Checking if the conditions for game over are met and triggering the loss state if needed.
     * - Setting up the next state for the game, including enabling the machine's turn
     *   and handling the scenario where only one card is left in the deck.
     * - Invoking auxiliary methods such as `removeCardHand`, `setCard`,
     *   `takeCard`, and `turnManagement` as part of the game progression logic.
     * <p>
     * Schedules a pause transition to allow smooth sequential execution of the machine's
     * actions after the player's turn has concluded.
     */
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
        removeCardHand(playerGrid,this.colum);
        sumOfPoints.setText("Points: " + gameFacade.getPoints());
        if(gameFacade.getPoints() > 50){
            gameOver = true;
            lossPlayer[0] = true;
            passFiftyPoints = true;
            playerLoss();
        }
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

    /**
     * This method handles the process of drawing a random card from the deck and assigning it to the player.
     * The card drawn is removed from the deck and rendered on the game interface.
     * <p>
     * The steps involved are:
     * 1. Randomly selects a card from the deck.
     * 2. Assigns the selected card to the player in a specified column.
     * 3. Draws the graphical representation of the card.
     * 4. Adds the graphical representation of the card to the player's grid in the game UI on the JavaFX application thread.
     * 5. Removes the selected card from the deck.
     * 6. Finalizes the card setup using the `setCard()` method.
     */
    public void takeCard(){
        int num = gameFacade.getRandom().nextInt(gameFacade.getGameModel().deck.getDeck().size());
        ArrayList<CardModel> cards = gameFacade.getGameModel().deck.getDeck();

        CardModel card = cards.get(num);
        gameFacade.getGameModel().player.setCard(card, colum);
        Group cardGroup = cardDrawingStrategy.drawCard(card);
        Platform.runLater(() -> cardDrawingStrategy.addCardToGridPane(cardGroup, playerGrid,colum,0));

        gameFacade.getGameModel().deck.subtractCard(card);
        setCard();
    }

    /**
     * This method determines which machines are in a "loss" state and updates
     * the machineLoss text accordingly. It iterates over the lossPlayer array,
     * checks each player's status, and appends information about losing machines
     * to a StringBuilder. The resulting text is then displayed in the machineLoss
     * component.
     */
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

    /**
     * Handles the scenario where a player loses the game.
     * This method disables the card throwing functionality for the player,
     * sets the player's turn status to false, and transitions to the game over view.
     * The transition is executed on the JavaFX Application Thread to ensure UI updates
     * are performed in a thread-safe manner.
     *
     * @throws RuntimeException if an IOException occurs during the transition to the game over view.
     */
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

    /**
     * Handles the logic for exiting the current view. It evaluates loss conditions
     * and initializes the exit view with appropriate messages and settings based on the game state.
     *
     * @param game Represents the state of the game. If true, the game is in progress; otherwise, it denotes game termination.
     * @throws IOException If an input or output operation fails or is interrupted.
     */
    public void exitView(boolean game) throws IOException {
        String message = "You beat the machines";
        try {
            CheckLossType(invalidCards, passFiftyPoints);
        } catch (lossTypes.ExcessPointsException | lossTypes.InvalidCardsException e) {
            message = e.getMessage();
        }
        ExitView exitView = ExitView.getInstance();
        exitView.setOnHiding(event -> {
            for (Window window : Stage.getWindows()) {
                if (window.isShowing()) {
                    window.hide();
                }
            }
        });
        ExitController exitViewController = exitView.getExitController();
        exitViewController.initialize(game, this.username, message);
    }

    /**
     * Evaluates the loss condition based on the provided flags for invalid cards and exceeding points.
     *
     * @param invalidCards a boolean indicating whether the player has invalid cards. If true, an InvalidCardsException is thrown.
     * @param passFiftyPoints a boolean indicating whether the player has exceeded fifty points. If true, an ExcessPointsException is thrown.
     * @throws lossTypes.InvalidCardsException if the invalidCards parameter is true.
     * @throws lossTypes.ExcessPointsException if the passFiftyPoints parameter is true.
     */
    public void CheckLossType(boolean invalidCards, boolean passFiftyPoints) throws lossTypes.InvalidCardsException, lossTypes.ExcessPointsException {
        if (invalidCards) {
            throw new lossTypes.InvalidCardsException("You have no cards to throw away");
        }
        if (passFiftyPoints) {
            throw new lossTypes.ExcessPointsException("You passed 50 points");
        }
    }

    /**
     * Retrieves the current state of the machine as an array of boolean values.
     * The returned array represents the state of each respective component in the machine.
     *
     * @return a boolean array representing the current state of the machine
     */
    public synchronized boolean[] getMachineState() {
        return this.machine;
    }

    /**
     * Updates the machine state with the provided state array.
     *
     * @param machine an array of boolean values representing the new state of the machine
     */
    public synchronized void setMachineState(boolean[] machine) {
        this.machine = machine;
    }

    /**
     * Retrieves the current loss state of players.
     *
     * @return An array of boolean values where each element represents
     *         the loss state of a specific player. True indicates the player has lost,
     *         while false indicates the player has not lost.
     */
    public synchronized boolean[] getLossPlayerState() {
        return this.lossPlayer;
    }

    /**
     * Sets the current player's turn.
     *
     * @param isPlayerTurn a boolean value indicating whether it is the player's turn.
     *                     True if it is the player's turn; false otherwise.
     */
    public synchronized void setPlayerTurn(boolean isPlayerTurn) {
        this.playerTurn = isPlayerTurn;
    }
    /**
     * Retrieves the current player's turn status.
     *
     * @return true if it is the player's turn, false otherwise.
     */
    public synchronized boolean getPlayerTurn() {
        return this.playerTurn;
    }

    /**
     * Sets the game over state.
     *
     * @param gameOver a boolean value indicating whether the game is over (true) or not (false)
     */
    public synchronized void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public synchronized boolean getGameOver() {
        return gameOver;
    }

    public synchronized GameFacade getGameFacade() {
        return gameFacade;
    }

    public synchronized void setLossPlayer(boolean lossPlayer){
        this.lossPlayer[0] = lossPlayer;
    }
}
