package it.unimol.gioco.app;


import it.unimol.gioco.app.board.Cella;
import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.board.CellaInvestigatore;
import it.unimol.gioco.app.board.Tabellone;
import it.unimol.gioco.app.player.Assassino;
import it.unimol.gioco.app.player.Giocatore;
import java.util.Scanner;

import it.unimol.gioco.app.player.Investigatore;
import it.unimol.gioco.ui.GestoreMenu;
import it.unimol.gioco.ui.GiocoUI;
import it.unimol.gioco.ui.StampaErroriUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Gioco {
    private Tabellone tabellone;
    private List<Giocatore> giocatori;
    private int turnoCorrente;
    private GiocoUI giocoUI = new GiocoUI();
    private StampaErroriUI erroreUI = new StampaErroriUI();
    private GestoreMenu menuUI = new GestoreMenu();

    public Gioco() {
        this.tabellone = new Tabellone();
        this.giocatori = new ArrayList<Giocatore>();
        this.turnoCorrente = 0;
    }

    public void inizializzaGioco(){
        tabellone.creagrafo();



        for (Giocatore giocatore : giocatori){
            giocatore.getPosizione();
            giocoUI.stampaPosizioneIniziale(giocatore);
        }
    }

    public void aggiugniGiocatoreAssassino(String nome, CellaAssassino posizioneIniziale) {
        List<CellaAssassino> obiettivi = createListaObiettivi();
        Assassino giocatore = new Assassino(nome, posizioneIniziale, obiettivi);
        giocatori.add(giocatore);
        giocatore.setPosizione(posizioneIniziale);
        (giocatore).setObiettivi(obiettivi);
        giocoUI.stampaPosizioneIniziale(giocatore);
    }
    public void aggiugniGiocatoreInvestigatore(String nome, CellaInvestigatore posizioneIniziale) {
        Investigatore giocatore = new Investigatore(nome, posizioneIniziale, false);
        giocatori.add(giocatore);
        giocatore.setPosizione(posizioneIniziale);
        giocoUI.stampaPosizioneIniziale(giocatore);
    }

    private List<CellaAssassino> createListaObiettivi() {
        List<CellaAssassino> list = new ArrayList<>();
        int[] obiettivi = menuUI.sceltaObiettivi();
        for (int numero : obiettivi) {
            // Controlla se l'accesso a getCellaAssassino è valido
            if (numero > 0 && numero <= tabellone.getMaxCelleAssassino()) {
                CellaAssassino cella = tabellone.getCellaAssassino(numero - 1);
                list.add(cella);
            } else {
                System.out.println("Numero obiettivo non valido: " + numero);
            }
        }
        return list;
    }

    public void inizia() {
        int scelta = menuUI.menuPrincipale();
        if (scelta == 1) {
            giocoUI.stampaMessaggioIniziale();
            inizializzaGioco();
            inserisciGiocatore();
            eseguiTurni();

        } else giocoUI.stampaUscita();
    }

    private void eseguiTurni() {
        boolean fine = false;
        while(!fine) {
            Giocatore giocatore = giocatori.get(turnoCorrente);
            giocoUI.stampaTurnoGiocatore(giocatore);
            turnoGiocatore(giocatore);
            controlloFineGioco();
            turnoCorrente = (turnoCorrente + 1) % giocatori.size();
        }
        //giocoUI.stampaFineGioco();
    }

    private void controlloFineGioco() {

    }

    private void turnoGiocatore(Giocatore giocatore) {
        if (giocatore instanceof Assassino) {
            //istruzioni
            //System.out.println("Assassinoooooo");
        } else if (giocatore instanceof Investigatore) {
            //istruzioni
            //System.out.println("Investigatoreeeeeeeeeeee");
        }
    }

    private boolean verificaFineGioco() {

        return false;
    }

    public void inserisciGiocatore(){
        boolean flag = true;
        while (flag && (giocatori.size() <= 4)) {
            int sceltaCella = menuUI.sceltaClasseGiocatore();
            if (sceltaCella == 1) {
                CellaAssassino posizioneIniziale;
                String nome = menuUI.sceltaNomeGiocatore();
                int numeroCella = menuUI.sceltaClasseAssassino();
                posizioneIniziale = tabellone.getCellaAssassino(numeroCella - 1);
                aggiugniGiocatoreAssassino(nome, posizioneIniziale);
            } else if (sceltaCella == 2) {
                CellaInvestigatore posizioneIniziale;
                String nome = menuUI.sceltaNomeGiocatore();
                String nomeCella = menuUI.sceltaClasseInvestigatore();
                posizioneIniziale = tabellone.getCellaInvestigatore(nomeCella);
                aggiugniGiocatoreInvestigatore(nome, posizioneIniziale);
            } else if (sceltaCella == 3) {
                flag = false;
            } else {
                erroreUI.erroreDiStampa1();
            }
        }
    }



}
