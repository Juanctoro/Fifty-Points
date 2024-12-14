package com.example.fiftypoints.models;

public class lossTypes extends Exception {
    /**
     * Exception thrown to indicate that an invalid set of cards has been encountered
     * during gameplay or within the game logic.
     * <p>
     * This exception is useful for representing scenarios where a validation
     * mechanism determines that one or more cards violate the expected rules
     * or constraints of the game.
     */
    public static class InvalidCardsException extends Exception {
        public InvalidCardsException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown to indicate that the points accumulated during a game
     * or a specific operation have exceeded acceptable limits or thresholds.
     * <p>
     * This exception is typically used in scenarios where the validation logic
     * determines that the total score or point count has surpassed the game's
     * maximum allowable value, thus violating the established rules or constraints.
     */
    public static class ExcessPointsException extends Exception {
        public ExcessPointsException(String message) {
            super(message);
        }
    }
}
