package com.mycompany.tp_note.UI;

import com.mycompany.tp_note.engine.GameState;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Console-based user interface for the Hangman game.
 * <p>
 * Responsible only for user I/O. No game rules are implemented here.
 * </p>
 */
public class ConsoleUI implements UserInterface {

    private static final int CLEAR_LINES = 50;
    private static final String SEPARATOR = "------------------------------------------------";

    /**
     * ASCII hangman stages indexed by number of errors.
     * The last stage is reused for any errors beyond the supported range.
     */
    private static final String[] HANGMAN_STAGES = {
            String.join("\n",
                    " +---+",
                    " |   |",
                    "     |",
                    "     |",
                    "     |",
                    "     |",
                    "======="
            ),
            String.join("\n",
                    " +---+",
                    " |   |",
                    " O   |",
                    "     |",
                    "     |",
                    "     |",
                    "======="
            ),
            String.join("\n",
                    " +---+",
                    " |   |",
                    " O   |",
                    " |   |",
                    "     |",
                    "     |",
                    "======="
            ),
            String.join("\n",
                    " +---+",
                    " |   |",
                    " O   |",
                    " |   |",
                    " |   |",
                    "     |",
                    "======="
            ),
            String.join("\n",
                    " +---+",
                    " |   |",
                    " O   |",
                    "/|   |",
                    " |   |",
                    "     |",
                    "======="
            ),
            String.join("\n",
                    " +---+",
                    " |   |",
                    " O   |",
                    "/|\\  |",
                    " |   |",
                    "     |",
                    "======="
            ),
            String.join("\n",
                    " +---+",
                    " |   |",
                    " O   |",
                    "/|\\  |",
                    " |   |",
                    "/    |",
                    "======="
            ),
            String.join("\n",
                    " +---+",
                    " |   |",
                    " O   |",
                    "/|\\  |",
                    " |   |",
                    "/ \\  |",
                    "======="
            )
    };

    private final Scanner scanner;

    /** Creates a ConsoleUI reading from standard input. */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user for a single alphabetic letter.
     * The method blocks until a valid input is provided.
     *
     * @return the proposed letter
     */
    @Override
    public char askForLetter() {
        System.out.print("Proposez une lettre : ");
        while (true) {
            String input = scanner.nextLine().trim();
            if (isSingleLetter(input)) {
                return input.charAt(0);
            }
            System.out.print("Entrée invalide. Veuillez entrer une seule lettre : ");
        }
    }

    /**
     * Displays the current game state: hangman drawing, masked word,
     * error counter, and proposed letters.
     *
     * @param state current game state (must not be null)
     */
    @Override
    public void displayGameState(GameState state) {
        if (state == null) {
            System.out.println("(Erreur) État du jeu indisponible.");
            return;
        }

        System.out.println("\n" + SEPARATOR);
        printHangman(state.getErrorsCount());
        System.out.println("Mot : " + state.getMaskedWord());
        System.out.println("Erreurs : " + state.getErrorsCount() + "/" + state.getMaxErrors());
        System.out.println("Lettres proposées : " + formatGuessedLetters(state));
        System.out.println(SEPARATOR + "\n");
    }

    /**
     * Displays the end-of-game message and the final hangman drawing.
     *
     * @param state final game state (must not be null)
     */
    @Override
    public void displayEndGame(GameState state) {
        if (state == null) {
            System.out.println("(Erreur) État du jeu indisponible.");
            return;
        }

        printHangman(state.getErrorsCount());
        if (state.getCurrentStatus() == GameState.Status.WON) {
            System.out.println("FÉLICITATIONS ! Vous avez trouvé le mot : " + state.getSecretWord());
        } else {
            System.out.println("PERDU ! Le mot était : " + state.getSecretWord());
        }
    }

    /**
     * Asks player 1 for the secret word (2-player mode) and clears the screen afterward.
     *
     * @return the secret word entered by player 1
     */
    @Override
    public String askForSecretWord() {
        System.out.print("Joueur 1, entrez le mot secret : ");
        String word = scanner.nextLine().trim();
        clearConsole();
        return word;
    }

    @Override
    public void displayAlreadyGuessed(char letter) {
        System.out.println("(!) La lettre '" + letter + "' a déjà été proposée. Essayez une autre.");
    }

    // -------------------- Private helpers --------------------

    private static boolean isSingleLetter(String input) {
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

    private static void printHangman(int errors) {
        int index = clamp(errors, 0, HANGMAN_STAGES.length - 1);
        System.out.println(HANGMAN_STAGES[index]);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}