package it.unimol.gioco.app.player;

import it.unimol.gioco.app.board.Cella;

public class Giocatore {
    private String nome;
    private Cella posizione;

    public Giocatore(String nome, Cella posizione) {
        this.nome = nome;
        this.posizione = posizione;
    }

    public String getNome() {
        return nome;
    }

    public Cella getPosizione() {
        return posizione;
    }

    public void setPosizione(Cella posizione) {
        this.posizione = posizione;
    }
}

