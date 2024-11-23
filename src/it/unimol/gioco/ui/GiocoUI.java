package it.unimol.gioco.ui;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.board.CellaInvestigatore;
import it.unimol.gioco.app.board.Tabellone;
import it.unimol.gioco.app.exceptions.MaxAssassinException;
import it.unimol.gioco.app.exceptions.MosseMassimeRaggiunte;
import it.unimol.gioco.app.player.Assassino;
import it.unimol.gioco.app.player.Giocatore;
import it.unimol.gioco.app.player.Investigatore;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GiocoUI {
    private final Tabellone tabellone;
    private final Gioco gioco;
    private final StampaErroriUI stampaErroriUI;
    private final Scanner scanner;

    public GiocoUI() {
        this.tabellone = new Tabellone();
        this.gioco = new Gioco();
        this.stampaErroriUI = new StampaErroriUI();
        this.scanner = new Scanner(System.in);
    }

    public void stampaMenuPrincipale() {
        System.out.println("----------------------------------------------- ");
        System.out.println("---------------WHITEHALL MISTERY--------------- ");
        System.out.println("----------------------------------------------- ");
        System.out.println("Benvenuti a tutti voi!");
        System.out.println("Allora vogliamo cominciare il gioco?");
        System.out.println("Fai la tua scelta: ");
        System.out.println("1. INIZIA ");
        System.out.println("2. CARICA ");
        System.out.println("3. ESCI ");
        System.out.println("------------------------------------------------");
        inizia(scanner.nextInt());
    }

    public String sceltaNomeGiocatore() {
        stampaRichiestaNomeGiocatore();
        return scanner.nextLine();
    }
    public int sceltaClasseGiocatore() {
        scegliClasse1();
        return scanner.nextInt();
    }

    public int sceltaClasseAssassino() {
        stampaRichiestaCellaInizialeAssassino();
        return scanner.nextInt();
    }

    public String sceltaClasseInvestigatore() {
        stampaRichiestaCellaInizialeInvestigatore();
        return scanner.nextLine();
    }

    public int[] sceltaObiettivi(){
        int[] obiettivi = new int[4];
        stampaScegliObiettivi1();
        for (int i = 0; i < obiettivi.length; i++) {
            stampaScegliObiettivi2();
            obiettivi[i] = scanner.nextInt();
        }
        return obiettivi;
    }

    public int sceltaMenuAssassino(Assassino giocatore) {
        menuAssassino(giocatore);
        return scanner.nextInt();
    }

    public int sceltaMenuInvestigatore(Investigatore giocatore) {
        menuInvestigatore(giocatore);
        return scanner.nextInt();
    }

//    public int movimentoAssassino(List<CellaAssassino> vicini) {
//        stampaCelleVicine(vicini);
//        return scanner.nextInt();
//    }

    public void stampaMessaggioInizializzazione() {
        System.out.println("Inizializzazione del gioco...");
        stampaMenuPrincipale();
    }

    public void inizia(int scelta) {
        if (scelta == 1) {
            gioco.inizializzaMappa();
            scegliClasse();
            eseguiTurni();
        }
        else stampaUscita();
    }

    public void scegliClasse() {
        boolean flag = true;

        while (flag) {
            try {
                int sceltaCella = sceltaClasseGiocatore();
                scanner.nextLine();
                if (sceltaCella == 1) {
                    try {
                        String nome = sceltaNomeGiocatore();
                        int numeroCella = sceltaClasseAssassino();
                        scanner.nextLine();
                        gioco.addObiettivo(sceltaObiettivi());
                        Giocatore giocatore = gioco.inserisciAssassino(nome, numeroCella);
                        stampaPosizione(giocatore);
                        stampaObiettivi();
                    } catch (MaxAssassinException e) {
                        System.out.println("Errore: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Errore: inserisci un numero valido per la cella.");
                    }
                } else if (sceltaCella == 2) {
                    String nome = sceltaNomeGiocatore();
                    String nomeCella = sceltaClasseInvestigatore();
                    Giocatore giocatore = gioco.inserisciInvestigatore(nome, nomeCella);
                    stampaPosizione(giocatore);
                } else if (sceltaCella == 3) {
                    flag = false;
                } else {
                    stampaErroriUI.erroreDiStampa1();
                }
            } catch (InputMismatchException e) {
                System.out.println("Errore: inserisci un valore numerico valido.");
            }
        }
    }

    public void eseguiTurni() {
        boolean fine = false;
        while(!fine) {
            List<Giocatore> giocatori= gioco.getListaGiocatori();

            for (Giocatore giocatore : giocatori) {
                stampaTurno();
                turnoGiocatore(giocatore);
                if (gioco.getListaObiettivi().isEmpty()) {
                    fine = true;
                }
                fine = controlloFineGioco(giocatore);
            }
        }
        stampaFineGioco();
    }

    public boolean controlloFineGioco(Giocatore giocatore) {
        if (giocatore instanceof Assassino) {
            // Vittoria Assassino
            return ((Assassino) giocatore).getObiettivi().isEmpty();
        } else if (giocatore instanceof Investigatore) {
            // Vittoria Investigatori
            return ((Investigatore) giocatore).getArresto() || gioco.isAssassinoSconfitto();
        }
        return false;
    }

    private void stampaTurno() {
        System.out.println("Turno: " + gioco.getTurnoCorrente());
    }

    public void turnoGiocatore(Giocatore giocatore) {
        if(giocatore instanceof Investigatore){
            int scelta = sceltaMenuInvestigatore((Investigatore) giocatore);
            scanner.nextLine();
            turnoInvestigatore(scelta,(Investigatore) giocatore);
            gioco.setTurnoCorrente();
        } else if (giocatore instanceof Assassino){
            stampaTurnoGiocatore(giocatore);
            int scelta = sceltaMenuAssassino((Assassino) giocatore);
            turnoAssassino(scelta,(Assassino) giocatore);
            gioco.setTurnoCorrente();
        }
    }

    public void turnoAssassino(int scelta, Assassino giocatore) {
        try {
            if (scelta == 1) {
                List<CellaAssassino> celleVicine = tabellone.getCelleAssassineVicine(giocatore.getPosizione());
                if (celleVicine.isEmpty()) {
                    throw new IllegalStateException("Non ci sono mosse disponibili!");
                }

                stampaCelleVicine(celleVicine);
                int nuovaPosizione = muoviAssassino(giocatore);

                if (!celleVicine.contains(tabellone.getCellaAssassino(nuovaPosizione - 1))) {
                    throw new IllegalArgumentException("Mossa non valida! Scegli una delle celle disponibili.");
                }

                gioco.muoviAssassino(giocatore, nuovaPosizione);
                stampaPosizione(giocatore);
                stampaStatoMosse();

                if (gioco.getListaObiettivi().contains(tabellone.getCellaAssassino(nuovaPosizione - 1))) {
                    System.out.println("🎯 Hai raggiunto un obiettivo!");
                    stampaObiettiviRimanenti();
                }

            } else if (scelta == 2) {
                confermaResa();
            } else {
                throw new IllegalArgumentException("Scelta non valida!");
            }
        } catch (MosseMassimeRaggiunte e) {
            System.out.println("⚠️ " + e.getMessage());
            System.out.println("Gli investigatori hanno vinto!");
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
            System.out.println("Riprova con una mossa valida.");
        } catch (IllegalStateException e) {
            System.out.println("Errore di stato del gioco: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore imprevisto: " + e.getMessage());
        }
    }

    private void confermaResa() {
        System.out.println("Sei sicuro di volerti arrendere? (s/n)");
        String risposta = scanner.next().toLowerCase();
        if (risposta.equals("s")) {
            System.out.println("L'assassino si è arreso! Gli investigatori hanno vinto! 🎉");
            gioco.getListaObiettivi().clear();
        }
    }

    private void stampaStatoMosse() {
        System.out.println("Mosse rimanenti: " + gioco.getMosseRimanentiAssassino());
    }

    private void stampaObiettiviRimanenti() {
        System.out.println("Obiettivi rimanenti: " + gioco.getListaObiettivi().size());
        stampaObiettivi();
    }

    public void turnoInvestigatore(int scelta, Investigatore giocatore) {
        try {
            boolean turnoContinua = true;
            boolean mossoInQuestoTurno = false;
            boolean indizioInQuestoTurno = false;

            while(turnoContinua) {
                if (scelta == 1 && !mossoInQuestoTurno) {
                    // Gestione movimento
                    List<CellaInvestigatore> celleVicine = tabellone.getCelleInvestigatoriVicine(giocatore.getPosizione());
                    stampaCelleVicineInvestigatore(celleVicine);

                    String nuovaPosizione = muoviInvestigatore(giocatore);
                    CellaInvestigatore nuovaCella = tabellone.getCellaInvestigatore(nuovaPosizione);

                    if (nuovaCella == null) {
                        throw new IllegalArgumentException("Cella non valida!");
                    }
                    if (!celleVicine.contains(nuovaCella)) {
                        throw new IllegalArgumentException("Non puoi muoverti in questa cella!");
                    }

                    giocatore.setPosizione(nuovaCella);
                    gioco.muoviInvestigatore(giocatore, nuovaPosizione);
                    stampaPosizione(giocatore);
                    mossoInQuestoTurno = true;

                } else if (scelta == 2 && !indizioInQuestoTurno) {
                    // Gestione richiesta indizio
                    System.out.println("Inserisci il numero della cella da controllare: ");
                    int numeroCella = scanner.nextInt();

                    if (numeroCella < 1 || numeroCella > tabellone.getMaxCelleAssassino()) {
                        throw new IllegalArgumentException("Numero cella non valido!");
                    }

                    CellaAssassino cellaIndizio = tabellone.getCellaAssassino(numeroCella - 1);
                    if (gioco.verificaIndizio(cellaIndizio)) {
                        System.out.println("🔍 INDIZIO TROVATO: L'assassino è passato da questa cella!");
                    } else {
                        System.out.println("❌ L'assassino non è passato da questa cella.");
                    }
                    indizioInQuestoTurno = true;

                } else if (scelta == 3) {
                    // Gestione arresto
                    System.out.println("Inserisci il numero della cella dove pensi si trovi l'assassino: ");
                    int numeroCella = scanner.nextInt();

                    if (numeroCella < 1 || numeroCella > tabellone.getMaxCelleAssassino()) {
                        throw new IllegalArgumentException("Numero cella non valido!");
                    }

                    Assassino assassino = trovaAssassino();
                    if (assassino == null) {
                        throw new IllegalStateException("Nessun assassino presente nel gioco!");
                    }

                    verificaArresto(giocatore, assassino, numeroCella);
                    turnoContinua = false;

                } else if (scelta == 4) {
                    // Termina il turno
                    turnoContinua = false;
                } else {
                    throw new IllegalArgumentException("Scelta non valida!");
                }

                // Se non ha ancora terminato il turno, chiedi la prossima azione
                if (turnoContinua) {
                    menuInvestigatoreAzioniRimanenti(mossoInQuestoTurno, indizioInQuestoTurno);
                    scelta = scanner.nextInt();
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Errore: " + e.getMessage());
            System.out.println("Riprova con un'azione valida.");
        } catch (IllegalStateException e) {
            System.out.println("Errore di stato del gioco: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore imprevisto: " + e.getMessage());
        }
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

    private Assassino trovaAssassino() {
        for (Giocatore giocatore : gioco.getListaGiocatori()) {
            if (giocatore instanceof Assassino) {
                return (Assassino) giocatore;
            }
        }
        return null;
    }

    private void verificaArresto(Investigatore investigatore, Assassino assassino, int numeroCella) {
        // Verifica se la cella scelta corrisponde alla posizione attuale dell'assassino
        if (assassino.getPosizione().equals(tabellone.getCellaAssassino(numeroCella - 1))) {
            System.out.println("🎉 Congratulazioni! Hai arrestato l'assassino!");
            investigatore.setArresto(true);
            stampaPercorsoAssassino();
        } else {
            System.out.println("❌ Tentativo di arresto fallito! L'assassino non si trova in questa cella.");
            System.out.println("L'assassino può continuare a muoversi...");
        }
    }

    public int muoviAssassino(Giocatore giocatore) {
            System.out.println("Inserisci la nuova posizione tra quelle possibili: ");
            return scanner.nextInt();
    }

    public String muoviInvestigatore(Giocatore giocatore) {
        System.out.println("Inserisci la nuova posizione tra quelle possibili: ");
        return scanner.nextLine();
    }

    private void stampaObiettivi() {
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

    public void scegliClasse1(){
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
        System.out.println("Tocca all'Assassino " + giocatore.getNome());
        System.out.println("Scegli la tua azione: ");
        System.out.println("1. Muoviti  ");
        System.out.println("2. Arrenditi ");

    }

    public void menuInvestigatore(Investigatore giocatore) {
        System.out.println("Tocca all'Investigatore " + giocatore.getNome());
        System.out.println("Scegli la tua azione: ");
        System.out.println("1. Muoviti ");
        System.out.println("2. Chiedi indizio");
        System.out.println("3. Tenta un arresto");
        System.out.println("4. Termina il turno");
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
    }}

