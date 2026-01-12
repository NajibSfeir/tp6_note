package com.mycompany.tp_note.UI;

import com.mycompany.tp_note.engine.GameState;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleUITest {

    private PrintStream originalOut;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private String output() {
        return out.toString();
    }

    @Test
    void displayGameStatePrintsSeparatorsAndLabels() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("TEST", 7);

        ui.displayGameState(state);

        String o = output();
        assertTrue(o.contains("------------------------------------------------"));
        assertTrue(o.contains("Mot :"));
        assertTrue(o.contains("Erreurs :"));
        assertTrue(o.contains("Lettres proposées :"));
    }

    @Test
    void displayGameStatePrintsErrorCounterFormat() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 7);
        state.incrementErrors(); // errors = 1

        ui.displayGameState(state);

        String o = output();
        assertTrue(o.contains("Erreurs : 1/7"), "Should print errors count as X/maxErrors");
    }

    @Test
    void displayGameStatePrintsHangmanForZeroErrors() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 7); // errors=0

        ui.displayGameState(state);

        String o = output();
        assertTrue(o.contains("+---+"), "Hangman header should be printed");
        assertTrue(o.contains("======="), "Hangman base should be printed");
        assertFalse(o.contains(" O   |"), "No head should appear at 0 errors");
    }

    @Test
    void displayGameStatePrintsHangmanWithHeadAtOneError() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 7);
        state.incrementErrors(); // errors=1

        ui.displayGameState(state);

        String o = output();
        assertTrue(o.contains(" O   |"), "Head should appear at 1 error");
    }

    @Test
    void displayGameStatePrintsGuessedLettersSortedAndCommaSeparated() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 7);

        // Add letters in non-sorted order
        state.addGuessedLetter('Z');
        state.addGuessedLetter('A');
        state.addGuessedLetter('M');

        ui.displayGameState(state);

        String o = output();
        assertTrue(o.contains("Lettres proposées : A, M, Z"),
                "Guessed letters should be sorted and joined with ', '");
    }

    @Test
    void displayEndGameWonPrintsCongratulationsAndSecretWord() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 7);
        state.setStatus(GameState.Status.WON);

        ui.displayEndGame(state);

        String o = output();
        assertTrue(o.contains("FÉLICITATIONS"), "Should print a win message");
        assertTrue(o.contains("JAVA"), "Should print the secret word");
    }

    @Test
    void displayEndGameLostPrintsLostMessageAndSecretWord() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 7);
        state.setStatus(GameState.Status.LOST);

        ui.displayEndGame(state);

        String o = output();
        assertTrue(o.contains("PERDU"), "Should print a lose message");
        assertTrue(o.contains("JAVA"), "Should print the secret word");
    }

    @Test
    void displayEndGamePrintsFinalHangmanAtSevenErrors() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 7);

        // force errorsCount to 7 (depends on your API)
        for (int i = 0; i < 7; i++)
            state.incrementErrors();
        state.setStatus(GameState.Status.LOST);

        ui.displayEndGame(state);

        String o = output();
        assertTrue(o.contains("/ \\  |"), "Final hangman should show both legs at 7 errors");
    }

    @Test
    void displayHangmanForSixErrorsDifficulty() {
        ConsoleUI ui = new ConsoleUI();
        GameState state = new GameState("JAVA", 6); // Hard mode

        // Error 0 -> Stage 0 (Empty)
        ui.displayGameState(state);
        assertTrue(output().contains("     |"), "Stage 0 should be empty body");

        // Error 1 -> Stage 1 (Head)
        state.incrementErrors();
        ui.displayGameState(state);
        assertTrue(output().contains(" O   |"), "Stage 1 should have head");
        // Reset stream
        setUp();

        // Error 2 -> Stage 3 (Head + Body + Leg?) - Actually Stage 3 in array is
        // Body+Leg?
        // Let's check mapping:
        // 0->0, 1->1, 2->3
        // Stage 1: Head
        // Stage 2: Head + Body (skipped)
        // Stage 3: Head + Body + Leg (Wait, let's verify array content)
        // Array[3]:
        // O
        // |
        // |
        // That is Head + Torso.
        // Array[2]:
        // O
        // |

        state.incrementErrors(); // Total 2
        ui.displayGameState(state);
        // Should print Stage 3
        String o = output();
        assertTrue(o.contains(" |   |"), "Stage 3 (2 errors in hard mode) should have full torso/body");
    }
}
