package com.mycompany.tp_note.engine;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the state of a Hangman game.
 */
public class GameState {

    public enum Status {
        PLAYING, WON, LOST
    }

    private final String secretWord;
    private final Set<Character> guessedLetters;
    private int errorsCount;
    private final int maxErrors;
    private Status currentStatus;

    /**
     * Creates a new game state.
     * 
     * @param secretWord aka the word to guess.
     * @param maxErrors  aka maximum allowed errors.
     */
    public GameState(String secretWord, int maxErrors) {
        this.secretWord = secretWord.toUpperCase();
        this.guessedLetters = new HashSet<>();
        this.errorsCount = 0;
        this.maxErrors = maxErrors;
        this.currentStatus = Status.PLAYING;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public Set<Character> getGuessedLetters() {
        return new HashSet<>(guessedLetters);
    }

    public int getErrorsCount() {
        return errorsCount;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void addGuessedLetter(char letter) {
        guessedLetters.add(letter);
    }

    public void incrementErrors() {
        errorsCount++;
    }

    public void setStatus(Status status) {
        this.currentStatus = status;
    }

    /**
     * Returns the masked view of the secret word (e.g., "A _ B _").
     * 
     * @return String representation of known letters and underscores.
     */
    public String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                sb.append(c);
            } else {
                sb.append('_');
            }
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * Checks if the word is fully discovered.
     * 
     * @return true if all letters in secretWord have been guessed.
     */
    public boolean isWordGuessed() {
        for (char c : secretWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }
}
