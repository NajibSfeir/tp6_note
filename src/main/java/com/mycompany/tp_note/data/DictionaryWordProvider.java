package com.mycompany.tp_note.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the loading and random selection of words from a dictionary file.
 */
public class DictionaryWordProvider implements WordProvider {

    private final List<String> words;

    /**
     * Initializes the DictionaryWordProvider by loading words from
     * the {@code dictionary.txt} resource.
     *
     * @throws IOException if the dictionary cannot be loaded or is empty
     */
    public DictionaryWordProvider() throws IOException {
        this.words = loadWords();
        if (words.isEmpty()) {
            throw new IOException("Dictionary is empty or could not be loaded.");
        }
    }

    /**
     * Loads words from the {@code dictionary.txt} resource file.
     * Only alphabetic, non-empty words are kept.
     *
     * @return a list of valid words
     * @throws IOException if the resource is missing or unreadable
     */
    private List<String> loadWords() throws IOException {
        List<String> loadedWords = new ArrayList<>();

        InputStream is = getClass().getResourceAsStream("/dictionary.txt");
        if (is == null) {
            throw new IOException("dictionary.txt not found in resources");
        }

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
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
     * @return a randomly selected word
     */
    @Override
    public String getWord() {
        if (words.isEmpty()) {
            throw new IllegalStateException("Dictionary is empty");
        }

        int index = ThreadLocalRandom.current().nextInt(words.size());
        return words.get(index);
    }

    /**
     * Returns the number of loaded words.
     *
     * @return dictionary size
     */
    public int getSize() {
        return words.size();
    }
}
