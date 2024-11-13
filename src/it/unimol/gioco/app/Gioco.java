package it.unimol.gioco.app;


import it.unimol.gioco.app.board.Cella;
import it.unimol.gioco.app.board.Tabellone;
import it.unimol.gioco.app.player.Giocatore;
import java.util.Scanner;
import it.unimol.gioco.ui.GiocoUI;
import it.unimol.gioco.ui.StampaErroriUI;

import java.util.ArrayList;
import java.util.List;

public class Gioco {
    private Tabellone tabellone;
    private List<Giocatore> giocatori;
    private int turnoCorrente;
    private GiocoUI giocoUI = new GiocoUI();
    private StampaErroriUI erroreUI = new StampaErroriUI();


    public Gioco() {
        this.tabellone = new Tabellone();
        this.giocatori = new ArrayList<Giocatore>();
        this.turnoCorrente = 0;
    }

    public void inizializzaGioco(){
        giocoUI.stampaMessaggioInizializzazione();
        tabellone.creagrafo();


        for (Giocatore giocatore : giocatori){
            giocatore.getPosizione();

        }
    }

    public void aggiugniGiocatore(String nome, Cella posizioneIniziale) {
    Giocatore giocatore = new Giocatore(nome, posizioneIniziale);
        giocatori.add(giocatore);
        giocoUI.stampaPosizioneIniziale(giocatore);
    }

    public void inizia() {
        giocoUI.stampaMessaggioIniziale();

        inizializzaGioco();

        eseguiTurni();
    }

    private void eseguiTurni() {
        while(true) {
            Giocatore giocatore = giocatori.get(turnoCorrente);
            giocoUI.stampaTurnoGiocatore(giocatore);

            turnoCorrente++;
            break;
        }
        giocoUI.stampaFineGioco();
    }

    private boolean verificaFineGioco() {

        return false;
    }

    public Tabellone getTabellone() {
        return this.tabellone;
    }

    public void inserisciGiocatore(){
        Scanner scanner = new Scanner(System.in);

        giocoUI.stampaRichiestaNomeGiocatore();
        String nome = scanner.nextLine();

        giocoUI.scegliClasse1();
        String tipoCella = scanner.nextLine();

        Cella posizioneIniziale = null;
        if (tipoCella.equalsIgnoreCase("Assassino")) {

            giocoUI.stampaRichiestaCellaInizialeAssassino();
            int numeroCella = scanner.nextInt();
            posizioneIniziale = tabellone.getCellaAssassino(numeroCella - 1);
        } else if (tipoCella.equalsIgnoreCase("Investigatore")) {
            giocoUI.stampaRichiestaCellaInizialeInvestigatore();
            String numeroCella = scanner.nextLine();
            posizioneIniziale = tabellone.getCellaInvestigatore(numeroCella);
        } else {
            erroreUI.erroreDiStampa1();
            return;
        }
        aggiugniGiocatore(nome, posizioneIniziale);
    }
}
