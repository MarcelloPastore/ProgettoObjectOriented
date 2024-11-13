package it.unimol.gioco.ui;

import it.unimol.gioco.app.board.Cella;
import it.unimol.gioco.app.player.Giocatore;

public class GiocoUI {

    public GiocoUI() {}

    public void stampaMessaggioIniziale() {
        System.out.println("Inizio del gioco!");
    }

    public void stampaMessaggioInizializzazione() {
        System.out.println("Inizializzazione del gioco...");
    }

    public void stampaPosizioneIniziale(Giocatore giocatore) {
        System.out.println(giocatore.getNome() + " è stato posizionato su " + giocatore.getPosizione());
    }

    public void stampaTurnoGiocatore(Giocatore giocatore) {
        System.out.println("Turno di: " + giocatore.getNome());
    }

    public void stampaFineGioco() {
        System.out.println("Il gioco è terminato!");
    }

    public void stampaRichiestaNomeGiocatore() {
        System.out.println("Inserisci il nome del giocatore: ");
    }
    public void stampaRichiestaCellaInizialeAssassino() {
        System.out.println("Ricordando che puoi solo scegliere le Celle Bianche\n" +
                "Inserisci il numero della cella Assassino da cui vuoi partire: ");
    }
    public void stampaRichiestaCellaInizialeInvestigatore() {
        System.out.println("Ricordando che puoi solo scegliere le Celle Gialle\n" +
                "Inserisci il nome della Cella Investigatore da cui vuoi partire: ");
    }

    public void scegliClasse1(){
        System.out.println("Scegli la classe, sei Assassino o Investigatore?");
    }

    public void scegliClasse2(){
        System.out.println("Hai scelto Assassino!");
    }

    public void scegliClasse3(){
        System.out.println("Hai scelto Investigatore!");
    }



    // Aggiungi altri metodi di stampa secondo necessità
}

