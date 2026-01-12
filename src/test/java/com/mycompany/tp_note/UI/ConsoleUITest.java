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
        for (int i = 0; i < 7; i++) state.incrementErrors();
        state.setStatus(GameState.Status.LOST);

        ui.displayEndGame(state);

        String o = output();
        assertTrue(o.contains("/ \\  |"), "Final hangman should show both legs at 7 errors");
    }
}
