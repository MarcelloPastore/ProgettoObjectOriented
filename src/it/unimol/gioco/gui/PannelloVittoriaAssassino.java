package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;
import javax.swing.*;
import java.awt.*;

public class PannelloVittoriaAssassino extends PannelloVittoria {

    public PannelloVittoriaAssassino(Gioco gioco, FinestraPrincipale mainFrame) {
        super(gioco, mainFrame);
    }

    @Override
    protected void setupVictorySpecifics(JPanel contentPanel) {
        // Titolo vittoria con effetto slide-in dall'alto
        titleLabel = new AnimatedLabel("VITTORIA ASSASSINO!");
        titleLabel.setBounds(0, -50, 600, 40);
        styleLabel(titleLabel);
        contentPanel.add(titleLabel);

        // Immagine vittoria con effetto fade-in
        imageLabel = new AnimatedLabel(new ImageIcon("resources/VittoriaAssassino.jpeg"));
        imageLabel.setBounds(200, -200, 200, 200);
        contentPanel.add(imageLabel);

        // Messaggio
        messageLabel = new AnimatedLabel("<html><center>Hai eliminato tutti gli obiettivi!<br>La città è nel caos!</center></html>");
        messageLabel.setBounds(100, 340, 400, 60);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        messageLabel.setForeground(MyColors.TEXT_COLOR);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(messageLabel);
    }
}