package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.player.Assassino;
import it.unimol.gioco.app.player.Investigatore;
import it.unimol.gioco.app.exceptions.MosseMassimeRaggiunte;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PannelloDiGioco extends JPanel {

    private final Gioco gioco;
    private final PannelloTurnoAssassino pannelloAssassino;
    private final PannelloTurnoInvestigatore pannelloInvestigatore;
    private final FinestraPrincipale mainFrame;

    public PannelloDiGioco(Gioco gioco, FinestraPrincipale mainFrame) {
        this.gioco = gioco;
        this.mainFrame = mainFrame;
        
        setLayout(null);
        setPreferredSize(new Dimension(1224, 768));
        setBackground(MyColors.BACKGROUND_COLOR);

        // Aggiungi label per il giocatore corrente
        JLabel currentPlayerLabel = new JLabel();
        currentPlayerLabel.setFont(new Font("Georgia", Font.BOLD, 18));
        currentPlayerLabel.setForeground(MyColors.TITLE_COLOR);
        currentPlayerLabel.setBounds(50, 10, 400, 30);
        add(currentPlayerLabel);

        // Inizializza i pannelli dei turni
        pannelloAssassino = new PannelloTurnoAssassino(gioco, mainFrame);
        pannelloInvestigatore = new PannelloTurnoInvestigatore(gioco, mainFrame);

        // Aggiungi i pannelli al CardLayout
        add(pannelloAssassino, "assassino");
        add(pannelloInvestigatore, "investigatore");

        // Inizializza con il primo turno
        updateTurnPanel();
    }

    public void updateTurnPanel() {
        int turnoCorrente = gioco.getTurnoCorrente();


        this.mainFrame.getContentPane().removeAll();

        if (turnoCorrente == 0) {
            this.mainFrame.getContentPane().add(new PannelloTurnoAssassino(gioco, mainFrame));
            System.out.println("turno: " + turnoCorrente);
            nextTurn();
        } else {
            this.mainFrame.getContentPane().add(new PannelloTurnoInvestigatore(gioco, mainFrame));
            nextTurn();
        }
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    public void nextTurn() {gioco.nextTurn();}
}