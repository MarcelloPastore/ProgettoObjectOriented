package it.unimol.gioco.app.board;

public class Cella {
    protected String quadrante;
    protected String numero;

    public Cella(String quadrante, String numero) {
        this.quadrante = quadrante;
        this.numero = numero;
    }

    public String getQuadrante() {
        return quadrante;
    }

    public void setQuadrante(String quadrante) {
        this.quadrante = quadrante;
    }

}

