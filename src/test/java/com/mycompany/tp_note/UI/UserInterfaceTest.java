package com.mycompany.tp_note.UI;

import com.mycompany.tp_note.engine.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserInterfaceTest {

    private static class DummyUserInterface implements UserInterface {

        @Override
        public void displayGameState(GameState state) {
            // no-op
        }

        @Override
        public char askForLetter() {
            return 'A';
        }

        @Override
        public void displayEndGame(GameState state) {
            // no-op
        }

        @Override
        public String askForSecretWord() {
            return "TEST";
        }

        @Override
        public void displayAlreadyGuessed(char letter) {
            // no-op
        }
    }

    @Test
    void testDisplayGameStateDoesNotThrow() {
        UserInterface ui = new DummyUserInterface();
        assertDoesNotThrow(() -> ui.displayGameState(null));
    }

    @Test
    void testAskForLetterReturnsLetter() {
        UserInterface ui = new DummyUserInterface();
        char letter = ui.askForLetter();
        assertTrue(Character.isLetter(letter));
    }

    @Test
    void testDisplayEndGameDoesNotThrow() {
        UserInterface ui = new DummyUserInterface();
        assertDoesNotThrow(() -> ui.displayEndGame(null));
    }

    @Test
    void testAskForSecretWordReturnsString() {
        UserInterface ui = new DummyUserInterface();
        String word = ui.askForSecretWord();
        assertNotNull(word);
        assertFalse(word.isEmpty());
    }

    @Test
    void testDisplayAlreadyGuessedDoesNotThrow() {
        UserInterface ui = new DummyUserInterface();
        assertDoesNotThrow(() -> ui.displayAlreadyGuessed('A'));
    }
}
