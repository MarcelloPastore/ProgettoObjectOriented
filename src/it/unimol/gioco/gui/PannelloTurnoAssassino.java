package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.player.Assassino;
import it.unimol.gioco.app.exceptions.MosseMassimeRaggiunte;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PannelloTurnoAssassino extends JPanel {

    private final Gioco gioco;
    private final JPanel posizioniPanel;
    private final JLabel infoLabel;
    private final ButtonGroup posizioniGroup;
    private final JButton confermaButton;
    private FinestraPrincipale mainFrame;

    public PannelloTurnoAssassino(Gioco gioco, FinestraPrincipale mainFrame) {
        this.gioco = gioco;
        this.mainFrame = mainFrame;
        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        setBackground(MyColors.BACKGROUND_COLOR);

        // Panel sinistro per i controlli
        JPanel controlPanel = new JPanel(null);
        controlPanel.setBounds(50, 50, 400, 668);
        controlPanel.setBackground(MyColors.BACKGROUND_COLOR);

        // Titolo del turno
        JLabel titleLabel = new JLabel("Turno Assassino");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(MyColors.TEXT_COLOR);
        titleLabel.setBounds(0, 20, 400, 40);
        controlPanel.add(titleLabel);

        // Info sul giocatore e mosse rimanenti
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(MyColors.TEXT_COLOR);
        infoLabel.setBounds(20, 80, 360, 80);
        controlPanel.add(infoLabel);

        // Label per le mosse disponibili
        JLabel moveLabel = new JLabel("Mosse disponibili:");
        moveLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        moveLabel.setForeground(MyColors.TEXT_COLOR);
        moveLabel.setBounds(20, 180, 360, 30);
        controlPanel.add(moveLabel);

        // Panel per le posizioni disponibili
        posizioniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        posizioniPanel.setBackground(MyColors.BACKGROUND_COLOR);
        posizioniPanel.setBounds(20, 220, 360, 150);
        controlPanel.add(posizioniPanel);

        posizioniGroup = new ButtonGroup();

        // Bottone di conferma
        confermaButton = new JButton("Conferma Mossa");
        confermaButton.setBounds(20, 400, 200, 40);
        styleButton(confermaButton);
        controlPanel.add(confermaButton);

        confermaButton.addActionListener(e -> eseguiMossa());

        // Panel destro per il tabellone di gioco
        JPanel gamePanel = new JPanel() {
            private final Image bgImage = new ImageIcon("resources/BoardIMG.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        gamePanel.setBounds(500, 50, 650, 650);
        gamePanel.setBackground(MyColors.BUTTON_COLOR);

        // Aggiunta dei pannelli principali
        add(controlPanel);
        add(gamePanel);

        updateInfoLabel();
        updateMosseDisponibili();
    }

    private void updateInfoLabel() {
        Assassino assassino = getAssassinoCorrente();
        if (assassino != null) {
            String nomeAssassino = assassino.getNome();
            infoLabel.setText("<html>Turno Assassino: " + nomeAssassino +
                    "<br>Mosse rimanenti: " + gioco.getMosseRimanentiAssassino() +
                    "<br>Obiettivi rimasti: " + gioco.getListaObiettivi().size() + "</html>");
        }
    }

    private void updateMosseDisponibili() {
        posizioniPanel.removeAll();
        posizioniGroup.clearSelection();

        Assassino assassino = getAssassinoCorrente();
        if (assassino != null) {
            List<String> mosseDisponibili = gioco.getCelleVicineVisibiliString(assassino);
            for (String mossa : mosseDisponibili) {
                JToggleButton btn = createMoveButton(mossa);
                posizioniGroup.add(btn);
                posizioniPanel.add(btn);
            }
        }

        posizioniPanel.revalidate();
        posizioniPanel.repaint();
    }

    private JToggleButton createMoveButton(String text) {
        JToggleButton btn = new JToggleButton(text);
        btn.setPreferredSize(new Dimension(80, 30));
        btn.setMinimumSize(new Dimension(80, 30));
        btn.setMaximumSize(new Dimension(80, 30));
        btn.setActionCommand(text);
        styleButton(btn);
        return btn;
    }

    private void styleButton(AbstractButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(MyColors.TEXT_COLOR);
        button.setBackground(MyColors.BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(MyColors.BUTTON_HOVER);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(MyColors.BUTTON_COLOR);
            }
        });
    }

    private Assassino getAssassinoCorrente() {
        return (Assassino) gioco.getListaGiocatori().stream()
                .filter(g -> g instanceof Assassino)
                .findFirst()
                .orElse(null);
    }

    private void eseguiMossa() {
        ButtonModel selectedButton = posizioniGroup.getSelection();
        if (selectedButton == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una posizione per muoverti",
                    "Nessuna posizione selezionata",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int nuovaPosizione = Integer.parseInt(selectedButton.getActionCommand());
            Assassino assassino = getAssassinoCorrente();
            if (assassino != null) {
                gioco.muoviAssassino(assassino, nuovaPosizione);

                // Aggiorna l'interfaccia
                updateInfoLabel();
                updateMosseDisponibili();

                // Controlla se la lista degli obiettivi è vuota
                if (gioco.getListaObiettivi().isEmpty()) {
                    // Passa al pannello vittoria
                    this.mainFrame.getContentPane().removeAll();
                    this.mainFrame.getContentPane().add(new PannelloVittoriaAssassino(gioco, mainFrame));
                    this.mainFrame.revalidate();
                    this.mainFrame.repaint();
                } else {
                    // Passa al turno successivo
                    this.mainFrame.getContentPane().removeAll();
                    this.mainFrame.getContentPane().add(new PannelloDiGioco(gioco, mainFrame));
                    this.mainFrame.revalidate();
                    this.mainFrame.repaint();
                }
            }
        } catch (MosseMassimeRaggiunte e) {
            JOptionPane.showMessageDialog(this,
                    "Hai raggiunto il numero massimo di mosse!",
                    "Mosse esaurite",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Errore durante il movimento: " + e.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}