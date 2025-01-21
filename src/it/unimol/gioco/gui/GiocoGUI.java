package it.unimol.gioco.gui;

import it.unimol.gioco.app.Gioco;

public class GiocoGUI {
    private final Gioco gioco;
        public GiocoGUI() {
            gioco = new Gioco();
        }
    public void start() {
        // Inizializza la finestra principale passando l'istanza di Gioco
        FinestraPrincipale finestraPrincipale = new FinestraPrincipale(gioco);
        finestraPrincipale.setVisible(true);
    }
}