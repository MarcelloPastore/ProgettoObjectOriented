package it.unimol.gioco.app;



import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.board.CellaInvestigatore;
import it.unimol.gioco.app.board.Tabellone;
import it.unimol.gioco.app.player.Assassino;
import it.unimol.gioco.app.player.Giocatore;
import it.unimol.gioco.app.player.Investigatore;
import it.unimol.gioco.app.exceptions.MaxAssassinException;
import it.unimol.gioco.app.exceptions.MosseMassimeRaggiunte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Gioco implements Serializable {
    private final Tabellone tabellone;
    private boolean partitaFinita;
    private Giocatore vincitore;
    private final List<Giocatore> giocatori;
    private final List<CellaAssassino> obiettiviAssassino = new ArrayList<>();
    private final List<CellaAssassino> percorsoAssassino = new ArrayList<>();
    private static int turnoCorrente = 0;
    private static final int MAX_MOSSE_ASSASSINO = 15;
    private int mosseAssassino;

    public Gioco() {
        this.tabellone = new Tabellone();
        this.giocatori = new ArrayList<Giocatore>();
    }

    public void setMosseAssassino(int mosse) {
        this.mosseAssassino = mosse;
    }

    public int numeroGiocatori(){
        return this.giocatori.size();
    }

    public List<Giocatore> getListaGiocatori(){
        return this.giocatori;
    }

    public void inizializzaMappa(){
        tabellone.creagrafo();
    }

    private boolean hasAssassino() {
        return giocatori.stream().anyMatch(g -> g instanceof Assassino);
    }

    public Giocatore inserisciAssassino(String nome, int posizione) throws MaxAssassinException {
        if (hasAssassino()) {
            throw new MaxAssassinException("È già presente un assassino nel gioco!");
        }
        CellaAssassino posizioneIniziale = tabellone.getCellaAssassino(posizione - 1);
        return aggiugniGiocatoreAssassino(nome, posizioneIniziale);
    }

    public Giocatore inserisciInvestigatore(String nome, String posizione){
        CellaInvestigatore posizioneIniziale = tabellone.getCellaInvestigatore(posizione);
        return aggiugniGiocatoreInvestigatore(nome, posizioneIniziale);
    }

    public Giocatore aggiugniGiocatoreAssassino(String nome, CellaAssassino posizioneIniziale) {
        Assassino giocatore = new Assassino(nome, posizioneIniziale, obiettiviAssassino);
        giocatori.add(giocatore);
        giocatore.setPosizione(posizioneIniziale);
        giocatore.setObiettivi(obiettiviAssassino);
        percorsoAssassino.add(posizioneIniziale);
        return giocatore;
    }

    public Giocatore aggiugniGiocatoreInvestigatore(String nome, CellaInvestigatore posizioneIniziale) {
        Investigatore giocatore = new Investigatore(nome, posizioneIniziale, false);
        giocatori.add(giocatore);
        giocatore.setPosizione(posizioneIniziale);
        return giocatore;
    }

    public List<CellaAssassino> getListaObiettivi(){
        return obiettiviAssassino;
    }

    public void addObiettivo(int[] obiettivi){
        for (int numero : obiettivi) {
            CellaAssassino cella = tabellone.getCellaAssassino(numero - 1);
            obiettiviAssassino.add(cella);
        }
    }

    public int getTurnoCorrente(){
        return turnoCorrente;
    }

    public void setTurnoCorrente(int turno){
        turnoCorrente = turno;
    }

    public void nextTurn() {
        turnoCorrente += 1;

        if (turnoCorrente >= numeroGiocatori()) {
            turnoCorrente = 0;
        }
    }

    public boolean isPartitaFinita() {
        return partitaFinita;
    }

    public Giocatore getVincitore() {
        return vincitore;
    }

    public void muoviAssassino(Assassino giocatore, int nuovaPosizione) throws MosseMassimeRaggiunte {
        if (mosseAssassino >= MAX_MOSSE_ASSASSINO) {
            throw new MosseMassimeRaggiunte("L'assassino ha raggiunto il limite massimo di " + MAX_MOSSE_ASSASSINO + " mosse!");
        }
        if (isAssassinoCircondato(giocatore)) {
            throw new IllegalStateException("L'assassino è circondato dagli investigatori!");
        }
        if (giocatore.getPosizione().equals(tabellone.getCellaAssassino(nuovaPosizione - 1))) {
            throw new IllegalArgumentException("Non puoi rimanere nella stessa posizione!");
        }
        giocatore.setPosizione(tabellone.getCellaAssassino(nuovaPosizione - 1));
        mosseAssassino++;
        percorsoAssassino.add(tabellone.getCellaAssassino(nuovaPosizione - 1));

        if (obiettiviAssassino.contains(tabellone.getCellaAssassino(nuovaPosizione - 1))) {
            obiettiviAssassino.remove(tabellone.getCellaAssassino(nuovaPosizione - 1));
            mosseAssassino = 0;
        }
    }

    private boolean isAssassinoCircondato(Assassino assassino) {
        List<CellaAssassino> celleVicine = tabellone.getCelleAssassineVicine(assassino.getPosizione());


        List<Investigatore> investigatori = new ArrayList<>();
        for (Giocatore g : giocatori) {
            if (g instanceof Investigatore) {
                investigatori.add((Investigatore) g);
            }
        }


        for (CellaAssassino cella : celleVicine) {
            boolean cellaBloccata = false;
            for (Investigatore inv : investigatori) {
                if (isInvestigatoreVicino(cella, inv.getPosizione())) {
                    cellaBloccata = true;
                    break;
                }
            }
            if (!cellaBloccata) {
                return false;
            }
        }
        return true;
    }

    private boolean isInvestigatoreVicino(CellaAssassino cellaAssassino, CellaInvestigatore posizione) {
        List<CellaInvestigatore> celleVicine = tabellone.getCelleInvestigatoriVicine(posizione);
        for (CellaInvestigatore cella : celleVicine) {
            if (cella.equals(posizione)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificaIndizio(CellaAssassino cella) {
        return percorsoAssassino.contains(cella);
    }

    public boolean tentaArresto(Investigatore investigatore, CellaAssassino cellaTarget) {
        // Verifica se la cella target è adiacente all'investigatore
        List<CellaAssassino> celleAssassinoVicine = tabellone.getCelleAssassinoAdiacenti(investigatore.getPosizione());
        boolean cellaAdiacente = false;

        for (CellaAssassino cella : celleAssassinoVicine) {
            if (cella.getNumero().equals(cellaTarget.getNumero())) {
                cellaAdiacente = true;
                break;
            }
        }

        if (!cellaAdiacente) {
            return false; // La cella target non è adiacente all'investigatore
        }

        // Verifica se l'assassino è nella cella target
        for (Giocatore g : giocatori) {
            if (g instanceof Assassino assassino) {
                boolean arrestoRiuscito = assassino.getPosizione().equals(cellaTarget);
                if (arrestoRiuscito) {
                    // Impostiamo lo stato di gioco come terminato
                    partitaFinita = true;
                    vincitore = investigatore;
                }
                return arrestoRiuscito;
            }
        }
        return false;
    }

    public List<CellaAssassino> getCelleVicineVisibili(Assassino assassino) {
        List<CellaAssassino> celleVicine = tabellone.getCelleAssassineVicine(assassino.getPosizione());
        List<CellaAssassino> celleVisibili = new ArrayList<>();

        for (CellaAssassino cella : celleVicine) {
            boolean investigatorePresenteNelPassaggio = false;
            for (Giocatore g : giocatori) {
                if (g instanceof Investigatore) {
                    if (isInvestigatoreVicino(cella, ((Investigatore) g).getPosizione())) {
                        investigatorePresenteNelPassaggio = true;
                        break;
                    }
                }
            }
            if (!investigatorePresenteNelPassaggio) {
                celleVisibili.add(cella);
            }
        }
        return celleVisibili;
    }

    public List<String> getCelleVicineVisibiliString(Assassino assassino) {
        List<CellaAssassino> celleVicine = tabellone.getCelleAssassineVicine(assassino.getPosizione());
        List<String> celleVisibili = new ArrayList<>();

        for (CellaAssassino cella : celleVicine) {
            boolean investigatorePresenteNelPassaggio = false;
            for (Giocatore g : giocatori) {
                if (g instanceof Investigatore) {
                    if (isInvestigatoreVicino(cella, ((Investigatore) g).getPosizione())) {
                        investigatorePresenteNelPassaggio = true;
                        break;
                    }
                }
            }
            if (!investigatorePresenteNelPassaggio) {
                celleVisibili.add(cella.getNumero());
            }
        }
        return celleVisibili;
    }

    public List<CellaAssassino> getPercorsoAssassino() {
        return new ArrayList<>(percorsoAssassino);
    }

    public void muoviInvestigatore(Investigatore giocatore, String nuovaPosizione) {
        giocatore.setPosizione(tabellone.getCellaInvestigatore(nuovaPosizione));
    }

    public int getMosseRimanentiAssassino() {
        return MAX_MOSSE_ASSASSINO - mosseAssassino;
    }

    public boolean isAssassinoSconfitto() {
        return mosseAssassino >= MAX_MOSSE_ASSASSINO ||
                (giocatori.stream()
                        .filter(g -> g instanceof Assassino)
                        .map(g -> (Assassino) g)
                        .anyMatch(this::isAssassinoCircondato));
    }

    public Tabellone getTabellone() {
        return tabellone;
    }
}
