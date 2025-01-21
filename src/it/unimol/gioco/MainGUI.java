package it.unimol.gioco;

import it.unimol.gioco.gui.GiocoGUI;

import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GiocoGUI giocoGUI = new GiocoGUI();
            giocoGUI.start();
        });
    }
}
