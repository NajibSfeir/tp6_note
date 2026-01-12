package com.mycompany.tp_note;

import com.mycompany.tp_note.UI.UserInterface;
import com.mycompany.tp_note.data.DictionaryWordProvider;
import com.mycompany.tp_note.engine.GameController;
import com.mycompany.tp_note.engine.GameState;
import com.mycompany.tp_note.engine.HangmanGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration-style tests for the application wiring (without running main()).
 * These tests are CI-safe: they do not read from System.in and do not block.
 */
public class Tp_noteTest {

    /**
     * A scripted UI that returns a sequence of letters and records calls.
     * This avoids any dependency on the real Console UI.
     */
    private static class ScriptedUI implements UserInterface {

        private final Queue<Character> letters = new ArrayDeque<>();
        int displayStateCalls = 0;
        int endCalls = 0;

        ScriptedUI(String lettersScript) {
            for (char c : lettersScript.toCharArray()) {
                letters.add(c);
            }
        }

        @Override
        public void displayGameState(GameState state) {
            displayStateCalls++;
        }

        @Override
        public char askForLetter() {
            // If script runs out, provide a wrong letter to finish the game
            return letters.isEmpty() ? 'Z' : letters.remove();
        }

        @Override
        public void displayEndGame(GameState state) {
            endCalls++;
        }

        @Override
        public String askForSecretWord() {
            return "IGNORED";
        }

        // If your UserInterface has this method, keep it. If not, remove it.
        @Override
        public void displayAlreadyGuessed(char letter) {
            // no-op
        }
    }

    @Test
    void dictionaryProviderLoadsAtLeastOneWord() throws IOException {
        DictionaryWordProvider provider = new DictionaryWordProvider();
        assertTrue(provider.getSize() > 0, "Dictionary should load at least one valid word");
        assertNotNull(provider.getWord(), "Provider should return a non-null word");
        assertFalse(provider.getWord().isEmpty(), "Provider should not return an empty word");
    }

    @Test
    void controllerCanRunGameToCompletionWithoutConsoleInput() {
        // simple word so we can end quickly
        HangmanGame game = new HangmanGame("AB", 7);

        // script guesses A then B -> WIN
        ScriptedUI ui = new ScriptedUI("AB");

        GameController controller = new GameController(game, ui);
        controller.startGame();

        assertEquals(GameState.Status.WON, game.getState().getCurrentStatus(),
                "Game should end in WON status with correct guesses");
        assertEquals(1, ui.endCalls, "End game should be displayed exactly once");
        assertTrue(ui.displayStateCalls >= 1, "Game state should be displayed at least once");
    }
}
