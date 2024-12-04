package it.unimol.gioco.ui.player;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.player.Assassino;
import it.unimol.gioco.app.player.Giocatore;

import java.util.List;

public class GiocatoreUI {
    private Gioco gioco;

    public GiocatoreUI(Gioco gioco) {
        this.gioco = gioco;
    }

    public void stampaObiettivi() {
        System.out.println(
                "Obiettivi assassino: " +
                        gioco.getListaObiettivi().toString()
        );
    }

    public void stampaPercorsoAssassino() {
        try {
            List<CellaAssassino> percorso = gioco.getPercorsoAssassino();
            System.out.println("\n🗺️ Percorso dell'assassino:");
            System.out.println("--------------------------------");
            for (int i = 0; i < percorso.size(); i++) {
                System.out.println((i + 1) + ". " + percorso.get(i));
            }
            System.out.println("--------------------------------");
        } catch (Exception e) {
            System.out.println("Errore nella visualizzazione del percorso: " + e.getMessage());
        }
    }

    public void stampaPosizione(Giocatore giocatore) {
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

    public void scegliClasse1() {
        System.out.println("Scegli la classe: ");
        System.out.println("1. Assassino");
        System.out.println("2. Investigatore");
        System.out.println("3. Esci");
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

    public void menuAssassino(Assassino giocatore) {
        System.out.println("Tocca all' Assassino " + giocatore.getNome());
        System.out.println("Scegli la tua azione: ");
        System.out.println("1. Muoviti  ");
        System.out.println("2. Arrenditi ");
        System.out.println("3. Salva Partita");
    }

    public void menuInvestigatoreAzioniRimanenti(boolean mosso, boolean indizioRichiesto) {
        System.out.println("\nAzioni disponibili:");
        if (!mosso) {
            System.out.println("1. Muoviti");
        }
        if (!indizioRichiesto) {
            System.out.println("2. Chiedi indizio");
        }
        System.out.println("3. Tenta un arresto");
        System.out.println("4. Termina il turno");
    }

    public void stampaObiettiviRimanenti() {
        System.out.println("Obiettivi rimanenti: " + gioco.getListaObiettivi().size());
        stampaObiettivi();
    }

    public void stampaStatoMosse() {
        System.out.println("Mosse rimanenti: " + gioco.getMosseRimanentiAssassino());
    }

    public void stampaTurno() {
        System.out.println("Turno: " + gioco.getTurnoCorrente());
    }
}
