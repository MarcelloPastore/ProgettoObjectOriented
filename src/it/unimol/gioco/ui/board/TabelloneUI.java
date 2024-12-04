package it.unimol.gioco.ui.board;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.board.CellaInvestigatore;
import it.unimol.gioco.app.player.Investigatore;

import java.util.List;

public class TabelloneUI {
    private Gioco gioco;

    public TabelloneUI(Gioco gioco) {
        this.gioco = gioco;
    }

    public void menuInvestigatore(Investigatore giocatore) {
        System.out.println("Tocca all' Investigatore " + giocatore.getNome());
        System.out.println("Scegli la tua azione: ");
        System.out.println("1. Muoviti ");
        System.out.println("2. Chiedi indizio");
        System.out.println("3. Tenta un arresto");
        System.out.println("4. Termina il turno");
        System.out.println("5. Salva Partita");
    }

    public void stampaCelleVicineInvestigatore(List<CellaInvestigatore> vicini) {
        try {
            System.out.println("\n📍 Posizioni disponibili per il movimento:");
            System.out.println("--------------------------------");
            for (CellaInvestigatore cella : vicini) {
                System.out.println("Cella: " + cella.getNumero());
                System.out.println("--------------------------------");
            }
            if (vicini.isEmpty()) {
                System.out.println("Non ci sono celle disponibili per il movimento!");
            }
        } catch (Exception e) {
            System.out.println("Errore nella visualizzazione delle celle vicine: " + e.getMessage());
        }
    }

    public void stampaCelleVicine(List<CellaAssassino> vicini) {
        try {
            System.out.println("\n📍 Posizioni disponibili per il movimento:");
            System.out.println("--------------------------------");
            for (CellaAssassino cella : vicini) {
                System.out.println("Cella numero: " + (cella.getNumero()));

                if (gioco.getListaObiettivi().contains(cella)) {
                    System.out.println("⭐ Questa è una cella obiettivo!");
                }
                System.out.println("--------------------------------");
            }
            if (vicini.isEmpty()) {
                System.out.println("Non ci sono celle disponibili per il movimento!");
            }
        } catch (Exception e) {
            System.out.println("Errore nella visualizzazione delle celle vicine: " + e.getMessage());
        }
    }
}

