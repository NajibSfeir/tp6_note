package com.mycompany.tp_note.engine;

import com.mycompany.tp_note.engine.GameState.Status;
import com.mycompany.tp_note.UI.UserInterface;

/**
 * Orchestrates the game flow.
 */
public class GameController {

    private final HangmanGame game;
    private final UserInterface ui;

    /**
     * Initializes the controller with the game logic and user interface.
     * 
     * @param game The game engine.
     * @param ui   The user interface.
     */
    public GameController(HangmanGame game, UserInterface ui) {
        this.game = game;
        this.ui = ui;
    }

    /**
     * Starts and manages the game loop.
     */
    public void startGame() {
        while (game.getState().getCurrentStatus() == Status.PLAYING) {
            ui.displayGameState(game.getState());
            char letter = ui.askForLetter();
            if (game.getState().getGuessedLetters().contains(Character.toUpperCase(letter))) {
                ui.displayAlreadyGuessed(letter);
            } else {
                game.guessLetter(letter);
            }
        }
        ui.displayEndGame(game.getState());
    }
}
