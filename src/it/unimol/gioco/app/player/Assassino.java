package it.unimol.gioco.app.player;

import it.unimol.gioco.app.board.CellaAssassino;

import java.io.Serializable;
import java.util.List;

public class Assassino extends Giocatore implements Serializable {
    private List<CellaAssassino> obiettivi;
    private int mosseCounter;
    protected CellaAssassino posizione;

    public Assassino(String nome,CellaAssassino posizione, List<CellaAssassino> obiettivi) {
        super(nome, posizione);
        this.obiettivi = obiettivi;
        this.posizione = posizione;
    }

    public List<CellaAssassino> getObiettivi() {
        return obiettivi;
    }

    @Override
    public CellaAssassino getPosizione(){
        return this.posizione;
    }

    public void setObiettivi(List<CellaAssassino> obiettivi) {
        this.obiettivi = obiettivi;
    }


    public void setPosizione(CellaAssassino nuovaPosizione){
        this.posizione = nuovaPosizione;
    }

    public int getMosseCounter() {
        return mosseCounter;
    }

    public void setMosseCounter(int mosseCounter) {
        this.mosseCounter = mosseCounter;
    }


}

