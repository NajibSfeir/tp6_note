package com.mycompany.tp_note.data;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryWordProviderTest {

    @Test
    public void testInitializationLoadsWords() throws IOException {
        DictionaryWordProvider provider = new DictionaryWordProvider();
        assertTrue(provider.getSize() > 0, "Dictionary should contain words");
    }

    @Test
    public void testGetWordReturnsValidWord() throws IOException {
        DictionaryWordProvider provider = new DictionaryWordProvider();
        String word = provider.getWord();
        assertNotNull(word);
        assertFalse(word.isEmpty(), "Returned word should not be empty");
        assertEquals(word.toUpperCase(), word, "Returned word should be uppercase");
    }

    @Test
    public void testMultipleGetWordCalls() throws IOException {
        DictionaryWordProvider provider = new DictionaryWordProvider();
        for (int i = 0; i < 10; i++) {
            String w = provider.getWord();
            assertNotNull(w);
            assertFalse(w.isEmpty());
        }
    }
}
