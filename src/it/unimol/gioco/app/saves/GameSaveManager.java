package it.unimol.gioco.app.saves;

import it.unimol.gioco.app.Gioco;
import it.unimol.gioco.app.board.CellaAssassino;
import it.unimol.gioco.app.board.CellaInvestigatore;
import it.unimol.gioco.app.board.Tabellone;
import it.unimol.gioco.app.player.Assassino;
import it.unimol.gioco.app.player.Giocatore;
import it.unimol.gioco.app.player.Investigatore;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {
    private static final String SAVE_DIRECTORY = "game_saves/";

    static {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Salva lo stato corrente del gioco in un file
     * @param gioco Lo stato corrente del gioco da salvare
     * @param fileName Nome del file di salvataggio
     * @throws IOException Se si verificano errori durante il salvataggio
     */
    public static void saveGame(Gioco gioco, String fileName) throws IOException {
        // Assicurati che il nome del file abbia l'estensione .whm (Whitehall Mystery)
        if (!fileName.endsWith(".whm")) {
            fileName += ".whm";
        }

        File saveFile = new File(SAVE_DIRECTORY + fileName);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            // Salva lo stato del gioco
            out.writeObject(new GameState(
                    gioco.getListaGiocatori(),
                    gioco.getListaObiettivi(),
                    gioco.getPercorsoAssassino(),
                    gioco.getTurnoCorrente(),
                    gioco.getMosseRimanentiAssassino()
            ));
        }
    }

    /**
     * Carica uno stato di gioco salvato
     * @param fileName Nome del file di salvataggio da caricare
     * @return Oggetto Gioco con lo stato caricato
     * @throws IOException Se si verificano errori di lettura del file
     * @throws ClassNotFoundException Se la classe salvata non può essere trovata
     */
    public static Gioco loadGame(String fileName) throws IOException, ClassNotFoundException {
        // Assicurati che il nome del file abbia l'estensione .whm
        if (!fileName.endsWith(".whm")) {
            fileName += ".whm";
        }

        File saveFile = new File(SAVE_DIRECTORY + fileName);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile))) {
            GameState savedState = (GameState) in.readObject();

            // Ricrea il gioco dallo stato salvato
            Gioco gioco = new Gioco();

            // Ripristina i giocatori
            for (Giocatore giocatore : savedState.giocatori) {
                gioco.getListaGiocatori().add(giocatore);
            }

            // Ripristina gli obiettivi
            gioco.getListaObiettivi().clear();
            gioco.getListaObiettivi().addAll(savedState.obiettiviAssassino);

            // Ripristina il percorso dell'assassino
            gioco.getPercorsoAssassino().clear();
            gioco.getPercorsoAssassino().addAll(savedState.percorsoAssassino);

            // Ripristina il turno corrente e le mosse
            gioco.setTurnoCorrente(savedState.turnoCorrente);
            gioco.setMosseAssassino(savedState.mosseAssassino);

            return gioco;
        }
    }

    /**
     * Classe interna per serializzare lo stato del gioco
     */
    private static class GameState implements Serializable {
        List<Giocatore> giocatori;
        List<CellaAssassino> obiettiviAssassino;
        List<CellaAssassino> percorsoAssassino;
        int turnoCorrente;
        int mosseAssassino;

        public GameState(List<Giocatore> giocatori,
                         List<CellaAssassino> obiettiviAssassino,
                         List<CellaAssassino> percorsoAssassino,
                         int turnoCorrente,
                         int mosseAssassino) {
            this.giocatori = new ArrayList<>(giocatori);
            this.obiettiviAssassino = new ArrayList<>(obiettiviAssassino);
            this.percorsoAssassino = new ArrayList<>(percorsoAssassino);
            this.turnoCorrente = turnoCorrente;
            this.mosseAssassino = mosseAssassino;
        }
    }

    /**
     * Restituisce l'elenco dei salvataggi disponibili
     * @return Array di nomi di file di salvataggio
     */
    public static String[] getAvailableSaves() {
        File saveDirectory = new File(SAVE_DIRECTORY);
        return saveDirectory.list((dir, name) -> name.endsWith(".whm"));
    }

    /**
     * Elimina un salvataggio specifico
     * @param fileName Nome del file di salvataggio da eliminare
     * @return true se l'eliminazione ha avuto successo, false altrimenti
     */
    public static boolean deleteSave(String fileName) {
        if (!fileName.endsWith(".whm")) {
            fileName += ".whm";
        }
        File saveFile = new File(SAVE_DIRECTORY + fileName);
        return saveFile.delete();
    }
}