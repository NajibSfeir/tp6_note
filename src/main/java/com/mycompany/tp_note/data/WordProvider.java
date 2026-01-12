package com.mycompany.tp_note.data;

import java.io.IOException;

/**
 *
 * @author bachir
 */

/**
 * Strategy interface for providing a secret word for the game.
 */
public interface WordProvider {
    /**
     * Retrieves a word for the game.
     * 
     * @return The secret word.
     * @throws IOException If an error occurs while retrieving the word.
     */
    String getWord() throws IOException;
}
