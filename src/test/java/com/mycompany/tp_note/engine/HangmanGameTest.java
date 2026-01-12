package com.mycompany.tp_note.engine;

import com.mycompany.tp_note.engine.GameState.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HangmanGameTest {

    @Test
    void testInitialization() {
        HangmanGame game = new HangmanGame("TEST", 5);
        assertEquals("TEST", game.getState().getSecretWord());
        assertEquals(5, game.getState().getMaxErrors());
        assertEquals(Status.PLAYING, game.getState().getCurrentStatus());
        assertEquals("_ _ _ _", game.getState().getMaskedWord());
    }

    @Test
    void testCorrectGuess() {
        HangmanGame game = new HangmanGame("JAVA", 5);
        game.guessLetter('a');
        assertEquals("_ A _ A", game.getState().getMaskedWord());
        assertEquals(0, game.getState().getErrorsCount());
    }

    @Test
    void testIncorrectGuess() {
        HangmanGame game = new HangmanGame("JAVA", 5);
        game.guessLetter('z');
        assertEquals(1, game.getState().getErrorsCount());
        assertEquals("_ _ _ _", game.getState().getMaskedWord());
    }

    @Test
    void testCaseInsensitivity() {
        HangmanGame game = new HangmanGame("JAVA", 5);
        game.guessLetter('j');
        assertTrue(game.getState().getGuessedLetters().contains('J'));
        assertEquals("J _ _ _", game.getState().getMaskedWord());
    }

    @Test
    void testWinningGame() {
        HangmanGame game = new HangmanGame("NON", 5);
        game.guessLetter('n');
        game.guessLetter('o');
        assertEquals(Status.WON, game.getState().getCurrentStatus());
    }

    @Test
    void testLosingGame() {
        HangmanGame game = new HangmanGame("ABC", 1);
        game.guessLetter('z');
        assertEquals(Status.LOST, game.getState().getCurrentStatus());
    }

    @Test
    void testRepeatedLetterDoesNotCountAsError() {
        HangmanGame game = new HangmanGame("ABC", 5);
        game.guessLetter('z'); // 1 error
        game.guessLetter('z'); // Should still be 1 error
        assertEquals(1, game.getState().getErrorsCount());
    }

    @Test
    void testInvalidCharactersIgnored() {
        HangmanGame game = new HangmanGame("ABC", 5);
        game.guessLetter('1');
        assertEquals(0, game.getState().getErrorsCount());
        assertFalse(game.getState().getGuessedLetters().contains('1'));
    }

    @Test
    void testConstructorInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new HangmanGame(null, 5));
        assertThrows(IllegalArgumentException.class, () -> new HangmanGame("", 5));
        assertThrows(IllegalArgumentException.class, () -> new HangmanGame("   ", 5));
        assertThrows(IllegalArgumentException.class, () -> new HangmanGame("ABC", 0));
        assertThrows(IllegalArgumentException.class, () -> new HangmanGame("ABC", -1));
    }

    @Test
    void testGuessLetterWhenGameNotPlaying() {
        HangmanGame game = new HangmanGame("A", 1);
        game.guessLetter('A'); // Won
        assertEquals(Status.WON, game.getState().getCurrentStatus());

        // Try to guess again
        game.guessLetter('B');
        // Should not change anything
        assertEquals(Status.WON, game.getState().getCurrentStatus());
        assertEquals(1, game.getState().getGuessedLetters().size());
    }
}
