package com.mycompany.tp_note.data;

import java.io.IOException;
import org.junit.jupiter.api.Test;
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
        assertFalse(word.isEmpty());
        // Verify upper case as per implementation
        assertEquals(word.toUpperCase(), word);
    }
}
