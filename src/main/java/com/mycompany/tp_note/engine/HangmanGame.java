package com.mycompany.tp_note.engine;

import com.mycompany.tp_note.engine.GameState.Status;

/**
 * Core game logic for Hangman.
 */
public class HangmanGame {

    private final GameState state;

    /**
     * Initializes a new game.
     * 
     * @param secretWord aka the word to be guessed.
     * @param maxErrors  aka the maximum number of allowed errors.
     */
    public HangmanGame(String secretWord, int maxErrors) {
        if (secretWord == null || secretWord.trim().isEmpty()) {
            throw new IllegalArgumentException("Secret word cannot be null or empty.");
        }
        if (maxErrors < 1) {
            throw new IllegalArgumentException("Max errors must be at least 1.");
        }
        this.state = new GameState(secretWord, maxErrors);
    }

    /**
     * Processes a guessed letter.
     * 
     * @param letter aka the letter proposed by the player.
     */
    public void guessLetter(char letter) {
        if (state.getCurrentStatus() != Status.PLAYING) {
            return;
        }

        // keep alphabetic & uppercase)
        if (!Character.isLetter(letter)) {
            return;
        }

        char normalizedLetter = Character.toUpperCase(letter);

        // if already guessed --> do nothing
        if (state.getGuessedLetters().contains(normalizedLetter)) {
            return;
        }

        state.addGuessedLetter(normalizedLetter);

        // check if letter is correctt
        if (state.getSecretWord().indexOf(normalizedLetter) < 0) {
            state.incrementErrors();
        }

        updateGameStatus();
    }

    private void updateGameStatus() {
        if (state.isWordGuessed()) {
            state.setStatus(Status.WON);
        } else if (state.getErrorsCount() >= state.getMaxErrors()) {
            state.setStatus(Status.LOST);
        }
    }

    public GameState getState() {
        return state;
    }
}
