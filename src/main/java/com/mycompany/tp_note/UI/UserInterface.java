package com.mycompany.tp_note.UI;

import com.mycompany.tp_note.engine.GameState;
/**
 * Interface defining user interaction contracts.
 */
public interface UserInterface {
    /**
     * Display the current state of the game.
     * 
     * @param state The game state.
     */
    void displayGameState(GameState state);

    /**
     * Ask the user for a letter.
     * 
     * @return The letter proposed.
     */
    char askForLetter();

    /**
     * Display the end game message.
     * 
     * @param state The final game state.
     */
    void displayEndGame(GameState state);

    /**
     * Ask for the secret word (for 2-player mode).
     * 
     * @return The secret word.
     */
    String askForSecretWord();
}
