package it.unimol.gioco.app.board;

import java.io.Serializable;
import java.util.HashMap;

public class Cella implements Serializable {
    protected String quadrante;
    protected String numero;

    public Cella(String quadrante, String numero) {
        this.quadrante = quadrante;
        this.numero = numero;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cella) {
            Cella cella = (Cella) obj;
            return this.quadrante.equals(cella.quadrante) && this.numero.equals(cella.numero);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return quadrante.hashCode() + numero.hashCode();
    }

    public String getQuadrante() {
        return quadrante;
    }

    public void setQuadrante(String quadrante) {
        this.quadrante = quadrante;
    }

    @Override
    public String toString() {
        return "Cella numero: " + numero ;
    }

    public String getNumero() {
        return numero;
    }


}

