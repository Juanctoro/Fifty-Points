package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.IMachine;
import com.example.fiftypoints.models.abstracts.HandModel;

/**
 * Represents a machine player's hand in the game. This class extends the functionality of
 * HandModel to implement specific behaviors for machine players, adhering to the IMachine interface.
 */
public class MachineModel extends HandModel implements IMachine {
    private int currentIndex = 0;

    /**
     * Constructs a MachineModel instance with the specified set of cards.
     *
     * @param cards an array of CardModel objects representing the initial cards in the machine player's hand
     */
    public MachineModel(CardModel[] cards) {
        super(cards);
    }

    /**
     * Throws a card from the current hand that satisfies specific conditions based on its value
     * and the provided position. The method assesses each card in the hand and attempts to find
     * a card whose number aligns with the game's scoring rules. If found, the card is removed
     * from the hand and returned.
     *
     * @param position the current position in the game, used to determine whether a card can be
     *                 played based on its value
     * @return the card that satisfies the conditions and is removed from the hand,
     *         or null if no suitable card is found
     */
    public CardModel throwCard(int position) {
        int index = 0;
        for (CardModel card : cards) {
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
                        number = (position + 10 <= 50) ? 10 : 1;
                        break;
                    default:
                        continue;
                }
            }
            if (subtract && position > 40) {
                removeCard(index);
                currentIndex = index;
                return card;
            } else if (number + position <= 50) {
                removeCard(index);
                currentIndex = index;
                return card;
            }
            index++;
        }
        return null;
    }

    /**
     * Retrieves the current index stored in the MachineModel.
     *
     * @return the current index as an integer
     */
    public int getIndex() {
        return currentIndex;
    }
}
