package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.Tabellone;
import it.unimol.gioco.app.player.Giocatore;
import it.unimol.gioco.app.player.Investigatore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PannelloInvestigatore extends JPanel {


    private final List<Investigatore> investigatori = new ArrayList<>();
    private int numeroInvestigatoriAttuali = 0;
    private static final int MAX_INVESTIGATORI = 2;
    private final Tabellone board = new Tabellone();
    private final JTextField nomeInvestigatore;
    private final JPanel posizioniPanel;
    private final JButton nuovoInvestigatoreButton;
    private final JButton iniziaPartitaButton;
    private final Gioco gioco;
    private final List<JToggleButton> posizioniButtons = new ArrayList<>();
    private ButtonGroup posizioniGroup;
    private FinestraPrincipale mainFrame;

    public PannelloInvestigatore(Gioco gioco, FinestraPrincipale mainFrame) {
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
        JLabel titolo = createLabel("Configurazione Investigatore", 0, 20, 400, 40);
        titolo.setFont(new Font("Georgia", Font.BOLD, 24));
        titolo.setForeground(MyColors.TITLE_COLOR);
        controlPanel.add(titolo);

        // Nome investigatore
        JLabel nomeLabel = createLabel("Nome Investigatore:", 20, 80, 200, 30);
        controlPanel.add(nomeLabel);

        nomeInvestigatore = new JTextField();
        nomeInvestigatore.setBounds(20, 110, 250, 30);
        nomeInvestigatore.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nomeInvestigatore.setForeground(MyColors.TEXT_COLOR);
        nomeInvestigatore.setBackground(MyColors.BUTTON_COLOR);
        controlPanel.add(nomeInvestigatore);

        // Posizioni
        JLabel posizioniLabel = createLabel("Posizione Iniziale:", 20, 150, 200, 30);
        controlPanel.add(posizioniLabel);

        posizioniPanel = new JPanel();
        posizioniPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        posizioniPanel.setBounds(20, 180, 360, 150);
        posizioniPanel.setBackground(MyColors.BACKGROUND_COLOR);
        controlPanel.add(posizioniPanel);

        // Panel per i bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setBounds(20, 600, 360, 40);
        buttonPanel.setBackground(MyColors.BACKGROUND_COLOR);

        // Bottoni
        nuovoInvestigatoreButton = createStyledButton("Nuovo Investigatore", 160, 40);
        iniziaPartitaButton = createStyledButton("Inizia Partita", 160, 40);

        buttonPanel.add(nuovoInvestigatoreButton);
        buttonPanel.add(iniziaPartitaButton);
        controlPanel.add(buttonPanel);

        // Panel destro per il placeholder del tabellone
        JPanel gamePanel = new JPanel() {
            private final Image bgImage = new ImageIcon("resources/BoardIMG.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this); // Adatta immagine alla dimensione del pannello
            }
        };
        gamePanel.setBounds(500, 50, 650, 650);
        gamePanel.setBackground(MyColors.BUTTON_COLOR);

        // Aggiunta dei pannelli principali
        add(controlPanel);
        add(gamePanel);

        // Inizializza i gruppi di bottoni
        inizializzaBottoni();
        inizializzaPosizioni();

        nuovoInvestigatoreButton.addActionListener(e -> gestisciNuovoInvestigatore());
        iniziaPartitaButton.addActionListener(e -> gestisciInizioPartita());
    }

    private void gestisciNuovoInvestigatore() {
        // Valida i dati dell'investigatore corrente
        if (validaDatiInvestigatore()) {
            return;
        }

        // Verifica se abbiamo raggiunto il numero massimo di investigatori
        if (numeroInvestigatoriAttuali >= MAX_INVESTIGATORI) {
            JOptionPane.showMessageDialog(this,
                    "Hai raggiunto il numero massimo di investigatori.",
                    "Limite raggiunto",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Salva i dati dell'investigatore corrente
        salvaDatiInvestigatore();

        // Resetta i campi per il nuovo investigatore
        resetCampi();

        // Aggiorna il contatore
        numeroInvestigatoriAttuali++;

        // Aggiorna lo stato dei bottoni
        aggiornaStatoBottoni();
    }

    private void gestisciInizioPartita() {
        // Controlla se ci sono dati non salvati
        if (!nomeInvestigatore.getText().trim().isEmpty() || getPosizioneSelezionata() != null) {
            // Chiedi all'utente se vuole salvare i dati correnti
            int risposta = JOptionPane.showConfirmDialog(this,
                    "Ci sono dati non salvati. Vuoi salvare questo investigatore prima di iniziare?",
                    "Dati non salvati",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (risposta == JOptionPane.YES_OPTION) {
                if (validaDatiInvestigatore()) {
                    return;
                }
                salvaDatiInvestigatore();
            } else if (risposta == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        // Verifica che ci sia almeno un investigatore
        if (investigatori.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Devi aggiungere almeno un investigatore per iniziare la partita.",
                    "Nessun investigatore",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Inizializza la mappa di gioco
        gioco.inizializzaMappa();

        // Ottiene il riferimento alla finestra principale
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().add(new PannelloDiGioco(gioco, mainFrame));
        this.mainFrame.revalidate();
        this.mainFrame.repaint();

    }

    private boolean validaDatiInvestigatore() {
        String nome = nomeInvestigatore.getText().trim();
        String posizione = getPosizioneSelezionata();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Inserisci il nome dell'investigatore.",
                    "Campo mancante",
                    JOptionPane.WARNING_MESSAGE);
            return true;
        }

        if (posizione == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleziona una posizione iniziale.",
                    "Selezione mancante",
                    JOptionPane.WARNING_MESSAGE);
            return true;
        }

        // Verifica che il nome non sia già in uso
        for (Investigatore inv : investigatori) {
            if (inv.getNome().equalsIgnoreCase(nome)) {
                JOptionPane.showMessageDialog(this,
                        "Esiste già un investigatore con questo nome.",
                        "Nome duplicato",
                        JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }

        // Verifica che la posizione non sia già occupata
        for (Investigatore inv : investigatori) {
            if (inv.getPosizione().equals(posizione)) {
                JOptionPane.showMessageDialog(this,
                        "Questa posizione è già occupata.",
                        "Posizione non disponibile",
                        JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }

        return false;
    }

    private void salvaDatiInvestigatore() {
        String nome = nomeInvestigatore.getText().trim();
        String posizione = getPosizioneSelezionata();

        // Salva l'investigatore nel gioco e ottieni l'istanza creata
        Investigatore nuovoInvestigatore = (Investigatore) gioco.inserisciInvestigatore(nome, posizione);


        // Aggiungi l'investigatore anche alla lista locale
        investigatori.add(nuovoInvestigatore);
         System.out.println("Nome: " + nuovoInvestigatore.getNome());
         System.out.println("posizione: " + nuovoInvestigatore.getPosizione());
         System.out.println("Giocatori: " + gioco.getListaGiocatori());
    }

    private void resetCampi() {
        nomeInvestigatore.setText("");
        posizioniGroup.clearSelection();
        nomeInvestigatore.requestFocus();
    }

    private void aggiornaStatoBottoni() {
        // Disabilita il bottone "Nuovo Investigatore" se abbiamo raggiunto il massimo
        nuovoInvestigatoreButton.setEnabled(numeroInvestigatoriAttuali < MAX_INVESTIGATORI);

        // Il bottone "Inizia Partita" è sempre abilitato perché possiamo
        // iniziare anche con un solo investigatore
        iniziaPartitaButton.setEnabled(true);
    }

    private void inizializzaBottoni() {
        posizioniGroup = new ButtonGroup();
    }

    public void aggiungiPosizione(String posizione) {
        JToggleButton btn = createToggleButton(posizione, 80, 30);
        posizioniButtons.add(btn);
        posizioniGroup.add(btn);
        posizioniPanel.add(btn);
        posizioniPanel.revalidate();
        posizioniPanel.repaint();
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

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
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

    private JButton createStyledButton(String text, int width, int height) {
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

        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(MyColors.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
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

    private void inizializzaPosizioni() {
        // Aggiungi tutte le posizioni
        List<String> numeriStartingPoint = board.getNumeriStartingPoint(); // Ottieni i numeri dal metodo
        for (String numero : numeriStartingPoint) {
           aggiungiPosizione(numero);
        }
    }
}


