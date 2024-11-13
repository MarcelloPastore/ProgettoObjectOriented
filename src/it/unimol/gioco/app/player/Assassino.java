package it.unimol.gioco.app.player;

import it.unimol.gioco.app.board.Cella;
import it.unimol.gioco.app.board.CellaAssassino;

import java.util.List;

public class Assassino extends Giocatore {
    private List<CellaAssassino> obiettivi;
    private int mosseCounter;
    public Assassino(String nome,Cella posizione, List<CellaAssassino> obiettivi) {
        super(nome, posizione);
        this.obiettivi = obiettivi;
    }

    public List<CellaAssassino> getObiettivi() {
        return obiettivi;
    }

    public void setObiettivi(List<CellaAssassino> obiettivi) {
        this.obiettivi = obiettivi;
    }

    public int getMosseCounter() {
        return mosseCounter;
    }

    public void setMosseCounter(int mosseCounter) {
        this.mosseCounter = mosseCounter;
    }
}

