package it.unimol.gioco.app.player;

import it.unimol.gioco.app.board.Cella;
import it.unimol.gioco.app.board.CellaInvestigatore;

public class Investigatore extends Giocatore {
    private Boolean arresto;
    public Investigatore(String nome, CellaInvestigatore posizione, Boolean arresto) {
        super(nome, posizione);
        this.arresto = arresto;
    }

    public Boolean getArresto() {
        return arresto;
    }

    public void setArresto(Boolean arresto) {
        this.arresto = arresto;
    }

}

