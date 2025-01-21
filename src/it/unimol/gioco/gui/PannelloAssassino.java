package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.Tabellone;
import it.unimol.gioco.app.exceptions.MaxAssassinException;
import it.unimol.gioco.app.player.Assassino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PannelloAssassino extends JPanel {

    private final Gioco gioco;
    private final Tabellone board = new Tabellone();
    private final JTextField nomeAssassino;
    private final JPanel posizioniPanel;
    private final JPanel obiettiviPanel;
    private Assassino assassinoSalvato;
    private final List<JToggleButton> posizioniButtons = new ArrayList<>();
    private final List<JToggleButton> obiettiviButtons = new ArrayList<>();
    private ButtonGroup posizioniGroup; // Per permettere una sola selezione
    private int obiettiviSelezionati = 0;
    private static final int MAX_OBIETTIVI = 4;
    private final FinestraPrincipale mainFrame;

    public PannelloAssassino(Gioco gioco, FinestraPrincipale mainFrame) {
        this.gioco = gioco;
        this.mainFrame = mainFrame;

        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        setBackground(MyColors.BACKGROUND_COLOR);

        // Panel sinistro per i controlli
        JPanel controlPanel = new JPanel(null);
        controlPanel.setBounds(50, 50, 400, 668);
        controlPanel.setBackground(MyColors.BACKGROUND_COLOR);

        // Titolo
        JLabel titolo = createLabel("Configurazione Nuova Partita", 0, 20, 400, 40);
        titolo.setFont(new Font("Georgia", Font.BOLD, 24));
        titolo.setForeground(MyColors.TITLE_COLOR);
        controlPanel.add(titolo);

        // Nome assassino
        JLabel nomeLabel = createLabel("Nome Assassino:", 20, 80, 200, 30);
        controlPanel.add(nomeLabel);

        nomeAssassino = new JTextField();
        nomeAssassino.setBounds(20, 110, 250, 30);
        nomeAssassino.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nomeAssassino.setForeground(MyColors.TEXT_COLOR);
        nomeAssassino.setBackground(MyColors.BUTTON_COLOR);
        controlPanel.add(nomeAssassino);

        // Posizioni
        JLabel posizioniLabel = createLabel("Posizione Iniziale:", 20, 160, 200, 30);
        controlPanel.add(posizioniLabel);

        posizioniPanel = new JPanel();
        posizioniPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        posizioniPanel.setBounds(20, 190, 360, 150);
        posizioniPanel.setBackground(MyColors.BACKGROUND_COLOR);
        controlPanel.add(posizioniPanel);

        // Obiettivi
        JLabel obiettiviLabel = createLabel("Obiettivi (seleziona 4):", 20, 350, 200, 30);
        controlPanel.add(obiettiviLabel);

        obiettiviPanel = new JPanel();
        obiettiviPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        obiettiviPanel.setBounds(20, 380, 360, 150);
        obiettiviPanel.setBackground(MyColors.BACKGROUND_COLOR);
        controlPanel.add(obiettiviPanel);

        // Bottone conferma
        JButton confermaButton = createStyledButton("Conferma", 20, 550, 200, 40);
        controlPanel.add(confermaButton);

        confermaButton.addActionListener(e -> {
            try {
                gestisciSalvataggioAssassino();
            } catch (MaxAssassinException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Panel destro per il tabellone
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

        // Inizializzazione dei bottoni
        inizializzaBottoni();
        inizializzaPosizioni();
        inizializzaObiettivi();
    }

    private void gestisciSalvataggioAssassino() throws MaxAssassinException {
        if (!validaDatiAssassino()) {
            return;
        }
        salvaDatiAssassino();
        // passo al prossimo giocatore
        nextPlayer();;
    }

    private boolean validaDatiAssassino() {
        String nome = nomeAssassino.getText().trim();
        String posizione = getPosizioneSelezionata();
        List<String> obiettiviSelezionati = getObiettiviSelezionati();

        // Validazione nome
        if (nome.isEmpty()) {
            mostraErrore("Inserisci il nome dell'assassino.");
            return false;
        }

        // Validazione posizione
        if (posizione == null) {
            mostraErrore("Seleziona una posizione iniziale.");
            return false;
        }

        // Validazione obiettivi
        if (obiettiviSelezionati.size() != MAX_OBIETTIVI) {
            mostraErrore("Devi selezionare esattamente " + MAX_OBIETTIVI + " obiettivi.");
            return false;
        }
        return true;
    }

    private void salvaDatiAssassino() throws MaxAssassinException {
        String nome = nomeAssassino.getText().trim();
        int posizione = Integer.parseInt(getPosizioneSelezionata());
        List<String> obiettivi = getObiettiviSelezionati();

       assassinoSalvato = (Assassino) gioco.inserisciAssassino(nome, posizione);

        // Debug info
        //System.out.println("Assassino salvato:");
        //System.out.println("Nome: " + assassinoSalvato.getNome());
        //System.out.println("Posizione: " + assassinoSalvato.getPosizione());
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this,
                messaggio,
                "Errore",
                JOptionPane.ERROR_MESSAGE);
    }

    public Assassino getAssassinoSalvato() {
        return assassinoSalvato;
    }

    public void aggiungiPosizione(String posizione) {
        JToggleButton btn = createToggleButton(posizione, 80, 30);
        posizioniButtons.add(btn);
        posizioniGroup.add(btn);
        posizioniPanel.add(btn);
        posizioniPanel.revalidate();
        posizioniPanel.repaint();
    }

    public void aggiungiObiettivo(String obiettivo) {
        JToggleButton btn = createToggleButton(obiettivo, 80, 30);
        obiettiviButtons.add(btn);
        obiettiviPanel.add(btn);

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                if (obiettiviSelezionati >= MAX_OBIETTIVI) {
                    btn.setSelected(false);
                    return;
                }
                obiettiviSelezionati++;
            } else {
                obiettiviSelezionati--;
            }
        });

        obiettiviPanel.revalidate();
        obiettiviPanel.repaint();
    }

    private void inizializzaBottoni() {
        posizioniGroup = new ButtonGroup();
    }

    private JToggleButton createToggleButton(String text, int width, int height) {
        JToggleButton btn = new JToggleButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected()) {
                    g2.setColor(MyColors.BUTTON_SELECTED);
                } else if (getModel().isPressed()) {
                    g2.setColor(MyColors.BUTTON_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(MyColors.BUTTON_HOVER);
                } else {
                    g2.setColor(MyColors.BUTTON_COLOR);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setPreferredSize(new Dimension(width, height));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(MyColors.TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        return btn;
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(MyColors.TEXT_COLOR);
        return label;
    }

    private JButton createStyledButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(MyColors.BUTTON_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(MyColors.BUTTON_HOVER);
                } else {
                    g2.setColor(MyColors.BUTTON_COLOR);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setBounds(x, y, width, height);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(MyColors.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        return button;
    }

    public String getPosizioneSelezionata() {
        for (JToggleButton btn : posizioniButtons) {
            if (btn.isSelected()) {
                return btn.getText();
            }
        }
        return null;
    }

    public List<String> getObiettiviSelezionati() {
        List<String> obiettivi = new ArrayList<>();
        for (JToggleButton btn : obiettiviButtons) {
            if (btn.isSelected()) {
                obiettivi.add(btn.getText());
            }
        }
        return obiettivi;
    }

    public void nextPlayer() {
        // Prima di cambiare pannello, inizializza la mappa
        gioco.inizializzaMappa();

        // Converti gli obiettivi selezionati in array di int
        List<String> obiettiviString = getObiettiviSelezionati();
        int[] obiettivi = obiettiviString.stream()
                .mapToInt(Integer::parseInt)
                .toArray();
        gioco.addObiettivo(obiettivi);

        // Cambia il pannello
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().add(new PannelloInvestigatore(gioco, mainFrame));
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    private void inizializzaPosizioni() {
        // Aggiungi tutte le posizioni
        List<String> numeriCelleBianche = board.getNumeriCelleBianche(); // Ottieni i numeri dal metodo
        for (String numero : numeriCelleBianche) {
            aggiungiPosizione(numero);
        }
    }

    private void inizializzaObiettivi() {
        // Aggiungi tutti gli obiettivi
        List<String> numeriCelleBianche = board.getNumeriCelleBianche();
        for (String numero : numeriCelleBianche) {
            aggiungiObiettivo(numero); // Differente da aggiungiPosizione
        }
    }
}