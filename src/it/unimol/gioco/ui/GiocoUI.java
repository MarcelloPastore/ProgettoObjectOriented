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
import it.unimol.gioco.app.saves.GameSaveManager;
import it.unimol.gioco.ui.board.TabelloneUI;
import it.unimol.gioco.ui.player.GiocatoreUI;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GiocoUI {
    private final Tabellone tabellone;
    private Gioco gioco;
    private final StampaErroriUI stampaErroriUI;
    private final Scanner scanner;
    private final GiocatoreUI giocatoreUI;
    private final TabelloneUI tabelloneUI;

    public GiocoUI() {
        this.tabellone = new Tabellone();
        this.gioco = new Gioco();
        this.stampaErroriUI = new StampaErroriUI();
        this.scanner = new Scanner(System.in);
        this.giocatoreUI = new GiocatoreUI(gioco);
        this.tabelloneUI = new TabelloneUI(gioco);
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
        int scelta = scanner.nextInt();
        scanner.nextLine(); // Consuma newline
        inizia(scelta);
    }

    public void inizia(int scelta) {
        switch (scelta) {
            case 1:
                gioco.inizializzaMappa();
                scegliClasse();
                eseguiTurni();
                break;
            case 2:
                caricaPartita();
                break;
            case 3:
                giocatoreUI.stampaUscita();
                break;
            default:
                stampaErroriUI.erroreDiStampa1();
        }
    }

    private void caricaPartita() {
        try {
            // Mostra i salvataggi disponibili
            String[] salvataggi = GameSaveManager.getAvailableSaves();

            if (salvataggi == null || salvataggi.length == 0) {
                System.out.println("Nessun salvataggio disponibile!");
                stampaMenuPrincipale();
                return;
            }

            System.out.println("Salvataggi disponibili:");
            for (int i = 0; i < salvataggi.length; i++) {
                System.out.println((i + 1) + ". " + salvataggi[i]);
            }

            System.out.println("Scegli il salvataggio da caricare:");
            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma newline

            if (scelta < 1 || scelta > salvataggi.length) {
                System.out.println("Selezione non valida!");
                stampaMenuPrincipale();
                return;
            }

            // Carica il gioco
            gioco = GameSaveManager.loadGame(salvataggi[scelta - 1]);

            // Continua con i turni
            eseguiTurni();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore nel caricamento: " + e.getMessage());
            stampaMenuPrincipale();
        }
    }

    private void salvaPartita() {
        try {
            System.out.println("Inserisci un nome per il salvataggio:");
            String nomeSalvataggio = scanner.nextLine();

            GameSaveManager.saveGame(gioco, nomeSalvataggio);
            System.out.println("Partita salvata con successo!");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    public String sceltaNomeGiocatore() {
        giocatoreUI.stampaRichiestaNomeGiocatore();
        return scanner.nextLine();
    }

    public int sceltaClasseGiocatore() {
        giocatoreUI.scegliClasse1();
        return scanner.nextInt();
    }

    public int sceltaClasseAssassino() {
        giocatoreUI.stampaRichiestaCellaInizialeAssassino();
        return scanner.nextInt();
    }

    public String sceltaClasseInvestigatore() {
        giocatoreUI.stampaRichiestaCellaInizialeInvestigatore();
        return scanner.nextLine();
    }

    public int[] sceltaObiettivi() {
        int[] obiettivi = new int[4];
        giocatoreUI.stampaScegliObiettivi1();
        for (int i = 0; i < obiettivi.length; i++) {
            giocatoreUI.stampaScegliObiettivi2();
            obiettivi[i] = scanner.nextInt();
        }
        return obiettivi;
    }

    public int sceltaMenuAssassino(Assassino giocatore) {
        giocatoreUI.menuAssassino(giocatore);
        return scanner.nextInt();
    }

    public int sceltaMenuInvestigatore(Investigatore giocatore) {
        tabelloneUI.menuInvestigatore(giocatore);
        return scanner.nextInt();
    }

    public void stampaMessaggioInizializzazione() {
        System.out.println("Inizializzazione del gioco...");
        stampaMenuPrincipale();
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
                        giocatoreUI.stampaPosizione(giocatore);
                        giocatoreUI.stampaObiettivi();
                    } catch (MaxAssassinException e) {
                        System.out.println("Errore: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Errore: inserisci un numero valido per la cella.");
                    }
                } else if (sceltaCella == 2) {
                    String nome = sceltaNomeGiocatore();
                    String nomeCella = sceltaClasseInvestigatore();
                    Giocatore giocatore = gioco.inserisciInvestigatore(nome, nomeCella);
                    giocatoreUI.stampaPosizione(giocatore);
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
        while (!fine) {
            List<Giocatore> giocatori = gioco.getListaGiocatori();

            for (Giocatore giocatore : giocatori) {
                giocatoreUI.stampaTurno();
                turnoGiocatore(giocatore);
                if (gioco.getListaObiettivi().isEmpty()) {
                    fine = true;
                }
                fine = controlloFineGioco(giocatore);
            }
        }
        giocatoreUI.stampaFineGioco();
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

    public void turnoGiocatore(Giocatore giocatore) {
        if (giocatore instanceof Investigatore) {
            int scelta = sceltaMenuInvestigatore((Investigatore) giocatore);
            scanner.nextLine();

            // Aggiungi l'opzione di salvataggio
            if (scelta == 5) {
                salvaPartita();
                return;
            }

            turnoInvestigatore(scelta, (Investigatore) giocatore);
            gioco.setTurnoCorrente(gioco.getTurnoCorrente() + 1);
        } else if (giocatore instanceof Assassino) {
            giocatoreUI.stampaTurnoGiocatore(giocatore);
            int scelta = sceltaMenuAssassino((Assassino) giocatore);

            // Aggiungi l'opzione di salvataggio
            if (scelta == 3) {
                salvaPartita();
                return;
            }

            turnoAssassino(scelta, (Assassino) giocatore);
            gioco.setTurnoCorrente(gioco.getTurnoCorrente() + 1);
        }
    }

    public void turnoAssassino(int scelta, Assassino giocatore) {
        try {
            if (scelta == 1) {
                List<CellaAssassino> celleVicine = tabellone.getCelleAssassineVicine(giocatore.getPosizione());
                if (celleVicine.isEmpty()) {
                    throw new IllegalStateException("Non ci sono mosse disponibili!");
                }

                tabelloneUI.stampaCelleVicine(celleVicine);
                int nuovaPosizione = muoviAssassino(giocatore);

                if (!celleVicine.contains(tabellone.getCellaAssassino(nuovaPosizione - 1))) {
                    throw new IllegalArgumentException("Mossa non valida! Scegli una delle celle disponibili.");
                }

                gioco.muoviAssassino(giocatore, nuovaPosizione);
                giocatoreUI.stampaPosizione(giocatore);
                giocatoreUI.stampaStatoMosse();

                if (gioco.getListaObiettivi().contains(tabellone.getCellaAssassino(nuovaPosizione - 1))) {
                    System.out.println("🎯 Hai raggiunto un obiettivo!");
                    giocatoreUI.stampaObiettiviRimanenti();
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

    public void turnoInvestigatore(int scelta, Investigatore giocatore) {
        try {
            boolean turnoContinua = true;
            boolean mossoInQuestoTurno = false;
            boolean indizioInQuestoTurno = false;

            while (turnoContinua) {
                if (scelta == 1 && !mossoInQuestoTurno) {
                    // Gestione movimento
                    List<CellaInvestigatore> celleVicine = tabellone.getCelleInvestigatoriVicine(giocatore.getPosizione());
                    tabelloneUI.stampaCelleVicineInvestigatore(celleVicine);

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
                    giocatoreUI.stampaPosizione(giocatore);
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
                    giocatoreUI.menuInvestigatoreAzioniRimanenti(mossoInQuestoTurno, indizioInQuestoTurno);
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
            giocatoreUI.stampaPercorsoAssassino();
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

}