package com.mycompany.tp_note.data;

import com.mycompany.tp_note.UI.UserInterface;
import com.mycompany.tp_note.engine.GameState;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManualWordProviderTest {

    // Simple stub for UserInterface to avoid complex mocking framework setup if not
    // needed
    private static class StubUserInterface implements UserInterface {
        private String secretWordToReturn;

        public void setSecretWord(String word) {
            this.secretWordToReturn = word;
        }

        @Override
        public String askForSecretWord() {
            return secretWordToReturn;
        }

        @Override
        public void displayGameState(GameState state) {
        }

        @Override
        public char askForLetter() {
            return ' ';
        }

        @Override
        public void displayEndGame(GameState state) {
        }

        @Override
        public void displayAlreadyGuessed(char letter) {
        }
    }

    @Test
    public void testGetWordReturnsInputFromUI() throws IOException {
        StubUserInterface stubUI = new StubUserInterface();
        stubUI.setSecretWord("HELLO");
        ManualWordProvider provider = new ManualWordProvider(stubUI);

        assertEquals("HELLO", provider.getWord());
    }

    @Test
    public void testGetWordHandlesNull() throws IOException {
        StubUserInterface stubUI = new StubUserInterface();
        stubUI.setSecretWord(null);
        ManualWordProvider provider = new ManualWordProvider(stubUI);

        assertEquals("", provider.getWord());
    }
}
