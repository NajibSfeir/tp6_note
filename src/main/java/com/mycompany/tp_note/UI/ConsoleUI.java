package com.mycompany.tp_note.UI;

import com.mycompany.tp_note.engine.GameState;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Console-based user interface for the Hangman game.
 */
public class ConsoleUI implements UserInterface {

    private final Scanner scanner;

    /**
     * Creates a new console user interface.
     * Initializes the input scanner.
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    /*
     * The main game loop is managed by the GameController.
     * This class only handles interactions with the user.
     */

    /**
     * The input is validated to ensure that exactly one alphabetic
     * character is provided. The method blocks until a valid input
     * is entered.
     */
    @Override
    public char askForLetter() {
        System.out.print("Proposez une lettre : ");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                return input.charAt(0);
            }
            System.out.print("Entrée invalide. Veuillez entrer une seule lettre : ");
        }
    }

    /**
     * This includes the visual representation of the hangman,
     * the masked word, the number of errors, and the list of
     * letters already proposed by the player.
     */
    @Override
    public void displayGameState(GameState state) {
        System.out.println("\n------------------------------------------------");
        displayHangman(state.getErrorsCount());
        System.out.println("Mot : " + state.getMaskedWord());
        System.out.println("Erreurs : " + state.getErrorsCount() + "/" + state.getMaxErrors());

        String guessed = state.getGuessedLetters().stream()
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println("Lettres proposées : " + guessed);
        System.out.println("------------------------------------------------\n");
    }

    /**
     * Displays the current state of the game.
     * This includes the visual representation of the hangman,
     * the masked word, the number of errors, and the list of
     * letters already proposed by the player.
     */
    @Override
    public void displayEndGame(GameState state) {
        displayHangman(state.getErrorsCount());
        if (state.getCurrentStatus() == GameState.Status.WON) {
            System.out.println("FÉLICITATIONS ! Vous avez trouvé le mot : " + state.getSecretWord());
        } else {
            System.out.println("PERDU ! Le mot était : " + state.getSecretWord());
        }
    }

    /**
     * Prompts the first player to enter the secret word in two-player mode.
     * The console is cleared afterward in order to hide the word from
     * the second player.
     */
    @Override
    public String askForSecretWord() {
        System.out.print("Joueur 1, entrez le mot secret : ");
        String word = scanner.nextLine().trim();
        for (int i = 0; i < 50; i++)
            System.out.println();
        return word;
    }

    @Override
    public void displayAlreadyGuessed(char letter) {
        System.out.println("(!) La lettre '" + letter + "' a déjà été proposée. Essayez une autre.");
    }

    /**
     * Displays the hangman ASCII representation corresponding
     * to the current number of errors.
     */
    private void displayHangman(int errors) {
        switch (errors) {
            case 0:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;
            case 1:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;
            case 2:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("     |");
                System.out.println("=======");
                break;
            case 3:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println(" |   |");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("=======");
                break;
            case 4:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|   |");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("=======");
                break;
            case 5:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|\\  |");
                System.out.println(" |   |");
                System.out.println("     |");
                System.out.println("=======");
                break;
            case 6:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|\\  |");
                System.out.println(" |   |");
                System.out.println("/    |");
                System.out.println("=======");
                break;
            case 7:
            default:
                System.out.println(" +---+");
                System.out.println(" |   |");
                System.out.println(" O   |");
                System.out.println("/|\\  |");
                System.out.println(" |   |");
                System.out.println("/ \\  |");
                System.out.println("=======");
                break;
        }

    }
}
