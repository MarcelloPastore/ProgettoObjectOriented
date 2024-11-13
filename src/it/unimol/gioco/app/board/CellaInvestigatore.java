package it.unimol.gioco.app.board;

public class CellaInvestigatore extends Cella {
    private Boolean startingPoint;

    public CellaInvestigatore(String quadrante, String nome, Boolean startingPoint) {
        super(quadrante, nome);
        this.startingPoint = startingPoint;
    }

    public Boolean getStartingPoint() {
        return startingPoint;
    }


    public void setStartingPoint(Boolean startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getNome() {
        return getNumero();
    }
}
