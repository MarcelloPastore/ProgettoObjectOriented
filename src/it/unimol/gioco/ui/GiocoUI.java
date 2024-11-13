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
        System.out.println("Ricordando che puoi solo scegliere le Celle Bianche per iniziare\n" +
                "Inserisci il numero della cella Assassino da cui vuoi partire: ");
    }
    public void stampaRichiestaCellaInizialeInvestigatore() {
        System.out.println("Ricordando che puoi solo scegliere le Celle Gialle per iniziare\n" +
                "Inserisci il nome della Cella Investigatore da cui vuoi partire: ");
    }

    public void scegliClasse1(){
        System.out.println("Scegli la classe: ");
        System.out.println("1. Assassino");
        System.out.println("2. Investigatore");
        System.out.println("3. Esci");
    }

    public void classe1(){
        System.out.println("Hai scelto Assassino!");
    }

    public void classe2(){
        System.out.println("Hai scelto Investigatore!");
    }

    public void stampaMenuPrincipale() {
        System.out.println("----------------------------------------------- ");
        System.out.println("---------------WHITEHALL MISTERY--------------- ");
        System.out.println("----------------------------------------------- ");
        System.out.println("Benvenuti a tutti voi!");
        System.out.println("Allora vogliamo cominciare il gioco?");
        System.out.println("Fai la tua scelta: ");
        System.out.println("1. INIZIA ");
        System.out.println("2. ESCI ");
        System.out.println("------------------------------------------------");
    }

    public void stampaUscita() {
        System.out.println("Grazie per aver giocato con noi! Alla prossima!");
        System.out.println("----------------------------------------------- ");
        System.out.println("---------------WHITEHALL MISTERY--------------- ");
        System.out.println("----------------------------------------------- ");
    }

    public void stampaScegliObiettivi1() {
        System.out.println("L'assassino deve scegliere 4 obiettivi in 4 quadranti diversi per iniziare!");
    }

    public void stampaScegliObiettivi2() {
        System.out.println("Scegli il numero della cella obiettivo: ");
    }
    // Aggiungi altri metodi di stampa secondo necessità
}

