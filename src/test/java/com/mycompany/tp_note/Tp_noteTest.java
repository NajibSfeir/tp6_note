package com.mycompany.tp_note;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Tp_noteTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;

        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(testIn);
    }

    @Test
    void testMode1_EasyDifficulty_LossLoop() {
        // Inputs:
        // "1" -> Mode 1
        // "7" -> Difficulty (Max Errors)
        // "A"..."Z" -> Guesses until loss
        StringBuilder inputs = new StringBuilder();
        inputs.append("1\n"); // Mode 1
        inputs.append("7\n"); // Errors
        // Guess A-Z. The random word is likely not "ABCDEFGHIJKLMNOPQRSTUVWXYZ", so
        // eventually it loses.
        // We just need to exhaust the 7 errors.
        for (char c = 'A'; c <= 'Z'; c++) {
            inputs.append(c).append("\n");
        }

        provideInput(inputs.toString());

        Tp_note.main(new String[] {});

        String out = outContent.toString();
        // Check for basic game flow markers
        assertTrue(out.contains("Bienvenue au Jeu du Pendu"), "Should show welcome message");
        assertTrue(out.contains("Nombre d'erreurs max (6 ou 7)"), "Should ask for difficulty");
        assertTrue(out.contains("PERDU") || out.contains("FÉLICITATIONS"), "Should finish game");
    }

    @Test
    void testMode2_Win() {
        // Inputs:
        // "2" -> Mode 2
        // "TEST" -> Secret Word
        // "6" -> Difficulty
        // "T", "E", "S" -> Winning guesses
        String inputs = "2\n" +
                "TEST\n" +
                "6\n" +
                "T\nE\nS\n";

        provideInput(inputs);

        Tp_note.main(new String[] {});

        String out = outContent.toString();
        assertTrue(out.contains("Mode 2 Joueurs"), "Option 2 indicates Mode 2"); // Or just inferred from flow
        assertTrue(out.contains("Joueur 1, entrez le mot secret"), "Should ask for secret word");
        assertTrue(out.contains("FÉLICITATIONS"), "Should win");
    }

    @Test
    void testInvalidInputs() {
        // Inputs:
        // "NotANumber" -> Scanner exception handled, defaults to Mode 0 or similar but
        // logic handles it
        // Actually Tp_note: if (scanner.hasNextInt()) ... catch Exception.
        // If not int, mode stays 0.
        // if mode != 2 -> Default to Mode 1 (Dictionary).

        // "1" (Implicitly selected by fallback if mode=0 from invalid input? No, if
        // invalid, mode=0. logic says if mode==2... else ... dictionary. So mode 0 ->
        // dictionary -> valid path.)

        // Wait, if I pass "NotNum", scanner.hasNextInt() is false?
        // Code:
        // if (scanner.hasNextInt()) { mode = scanner.nextInt(); ... }
        // catch (Exception) { // nothing }
        //
        // So valid non-int input makes mode=0.
        // Then block "if (mode == 2) ... else { ... }" executes else block (Mode 1).

        // Then "InvalidDifficulty" for max errors -> defaults to 7.
        // Then guesses.

        StringBuilder inputs = new StringBuilder();
        inputs.append("NotAMode\n");
        inputs.append("NotADifficulty\n");
        for (char c = 'A'; c <= 'Z'; c++) {
            inputs.append(c).append("\n");
        }

        provideInput(inputs.toString());

        Tp_note.main(new String[] {});

        String out = outContent.toString();
        assertTrue(out.contains("Valeur invalide") || out.contains("Entrée invalide"),
                "Should warn about invalid difficulty or handle it silently");
    }
}
