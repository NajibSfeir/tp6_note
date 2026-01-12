package com.mycompany.tp_note.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author bachir
 */

/**
 * Manages the loading and selection of words from the dictionary.
 */

public class DictionaryWordProvider implements WordProvider {

    private final List<String> words;
    private final Random random;

    /**
     * Initializes the DictionaryWordProvider by loading words from the valid file.
     * 
     * @throws IOException If the file cannot be read.
     */
    public DictionaryWordProvider() throws IOException {
        this.words = loadWords();
        if (words == null || words.isEmpty()) {
            throw new IOException("Dictionary is empty or could not be loaded.");
        }
        this.random = new Random();
    }

    /**
     * Loads words from the 'dictionary.txt' resource.
     * 
     * @return List of loaded words.
     * @throws IOException If an I/O error occurs.
     */
    private List<String> loadWords() throws IOException {
        List<String> loadedWords = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream("/dictionary.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            if (is == null) {
                throw new IOException("dictionary.txt not found in resources");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                // Basic validation: only letters, non-empty
                if (!word.isEmpty() && word.matches("[a-zA-Z]+")) {
                    loadedWords.add(word.toUpperCase());
                }
            }
        }
        return loadedWords;
    }

    /**
     * Returns a random word from the dictionary.
     * 
     * @return A random word.
     */
    @Override
    public String getWord() {
        if (words.isEmpty()) {
            throw new IllegalStateException("Dictionary is empty");
        }
        return words.get(random.nextInt(words.size()));
    }

    /**
     * Returns the size of the dictionary.
     * 
     * @return Number of words loaded.
     */
    public int getSize() {
        return words.size();
    }
}
