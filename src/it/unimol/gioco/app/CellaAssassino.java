package it.unimol.gioco.app;

public class CellaAssassino extends Cella{
    private String tipo;

    public CellaAssassino(String quadrante, String numero, String tipo) {
            super(quadrante, numero);
        this.tipo = tipo;
    }
}
