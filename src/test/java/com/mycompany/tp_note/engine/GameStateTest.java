package com.mycompany.tp_note.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void testInitialState() {
        GameState state = new GameState("TEST", 5);
        assertEquals("TEST", state.getSecretWord());
        assertEquals(5, state.getMaxErrors());
        assertEquals(0, state.getErrorsCount());
        assertEquals(GameState.Status.PLAYING, state.getCurrentStatus());
        assertTrue(state.getGuessedLetters().isEmpty());
    }

    @Test
    void testMaskedWord() {
        GameState state = new GameState("TEST", 5);
        assertEquals("_ _ _ _", state.getMaskedWord());

        state.addGuessedLetter('T');
        assertEquals("T _ _ T", state.getMaskedWord());

        state.addGuessedLetter('E');
        assertEquals("T E _ T", state.getMaskedWord());
    }

    @Test
    void testIsWordGuessed() {
        GameState state = new GameState("HI", 5);
        assertFalse(state.isWordGuessed());

        state.addGuessedLetter('H');
        assertFalse(state.isWordGuessed());

        state.addGuessedLetter('I');
        assertTrue(state.isWordGuessed());
    }

    @Test
    void testStatusUpdate() {
        GameState state = new GameState("A", 1);
        state.setStatus(GameState.Status.WON);
        assertEquals(GameState.Status.WON, state.getCurrentStatus());
    }

    @Test
    void testErrors() {
        GameState state = new GameState("A", 1);
        state.incrementErrors();
        assertEquals(1, state.getErrorsCount());
    }
}
