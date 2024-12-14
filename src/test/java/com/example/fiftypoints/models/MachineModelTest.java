package com.example.fiftypoints.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MachineModelTest {

    @Test
    void testThrowCard_NumberParsingNineToZero() {
        CardModel[] cards = {new CardModel("9", "hearts"), new CardModel("5", "diamonds")};
        MachineModel machine = new MachineModel(cards);

        CardModel thrownCard = machine.throwCard(40);

        assertNotNull(thrownCard);
        assertEquals("9", thrownCard.getNumber());
        assertEquals(0, machine.getIndex());
    }

    @Test
    void testThrowCard_NumberOverFifty_NotRemoved() {
        CardModel[] cards = {new CardModel("10", "hearts")};
        MachineModel machine = new MachineModel(cards);

        CardModel thrownCard = machine.throwCard(44);
        assertNull(thrownCard);
    }

    @Test
    void testThrowCard_AboveTen_SubtractionCondition() {
        CardModel[] cards = {new CardModel("J", "spades"), new CardModel("A", "clubs")};
        MachineModel machine = new MachineModel(cards);

        CardModel thrownCard = machine.throwCard(41);

        assertNotNull(thrownCard);
        assertEquals("J", thrownCard.getNumber());
        assertEquals(0, machine.getIndex());
    }

    @Test
    void testThrowCard_ParseAce_AsTen() {
        CardModel[] cards = {new CardModel("A", "hearts")};
        MachineModel machine = new MachineModel(cards);

        CardModel thrownCard = machine.throwCard(35);

        assertNotNull(thrownCard);
        assertEquals("A", thrownCard.getNumber());
        assertEquals(0, machine.getIndex());
    }

    @Test
    void testThrowCard_ParseAce_AsOne() {
        CardModel[] cards = {new CardModel("A", "diamonds")};
        MachineModel machine = new MachineModel(cards);

        CardModel thrownCard = machine.throwCard(45);

        assertNotNull(thrownCard);
        assertEquals("A", thrownCard.getNumber());
        assertEquals(0, machine.getIndex());
    }

    @Test
    void testThrowCard_RemoveNonNumberCardOverPosition() {
        CardModel[] cards = {new CardModel("K", "clubs")};
        MachineModel machine = new MachineModel(cards);

        CardModel thrownCard = machine.throwCard(45);

        assertNotNull(thrownCard);
        assertEquals("K", thrownCard.getNumber());
        assertEquals(0, machine.getIndex());
    }
}