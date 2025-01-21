package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class PannelloVittoria extends JPanel {
    protected final Gioco gioco;
    protected final FinestraPrincipale mainFrame;
    protected JPanel contentPanel;
    protected AnimatedLabel titleLabel;
    protected AnimatedLabel imageLabel;
    protected AnimatedLabel messageLabel;
    protected JButton restartButton;
    protected int animationStep = 0;
    protected Timer animationTimer;
    protected float alpha = 0f;

    // Classe interna per gestire label animate
    protected class AnimatedLabel extends JLabel {
        protected float opacity = 0f;


        public AnimatedLabel(String text) {
            super(text);
        }

        public AnimatedLabel(Icon icon) {
            super(icon);
        }

        public void setOpacity(float opacity) {
            this.opacity = Math.max(0f, Math.min(1f, opacity));
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }

    public PannelloVittoria(Gioco gioco, FinestraPrincipale mainFrame) {
        this.gioco = gioco;
        this.mainFrame = mainFrame;

        setLayout(null);
        setPreferredSize(new Dimension(1024, 768));
        setBackground(MyColors.BACKGROUND_COLOR);

        setupCommonComponents();

        titleLabel = new AnimatedLabel("Hai Vinto!");
        styleLabel(titleLabel);
        titleLabel.setBounds(100, -50, 400, 50); // Posizione iniziale per l'animazione
        contentPanel.add(titleLabel);

        imageLabel = new AnimatedLabel(new ImageIcon("resources/Vittoria.jpeg"));
        imageLabel.setBounds(100, -200, 200, 200); // Posizione iniziale per l'animazione
        contentPanel.add(imageLabel);

        messageLabel = new AnimatedLabel("Congratulazioni per aver completato il gioco!");
        styleLabel(messageLabel);
        messageLabel.setBounds(50, 200, 500, 50);
        contentPanel.add(messageLabel);

        setupVictorySpecifics(contentPanel); // Configura contenuti specifici
        startAnimation();

    }

    private void setupCommonComponents() {
        contentPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        contentPanel.setBounds(212, 134, 600, 500);
        contentPanel.setBackground(MyColors.BUTTON_COLOR);
        add(contentPanel);

        restartButton = new JButton("Nuova Partita");
        restartButton.setBounds(200, 400, 200, 40);
        restartButton.setVisible(false);
        styleButton(restartButton);
        restartButton.addActionListener(e -> ricomincia());
        contentPanel.add(restartButton);

        animationTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimation();
            }
        });
    }

    protected void updateAnimation() {
        switch (animationStep) {
            case 0: // Fade in del pannello
                alpha += 0.1f;
                if (alpha >= 1.0f) {
                    alpha = 1.0f;
                    animationStep++;
                }
                break;
            case 1: // Animazione del titolo
                titleLabel.setOpacity(Math.min(1f, titleLabel.opacity + 0.1f));
                titleLabel.setLocation(titleLabel.getX(),
                        Math.min(50, titleLabel.getY() + 10));
                if (titleLabel.getY() >= 50 && titleLabel.opacity >= 1f) {
                    animationStep++;
                }
                break;
            case 2: // Animazione dell'immagine
                imageLabel.setOpacity(Math.min(1f, imageLabel.opacity + 0.1f));
                if (imageLabel.getY() < 120) {
                    imageLabel.setLocation(imageLabel.getX(),
                            imageLabel.getY() + 15);
                }
                if (imageLabel.getY() >= 120 && imageLabel.opacity >= 1f) {
                    animationStep++;
                }
                break;
            case 3: // Animazione del messaggio
                messageLabel.setOpacity(Math.min(1f, messageLabel.opacity + 0.1f));
                if (messageLabel.opacity >= 1f) {
                    animationTimer.stop();
                    showRestartButton();
                }
                break;
        }
        repaint();
    }

    private void startAnimation() {
        // Posizioni iniziali per l'animazione
        titleLabel.setLocation(titleLabel.getX(), -50);
        titleLabel.setOpacity(0f);

        imageLabel.setLocation(imageLabel.getX(), -200);
        imageLabel.setOpacity(0f);

        messageLabel.setOpacity(0f);

        animationTimer.start();
    }

    private void showRestartButton() {
        Timer buttonTimer = new Timer(1000, e -> {
            restartButton.setVisible(true);
            ((Timer)e.getSource()).stop();
        });
        buttonTimer.setRepeats(false);
        buttonTimer.start();
    }

    protected void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
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

    protected void styleLabel(AnimatedLabel label) {
        label.setFont(new Font("Georgia", Font.BOLD, 24));
        label.setForeground(MyColors.TEXT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void ricomincia() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(new Menu(mainFrame, gioco));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    protected abstract void setupVictorySpecifics(JPanel contentPanel);
}