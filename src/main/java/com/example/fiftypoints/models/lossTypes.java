package com.example.fiftypoints.models;

public class lossTypes extends Exception {
    public static class InvalidCardsException extends Exception {
        public InvalidCardsException(String message) {
            super(message);
        }
    }

    public static class ExcessPointsException extends Exception {
        public ExcessPointsException(String message) {
            super(message);
        }
    }
}
