package com.mycompany.tp_note.data;

import com.mycompany.tp_note.UI.UserInterface;
import java.io.IOException;

/**
 * Provides a word by asking the user via the UI.
 */
public class ManualWordProvider implements WordProvider {

    private final UserInterface ui;

    public ManualWordProvider(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public String getWord() throws IOException {
        String word = ui.askForSecretWord();
        if (word == null)
            return "";
        return word;
    }
}
