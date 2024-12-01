package com.example.fiftypoints.models;

import com.example.fiftypoints.interfaces.IMachine;

public class MachineModel extends HandModel implements IMachine {
    private int index = 0;

    public MachineModel(CardModel[] cards) {
        super(cards);
    }

    public CardModel throwCard(int position) {
        this.index = 0;
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
                removeCard(this.index);
                return card;
            } else if (number + position <= 50) {
                removeCard(this.index);
                return card;
            }
            this.index++;
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
