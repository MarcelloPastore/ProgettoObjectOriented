package it.unimol.gioco.app;

public class CellaInvestigatore extends Cella {
    private Boolean startingPoint;

    public CellaInvestigatore(String quadrante, String numero, Boolean startingPoint) {
        super(quadrante, numero);
        this.startingPoint = startingPoint;
    }

    public Boolean getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Boolean startingPoint) {
        this.startingPoint = startingPoint;
    }
}
