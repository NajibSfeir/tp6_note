package com.mycompany.tp_note.UI;

import com.mycompany.tp_note.engine.GameState;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Console-based user interface for the Hangman game.
 * <p>
 * This class is responsible only for input/output operations.
 * All game logic is handled by the engine layer.
 * </p>
 */
public class ConsoleUI implements UserInterface {

    private static final int CLEAR_LINES = 50;
    private static final String SEPARATOR = "------------------------------------------------";

    /**
     * All hangman ASCII stages indexed by error count.
     * This avoids duplicated string literals and improves maintainability.
     */
    private static final String[] HANGMAN_STAGES = {
            """
                     +---+
                     |   |
                         |
                         |
                         |
                         |
                    =======
                    """,
            """
                     +---+
                     |   |
                     O   |
                         |
                         |
                         |
                    =======
                    """,
            """
                     +---+
                     |   |
                     O   |
                     |   |
                         |
                         |
                    =======
                    """,
            """
                     +---+
                     |   |
                     O   |
                     |   |
                     |   |
                         |
                    =======
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|   |
                     |   |
                         |
                    =======
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                     |   |
                         |
                    =======
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                     |   |
                    /    |
                    =======
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                     |   |
                    / \\  |
                    =======
                    """
    };

    private final Scanner scanner;

    /** Creates a ConsoleUI reading from standard input. */
    public ConsoleUI() {
        this(new Scanner(System.in));
    }

    /**
     * Creates a ConsoleUI using the provided Scanner.
     * 
     * @param scanner the scanner to use for input
     */
    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prompts the user for a single alphabetic letter.
     *
     * @return a valid letter entered by the user
     */
    @Override
    public char askForLetter() {
        System.out.print("Proposez une lettre : ");
        while (true) {
            String input = scanner.nextLine().trim();
            if (isValidLetter(input)) {
                return input.charAt(0);
            }
            System.out.print("Entrée invalide. Veuillez entrer une seule lettre : ");
        }
    }

    /**
     * Displays the current game state.
     *
     * @param state the current game state
     */
    @Override
    public void displayGameState(GameState state) {
        if (state == null) {
            System.out.println("(Erreur) État du jeu indisponible.");
            return;
        }

        System.out.println("\n" + SEPARATOR);
        printHangman(state.getErrorsCount(), state.getMaxErrors());
        System.out.println("Mot : " + state.getMaskedWord());
        System.out.println("Erreurs : "
                + state.getErrorsCount() + "/" + state.getMaxErrors());
        System.out.println("Lettres proposées : "
                + formatGuessedLetters(state));
        System.out.println(SEPARATOR + "\n");
    }

    /**
     * Displays the final game result.
     *
     * @param state the final game state
     */
    @Override
    public void displayEndGame(GameState state) {
        if (state == null) {
            System.out.println("(Erreur) État du jeu indisponible.");
            return;
        }

        printHangman(state.getErrorsCount(), state.getMaxErrors());

        if (state.getCurrentStatus() == GameState.Status.WON) {
            System.out.println(
                    "FÉLICITATIONS ! Vous avez trouvé le mot : "
                            + state.getSecretWord());
        } else {
            System.out.println(
                    "PERDU ! Le mot était : "
                            + state.getSecretWord());
        }
    }

    /**
     * Asks player 1 to enter the secret word in two-player mode.
     *
     * @return the entered secret word
     */
    @Override
    public String askForSecretWord() {
        System.out.print("Joueur 1, entrez le mot secret : ");
        String word = scanner.nextLine().trim();
        clearConsole();
        return word;
    }

    /**
     * Informs the user that a letter has already been proposed.
     *
     * @param letter the repeated letter
     */
    @Override
    public void displayAlreadyGuessed(char letter) {
        System.out.println(
                "(!) La lettre '" + letter + "' a déjà été proposée. Essayez une autre.");
    }

    /* -------------------- Helper methods -------------------- */

    private static boolean isValidLetter(String input) {
        return input.length() == 1 && Character.isLetter(input.charAt(0));
    }

    private static String formatGuessedLetters(GameState state) {
        return state.getGuessedLetters().stream()
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    private static void clearConsole() {
        for (int i = 0; i < CLEAR_LINES; i++) {
            System.out.println();
        }
    }

    private static void printHangman(int errors, int maxErrors) {
        // If maxErrors is 6, we skip index 2 (small body) and jump to 3 (full body)
        // Mapping: 0->0, 1->1, 2->3, 3->4, 4->5, 5->6, 6->7
        int stageIndex;
        if (maxErrors == 6) {
            if (errors == 0)
                stageIndex = 0;
            else if (errors == 1)
                stageIndex = 1;
            else
                stageIndex = errors + 1; // 2->3, 3->4, etc.
        } else {
            // Default 7 errors: direct mapping
            stageIndex = errors;
        }

        // Safety clamp
        stageIndex = Math.min(stageIndex, HANGMAN_STAGES.length - 1);
        System.out.println(HANGMAN_STAGES[stageIndex]);
    }
}
