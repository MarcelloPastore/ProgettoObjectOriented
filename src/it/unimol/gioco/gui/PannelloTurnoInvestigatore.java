package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.board.CellaInvestigatore;
import it.unimol.gioco.app.player.Giocatore;
import it.unimol.gioco.app.player.Investigatore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PannelloTurnoInvestigatore extends JPanel {
    private final Gioco gioco;
    private final JPanel posizioniInvestigatorePanel;
    private final JPanel posizioniAssassinoPanel;
    private final JLabel infoLabel;
    private final ButtonGroup posizioniInvestigatoreGroup;
    private final ButtonGroup posizioniAssassinoGroup;
    private final JButton confermaButton;
    private final JButton verificaIndizioButton;
    private final JButton arrestaButton;
    private final FinestraPrincipale mainFrame;

    public PannelloTurnoInvestigatore(Gioco gioco, FinestraPrincipale mainFrame) {
        this.gioco = gioco;
        this.mainFrame = mainFrame;

        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        setBackground(MyColors.BACKGROUND_COLOR);

        // Panel destro per i controlli - allargato
        JPanel controlPanel = new JPanel(null);
        controlPanel.setBounds(700, 50, 300, 668);  // Aumentata larghezza a 300
        controlPanel.setBackground(MyColors.BACKGROUND_COLOR);

        // Titolo del turno
        JLabel titleLabel = new JLabel("Turno Investigatore");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(MyColors.TEXT_COLOR);
        titleLabel.setBounds(0, 20, 300, 40);
        controlPanel.add(titleLabel);

        // Info sul giocatore
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(MyColors.TEXT_COLOR);
        infoLabel.setBounds(20, 80, 260, 40);
        controlPanel.add(infoLabel);

        // Sezione movimento investigatore
        JLabel moveLabelInvestigatore = new JLabel("Celle Movimento Disponibili:");
        moveLabelInvestigatore.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        moveLabelInvestigatore.setForeground(MyColors.TEXT_COLOR);
        moveLabelInvestigatore.setBounds(20, 140, 260, 30);
        controlPanel.add(moveLabelInvestigatore);

        posizioniInvestigatorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        posizioniInvestigatorePanel.setBackground(MyColors.BACKGROUND_COLOR);
        posizioniInvestigatorePanel.setBounds(20, 180, 260, 100);
        controlPanel.add(posizioniInvestigatorePanel);

        posizioniInvestigatoreGroup = new ButtonGroup();

        // Bottone conferma movimento
        confermaButton = new JButton("Conferma Movimento");
        confermaButton.setBounds(20, 290, 200, 40);
        styleButton(confermaButton);
        controlPanel.add(confermaButton);

        // Sezione celle assassino
        JLabel moveLabelAssassino = new JLabel("Celle Assassino Disponibili:");
        moveLabelAssassino.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        moveLabelAssassino.setForeground(MyColors.TEXT_COLOR);
        moveLabelAssassino.setBounds(20, 350, 260, 30);
        controlPanel.add(moveLabelAssassino);

        posizioniAssassinoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        posizioniAssassinoPanel.setBackground(MyColors.BACKGROUND_COLOR);
        posizioniAssassinoPanel.setBounds(20, 390, 260, 100);
        controlPanel.add(posizioniAssassinoPanel);

        posizioniAssassinoGroup = new ButtonGroup();

        // Bottoni azioni assassino
        verificaIndizioButton = new JButton("Verifica Indizio");
        verificaIndizioButton.setBounds(20, 500, 200, 40);
        styleButton(verificaIndizioButton);
        controlPanel.add(verificaIndizioButton);

        arrestaButton = new JButton("Arresta Assassino");
        arrestaButton.setBounds(20, 550, 200, 40);
        styleButton(arrestaButton);
        controlPanel.add(arrestaButton);

        // Panel sinistro per il tabellone di gioco
        JPanel gamePanel = new JPanel() {
            private final Image bgImage = new ImageIcon("resources/BoardIMG.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        gamePanel.setBounds(50, 50, 650, 650);
        gamePanel.setBackground(MyColors.BUTTON_COLOR);

        // Aggiunta dei pannelli principali
        add(gamePanel);
        add(controlPanel);

        // Aggiunta dei listener
        confermaButton.addActionListener(e -> eseguiMossa());
        verificaIndizioButton.addActionListener(e -> verificaIndizio());
        arrestaButton.addActionListener(e -> tentaArresto());

        confermaButton.addActionListener(e -> System.out.println("TEST - Bottone premuto"));

        updatePanel();
    }

    private JToggleButton createMoveButton(String text, boolean isInvestigatore) {
        JToggleButton btn = new JToggleButton(text);
        btn.setPreferredSize(new Dimension(70, 30));  // Ridotta larghezza da 80 a 70
        btn.setMinimumSize(new Dimension(70, 30));
        btn.setMaximumSize(new Dimension(70, 30));
        btn.setActionCommand(text);
        styleButton(btn);

        if (!isInvestigatore) {
            btn.setBackground(MyColors.BUTTON_COLOR.darker());
        }

        return btn;
    }

    private void updateInfoLabel() {
        Investigatore investigatore = getInvestigatoreCorrente();
        if (investigatore != null) {
            String nomeInvestigatore = investigatore.getNome();
            infoLabel.setText("Investigatore: " + nomeInvestigatore);
        }
    }

    private void updateMosseDisponibili() {
        // Aggiorna celle movimento investigatore
        posizioniInvestigatorePanel.removeAll();
        posizioniInvestigatoreGroup.clearSelection();

        Investigatore investigatore = getInvestigatoreCorrente();
        if (investigatore != null) {
            List<CellaInvestigatore> mosseDisponibili = gioco.getTabellone()
                    .getCelleInvestigatoriVicine(investigatore.getPosizione());

            for (CellaInvestigatore cella : mosseDisponibili) {
                JToggleButton btn = createMoveButton(String.valueOf(cella.getNumero()), true);
                posizioniInvestigatoreGroup.add(btn);
                posizioniInvestigatorePanel.add(btn);
            }
        }

        // Aggiorna celle assassino raggiungibili
        posizioniAssassinoPanel.removeAll();
        posizioniAssassinoGroup.clearSelection();

        if (investigatore != null) {
            // Ottieni tutte le celle assassino adiacenti
            List<CellaAssassino> celleAssassino = gioco.getTabellone()
                    .getCelleAssassinoAdiacenti(investigatore.getPosizione());

            if (celleAssassino.isEmpty()) {
                JLabel noCellLabel = new JLabel("Nessuna cella assassino raggiungibile");
                noCellLabel.setForeground(MyColors.TEXT_COLOR);
                posizioniAssassinoPanel.add(noCellLabel);
            } else {
                for (CellaAssassino cella : celleAssassino) {
                    String cellNumber = String.valueOf(cella.getNumero());
                    JToggleButton btn = createMoveButton(cellNumber, false);
                    posizioniAssassinoGroup.add(btn);
                    posizioniAssassinoPanel.add(btn);
                }
            }
        }

        // Forza l'aggiornamento dei pannelli
        posizioniInvestigatorePanel.revalidate();
        posizioniInvestigatorePanel.repaint();
        posizioniAssassinoPanel.revalidate();
        posizioniAssassinoPanel.repaint();

        // Aggiorna anche il pannello contenitore
        this.revalidate();
        this.repaint();
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

    private Investigatore getInvestigatoreCorrente() {
        List<Giocatore> giocatori = gioco.getListaGiocatori();
        int turnoCorrente = gioco.getTurnoCorrente() % giocatori.size();

        // Accediamo direttamente all'indice invece di usare indexOf
        if (turnoCorrente >= 0 && turnoCorrente < giocatori.size()) {
            Giocatore giocatoreCorrente = giocatori.get(turnoCorrente);
            if (giocatoreCorrente instanceof Investigatore) {
                return (Investigatore) giocatoreCorrente;
            }
        }

        // Se non troviamo l'investigatore all'indice del turno,
        // cerchiamo il primo investigatore nella lista
        for (Giocatore g : giocatori) {
            if (g instanceof Investigatore) {
                return (Investigatore) g;
            }
        }

        return null;
    }

    private void eseguiMossa() {
        ButtonModel selectedButton = posizioniInvestigatoreGroup.getSelection();

        // Debug completo dell'investigatore
        Investigatore investigatore = getInvestigatoreCorrente();
        System.out.println("DEBUG - Dettagli Investigatore:");
        System.out.println("- Investigatore oggetto: " + investigatore);
        if (investigatore != null) {
            System.out.println("- Nome: " + investigatore.getNome());
            System.out.println("- Posizione: " + investigatore.getPosizione());
            System.out.println("- Index in lista giocatori: " + gioco.getListaGiocatori().indexOf(investigatore));
            System.out.println("- Turno corrente: " + gioco.getTurnoCorrente());
        }

        if (selectedButton == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una posizione per muoverti",
                    "Nessuna posizione selezionata",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String nuovaPosizione = selectedButton.getActionCommand();
            //System.out.println("DEBUG - Dettagli Movimento:");
            ///System.out.println("- Nuova posizione selezionata: " + nuovaPosizione);

            if (investigatore != null) {
                // Debug pre-movimento
                //System.out.println("- Pre-movimento - Posizione attuale: " + investigatore.getPosizione());

                // Esegui il movimento
                gioco.muoviInvestigatore(investigatore, nuovaPosizione);

                // Debug post-movimento
                //System.out.println("- Post-movimento - Nuova posizione: " + investigatore.getPosizione());

                // Aggiorna il pannello principale
                this.mainFrame.getContentPane().removeAll();
                this.mainFrame.getContentPane().add(new PannelloDiGioco(gioco, mainFrame));
                this.mainFrame.getContentPane().revalidate();
                this.mainFrame.getContentPane().repaint();

                //System.out.println("- Aggiornamento UI completato");
            } else {
                //System.out.println("ERRORE CRITICO: Investigatore null dopo la verifica iniziale!");
                // Debug stato del gioco
                //System.out.println("- Stato del gioco:");
                //System.out.println("  * Numero giocatori: " + gioco.getListaGiocatori().size());
                //System.out.println("  * Turno corrente: " + gioco.getTurnoCorrente());
            }
        } catch (Exception e) {
            //System.out.println("ERRORE durante eseguiMossa(): " + e.getMessage());
            //System.out.println("Stack trace completo:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Errore durante il movimento: " + e.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verificaIndizio() {
        ButtonModel selectedButton = posizioniAssassinoGroup.getSelection();
        if (selectedButton == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una posizione da verificare",
                    "Nessuna posizione selezionata",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String posizione = selectedButton.getActionCommand();
        CellaAssassino cellaAssassino = gioco.getTabellone().getCellaAssassino(Integer.parseInt(posizione) -1);

        if (cellaAssassino != null) {
            boolean indizioTrovato = gioco.verificaIndizio(cellaAssassino);
            String message = indizioTrovato ?
                    "Hai trovato un indizio! L'assassino è passato da qui." :
                    "Nessun indizio trovato in questa posizione.";

            JOptionPane.showMessageDialog(this,
                    message,
                    "Risultato verifica",
                    indizioTrovato ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
        }
    }

    private void tentaArresto() {
        ButtonModel selectedButton = posizioniAssassinoGroup.getSelection();
        if (selectedButton == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una posizione dove tentare l'arresto",
                    "Nessuna posizione selezionata",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String posizione = selectedButton.getActionCommand();
        CellaAssassino cellaAssassino = gioco.getTabellone().getCellaAssassino(Integer.parseInt(posizione) - 1);
        Investigatore investigatore = getInvestigatoreCorrente();

        if (cellaAssassino != null && investigatore != null) {
            try {
                if (gioco.tentaArresto(investigatore, cellaAssassino)) {
                    JOptionPane.showMessageDialog(this,
                            "Congratulazioni! Hai arrestato l'assassino!",
                            "Arresto riuscito",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Aggiorna il pannello di gioco per mostrare la vittoria
                    this.mainFrame.getContentPane().removeAll();
                    this.mainFrame.getContentPane().add(new PannelloVittoriaInvestigatore(gioco, mainFrame));
                    this.mainFrame.getContentPane().revalidate();
                    this.mainFrame.getContentPane().repaint();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "L'assassino non si trova in questa posizione. Il gioco continua!",
                            "Arresto fallito",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                System.out.println("ERRORE durante tentaArresto(): " + e.getMessage());
                JOptionPane.showMessageDialog(this,
                        "Errore durante il tentativo di arresto: " + e.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Errore nel tentativo di arresto. Verifica la selezione della cella.",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updatePanel() {
        updateInfoLabel();
        updateMosseDisponibili();
    }
}