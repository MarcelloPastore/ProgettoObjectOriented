package it.unimol.gioco.gui;

import javax.swing.*;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.gui.Menu;

import java.awt.*;


public class FinestraPrincipale extends JFrame {

    private Menu menuPanel;

    public FinestraPrincipale(Gioco gioco) {

        super("WhiteHall Mistery");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1224, 768);
        setLocationRelativeTo(null);
        setResizable(false);


        // Crea e aggiungi i pannelli
        menuPanel = new Menu(this, gioco);
        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        setVisible(true);
    }

}


