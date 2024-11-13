package it.unimol.gioco.ui;

public class StampaErroriUI {
    public void erroreDiStampa1(){
        System.out.println("Errore 01: il parametro inserito non è corretto");
    }

    public void erroreRicercaCellaInvestigatore1(){
        System.out.println("Nome della cella Investigatore fornito è null.");
    }
    public void erroreRicercaCellaInvestigatore2(){
        System.out.println("Nome della cella Investigatore non inizializzato.");
    }
    public void erroreRicercaCellaInvestigatore3(String nome){
        System.out.println("Nome della cella Investigatore non trovato: " + nome);
    }
    public void erroreRicercaCellaAssassino(){
        System.out.println("Numeo della cella Assassino non inizializzato.");
    }

}
