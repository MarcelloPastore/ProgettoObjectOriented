package it.unimol.gioco.app.board;

import java.io.Serializable;

public class CellaAssassino extends Cella implements Serializable {
    private final String tipo;

    public CellaAssassino(String quadrante, String numero, String tipo) {
            super(quadrante, numero);
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return "Cella numero: " + numero ;
    }

    public String getTipo() {
        return tipo;
    }
}
