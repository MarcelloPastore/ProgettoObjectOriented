package it.unimol.gioco.app.player;

import it.unimol.gioco.app.board.CellaInvestigatore;

import java.io.Serializable;

public class Investigatore extends Giocatore implements Serializable {
    private Boolean arresto;

    public Investigatore(String nome, CellaInvestigatore posizione, Boolean arresto) {
        super(nome, posizione);
        this.arresto = arresto;
    }

    public Boolean getArresto() {
        return arresto;
    }

    @Override
    public CellaInvestigatore getPosizione() {
        return (CellaInvestigatore) super.getPosizione();
    }

    public void setPosizione(CellaInvestigatore nuovaPosizione) {
        super.setPosizione(nuovaPosizione);
    }

    public void setArresto(Boolean arresto) {
        this.arresto = arresto;
    }
}