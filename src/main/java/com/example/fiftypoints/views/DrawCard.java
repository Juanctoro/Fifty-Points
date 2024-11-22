package com.example.fiftypoints.views;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DrawCard {
    public Group drawCard(String number, String suit) {
        Paint color;

        if (suit.equals("Hearts") || suit.equals("Diamonds")) {
            color = Color.RED;
        } else {
            color = Color.BLACK;
        }

        double cardWidth = 80;
        double cardHeight = 120;

        Rectangle cardBackground = new Rectangle(cardWidth, cardHeight);
        cardBackground.setArcWidth(15);
        cardBackground.setArcHeight(15);
        cardBackground.setFill(Color.WHITE);
        cardBackground.setStroke(Color.BLACK);

        Text topNumber = new Text(number);
        topNumber.setFont(Font.font("Arial", 22));
        topNumber.setFill(color);
        topNumber.setX(10);
        topNumber.setY(25);

        Text bottomNumber = new Text(number);
        bottomNumber.setFont(Font.font("Arial", 22));
        bottomNumber.setFill(color);
        bottomNumber.setX(60);
        bottomNumber.setY(110);
        try {
            if(Integer.parseInt(number) == 10){
                bottomNumber.setX(50);
                bottomNumber.setY(110);
            }
        } catch (Exception ignored) {}
        bottomNumber.setRotate(180);

        Text suitSymbol = new Text();
        suitSymbol.setFont(Font.font("Arial", 30));
        suitSymbol.setFill(color);
        suitSymbol.setX(30);
        suitSymbol.setY(70);

        switch (suit) {
            case "Hearts":
                suitSymbol.setText("♥");
                break;
            case "Diamonds":
                suitSymbol.setText("♦");
                break;
            case "Clubs":
                suitSymbol.setText("♣");
                break;
            case "Spades":
                suitSymbol.setText("♠");
                break;
            default:
                suitSymbol.setText("");
        }
        return new Group(cardBackground, topNumber, bottomNumber, suitSymbol);
    }

    public Group drawCardBack(String color) {
        double cardWidth = 80;
        double cardHeight = 120;

        Rectangle cardBackground = new Rectangle(cardWidth, cardHeight);
        cardBackground.setArcWidth(15);
        cardBackground.setArcHeight(15);

        if (color.equalsIgnoreCase("red")) {
            cardBackground.setFill(Color.RED);
        } else if (color.equalsIgnoreCase("black")) {
            cardBackground.setFill(Color.BLACK);
        } else {
            cardBackground.setFill(Color.DARKGRAY);
        }
        cardBackground.setStroke(Color.BLACK);

        Circle centerCircle = new Circle(cardWidth / 2, cardHeight / 2, 20);
        centerCircle.setFill(Color.WHITE);
        centerCircle.setStroke(Color.BLACK);

        Line diagonal1 = new Line(10, 10, cardWidth - 10, cardHeight - 10);
        diagonal1.setStroke(Color.WHITE);
        diagonal1.setStrokeWidth(2);

        Line diagonal2 = new Line(10, cardHeight - 10, cardWidth - 10, 10);
        diagonal2.setStroke(Color.WHITE);
        diagonal2.setStrokeWidth(2);

        return new Group(cardBackground, centerCircle, diagonal1, diagonal2);
    }

}
