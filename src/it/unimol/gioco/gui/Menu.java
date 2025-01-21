package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    private final Gioco gioco;
    private JTextArea txtTitolo;
    private JButton btnInizia;
    private JButton btnCarica;
    private JButton btnEsci;
    private final FinestraPrincipale mainFrame;

    public Menu(FinestraPrincipale mainFrame, Gioco gioco) {
        this.mainFrame = mainFrame;
        this.gioco = gioco;

        setLayout(null); // Usiamo null layout per posizionamento preciso
        setPreferredSize(new Dimension(1024, 768));
        setBackground(MyColors.BACKGROUND_COLOR);

        // Configurazione del titolo
        txtTitolo = new JTextArea("Benvenuto in WhiteHall Mistery");
        txtTitolo.setFont(new Font("Georgia", Font.BOLD, 36));
        txtTitolo.setForeground(MyColors.TITLE_COLOR);
        txtTitolo.setEditable(false);
        txtTitolo.setBackground(MyColors.BACKGROUND_COLOR);
        txtTitolo.setWrapStyleWord(true);
        txtTitolo.setLineWrap(true);
        txtTitolo.setBounds((1224 - 600) / 2, 150, 600, 100);
        txtTitolo.setAlignmentX(SwingConstants.CENTER);

        // Dimensioni comuni per i bottoni
        int buttonWidth = 250;
        int buttonHeight = 50;
        int startX = (1224 - buttonWidth) / 2;
        int startY = 350;
        int spacing = 80;

        // Configurazione dei bottoni
        btnInizia = createStyledButton("Inizia", startX, startY, buttonWidth, buttonHeight);
        btnCarica = createStyledButton("Carica", startX, startY + spacing, buttonWidth, buttonHeight);
        btnEsci = createStyledButton("Esci", startX, startY + spacing * 2, buttonWidth, buttonHeight);

        // Aggiunta dei componenti
        add(txtTitolo);
        add(btnInizia);
        add(btnCarica);
        add(btnEsci);

        // Aggiunta degli action listener
        btnInizia.addActionListener(e -> iniziaGioco());
        btnCarica.addActionListener(e -> caricaPartita());
        btnEsci.addActionListener(e -> System.exit(0));
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
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(MyColors.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        // Effetto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        return button;
    }

    private void iniziaGioco() {
        System.out.println("Inizia nuova partita");
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().add(new PannelloAssassino(gioco, mainFrame));
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    private void caricaPartita() {
        System.out.println("Carica partita esistente");
    }
}