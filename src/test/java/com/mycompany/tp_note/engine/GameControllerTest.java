package com.mycompany.tp_note.engine;

import com.mycompany.tp_note.UI.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for GameController.
 * We need to mock UserInterface. Since we don't have Mockito in the
 * dependencies (based on pom.xml view earlier),
 * I will create a simple internal implementation for testing.
 */
class GameControllerTest {

    private HangmanGame game;
    private MockUI mockUI;
    private GameController controller;

    @BeforeEach
    void setUp() {
        game = new HangmanGame("TEST", 5);
        mockUI = new MockUI();
        controller = new GameController(game, mockUI);
    }

    @Test
    void testGameFlowWinning() {
        mockUI.setInputs("T", "E", "S", "T"); // correct letters

        controller.startGame();

        assertEquals(GameState.Status.WON, game.getState().getCurrentStatus());
        assertTrue(mockUI.gameEnded);
    }

    @Test
    void testGameFlowLosing() {
        // "TEST" has 4 unique letters. Max errors 5.
        // we need 5 wrong guesses: A, B, C, D, F
        mockUI.setInputs("A", "B", "C", "D", "F", "G"); // G triggers the 6th error/loss if strict

        controller.startGame();

        assertEquals(GameState.Status.LOST, game.getState().getCurrentStatus());
        assertTrue(mockUI.gameEnded);
    }

    @Test
    void testDuplicateGuessWarning() {
        mockUI.setInputs("T", "T", "E", "S"); // T repeated. Note: loop consumes input.
        // First T -> Guess.
        // Second T -> Duplicate warning.
        // E -> Guess.
        // S -> Guess (game won basically if we added last T, but let's just check the
        // warning call)

        // This is a bit tricky with simple loop.
        // 1. T -> Correct
        // 2. T -> Warning
        // 3. E -> Correct
        // 4. S -> Correct -> WON

        controller.startGame();

        assertTrue(mockUI.warningDisplayed);
        assertEquals(GameState.Status.WON, game.getState().getCurrentStatus());
    }

    // simple Mock for UI
    static class MockUI implements UserInterface {
        String[] inputs = {};
        int inputIndex = 0;
        boolean gameEnded = false;
        boolean warningDisplayed = false;

        public void setInputs(String... inputs) {
            this.inputs = inputs;
        }

        @Override
        public void displayGameState(GameState state) {
        }

        @Override
        public char askForLetter() {
            if (inputIndex < inputs.length) {
                return inputs[inputIndex++].charAt(0);
            }
            return ' ';
        }

        @Override
        public void displayEndGame(GameState state) {
            gameEnded = true;
        }

        @Override
        public String askForSecretWord() {
            return "TEST";
        }

        @Override
        public void displayAlreadyGuessed(char letter) {
            warningDisplayed = true;
        }
    }
}
