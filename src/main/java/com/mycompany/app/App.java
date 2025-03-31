package com.mycompany.app;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.app.gui.TodoFrame;

import javax.swing.*;

/**
 * Todo Application with GUI
 */
public class App {
    public static void main(String[] args) {
        // Set the look and feel to FlatLaf for a modern appearance
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        // Use SwingUtilities to ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            TodoFrame frame = new TodoFrame();
            frame.setVisible(true);
        });
    }
}