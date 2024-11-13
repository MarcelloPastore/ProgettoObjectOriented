package it.unimol.gioco.app.player;

import it.unimol.gioco.app.board.Cella;

public class Investigatore extends Giocatore {
    private Boolean arresto;
    public Investigatore(String nome,Cella posizione, Boolean arresto) {
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

