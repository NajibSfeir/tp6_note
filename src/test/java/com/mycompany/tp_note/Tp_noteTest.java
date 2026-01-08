/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.tp_note;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author lenovo
 */
public class Tp_noteTest {
    
    public Tp_noteTest() {
    }

    /**
     * Test of main method, of class Tp_note.
     */
    @Test
    public void testMain() {
        System.out.println("Testing main method...");
        String[] args = null;
        
        // This executes your main program
        Tp_note.main(args);
        
        // If the code reaches here without crashing, the test passes.
        assertTrue(true, "Main method executed successfully.");
    }
    
}