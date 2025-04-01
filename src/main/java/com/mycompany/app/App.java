package com.mycompany.app;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.app.gui.TodoFrame;

import javax.swing.*;

/**
 * Todo Application with GUI
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Starting Application...");
    
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    
        SwingUtilities.invokeLater(() -> {
            System.out.println("Launching GUI...");
            TodoFrame frame = new TodoFrame();
            frame.setVisible(true);
            System.out.println("Hello, World!!!!");
        });
    }
    
}

// mvn exec:java -Dexec.mainClass="com.mycompany.app.App"