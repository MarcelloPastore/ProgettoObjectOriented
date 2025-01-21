package it.unimol.gioco.app.board;

import java.io.Serializable;

public class CellaInvestigatore extends Cella implements Serializable {
    private Boolean startingPoint;

    public CellaInvestigatore(String quadrante, String numero, Boolean startingPoint) {
        super(quadrante, numero);
        this.startingPoint = startingPoint;
    }

    public Boolean isStartingPoint() {
        return startingPoint;
    }

    public String getNumero() {
        return numero;
    }

    public void setStartingPoint(Boolean startingPoint) {
        this.startingPoint = startingPoint;
    }

    @Override
    public String toString() {
        return "CellaInvestigatore: " + numero ;
    }

    public String getStartingPoint() {
        return startingPoint.toString();
    }
}
