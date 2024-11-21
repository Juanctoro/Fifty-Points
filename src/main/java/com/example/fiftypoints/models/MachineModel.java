package com.example.fiftypoints.models;

import java.util.Objects;

public class MachineModel {
    CardModel[] cards;

    public MachineModel(CardModel[] hand) {
        cards = hand;
    }

    public CardModel throwCard(int position) {
        for (CardModel card : cards) {
            int number;
            boolean subtract = false;

            try {
                number = Integer.parseInt(card.getNumber());
                if(number == 9) {
                    number = 0;
                }
            } catch (NumberFormatException ignored) {
                subtract = true;
                switch (card.getNumber()) {
                    case "J":
                    case "Q":
                    case "K":
                        number = 10;
                        break;
                    case "A":
                        number = (position + 10 <= 50) ? 10 : 1;
                        break;
                    default:
                        continue;
                }
            }
            if (subtract && position > 40) {
                return card;
            } else if (number + position <= 50) {
                return card;
            }
        }
        return null;
    }

    public CardModel[] getCards() {
        return cards;
    }
}
