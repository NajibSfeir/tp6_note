/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tp_note;

/**
 *
 * @author spicesx
 */

import com.mycompany.tp_note.data.DictionaryWordProvider;
import com.mycompany.tp_note.data.ManualWordProvider;
import com.mycompany.tp_note.data.WordProvider;
import com.mycompany.tp_note.engine.GameController;
import com.mycompany.tp_note.engine.HangmanGame;
import com.mycompany.tp_note.UI.ConsoleUI;

import java.io.IOException;
import java.util.Scanner;

public class Tp_note {

    private static final int MAX_ERRORS = 7;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleUI ui = new ConsoleUI();

        System.out.println("Bienvenue au Jeu du Pendu !");
        System.out.println("1. Mode 1 Joueur (Contre l'ordinateur)");
        System.out.println("2. Mode 2 Joueurs (L'un choisit, l'autre devine)");
        System.out.print("Choisissez votre mode : ");

        int mode = 0;
        try {
            if (scanner.hasNextInt()) {
                mode = scanner.nextInt();
                scanner.nextLine();
            }
        } catch (Exception e) {
            // nothingg
        }

        WordProvider wordProvider;

        if (mode == 2) {
            wordProvider = new ManualWordProvider(ui);
        } else {
            // Default to mode 1
            try {
                wordProvider = new DictionaryWordProvider();
            } catch (IOException e) {
                System.err.println("Erreur de chargement du dictionnaire : " + e.getMessage());
                return;
            }
        }

        String secretWord = "";
        try {
            secretWord = wordProvider.getWord();
        } catch (IOException e) {
            System.err.println("Erreur lors de la récupération du mot : " + e.getMessage());
            return;
        }

        // Basic validation in case of empty input
        if (secretWord.isEmpty() || !secretWord.matches("[a-zA-Z]+")) {
            if (mode == 2) {
                System.out.println("Mot invalide. Le jeu va utiliser un mot par défaut: PENDU");
            }
            secretWord = "PENDU";
        }

        HangmanGame game = new HangmanGame(secretWord, MAX_ERRORS);
        GameController controller = new GameController(game, ui);
        controller.startGame();

        scanner.close();
    }
}
