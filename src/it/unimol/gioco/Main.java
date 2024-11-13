package it.unimol.gioco;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.Cella;
import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.player.Giocatore;

public class Main {
    public static void main(String[] args) {
        Gioco gioco = new Gioco();
        gioco.inizia();
    }
}

