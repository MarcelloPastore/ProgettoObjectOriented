package it.unimol.gioco.app.board;

public class CellaInvestigatore extends Cella {
    private Boolean startingPoint;

    public CellaInvestigatore(String quadrante, String numero, Boolean startingPoint) {
        super(quadrante, numero);
        this.startingPoint = startingPoint;
    }

    public Boolean getStartingPoint() {
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
        return "CellaInvestigatore{nome='" + numero + "'}";
    }
}
