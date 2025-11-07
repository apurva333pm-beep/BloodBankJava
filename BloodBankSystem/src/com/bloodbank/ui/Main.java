package com.bloodbank.ui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager; // <-- Add this import

public class Main {
    public static void main(String[] args) {
        
        // --- ADD THIS ENTIRE BLOCK ---
        try {
            // This line sets the modern "Nimbus" theme!
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // --- END OF BLOCK ---

        SwingUtilities.invokeLater(() -> {
            MainFrame f = new MainFrame();
            f.setVisible(true);
        });
    }
}