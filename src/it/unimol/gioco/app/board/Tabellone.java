package it.unimol.gioco.app.board;

import it.unimol.gioco.ui.StampaErroriUI;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * La classe Tabellone rappresenta il campo di gioco dei Giocatori
 */
public class Tabellone {
    private Graph<Cella, DefaultEdge> tabellone;
    private List<CellaAssassino> celleAssassino;
    private List<CellaInvestigatore> celleInvestigatore;
    private StampaErroriUI erroreUI = new StampaErroriUI();

    /**
     * Costruttore di Tabellone, crea un grafo semplice non diretto e con archi non pesati
     */
    public Tabellone() {
        this.tabellone = new SimpleDirectedGraph<Cella, DefaultEdge>(DefaultEdge.class);
        creagrafo();
    }

    /**
     * Il metodo crea delle liste di Celle che vengono poi inserite
     * come vertici all'interno del grafo stesso ed infine ne vengono
     * creati gli archi associati in modo arbitrario
     */
    public void creagrafo() {

        /*Composizione CelleAssassino*/
        celleAssassino = Arrays.asList(
                new CellaAssassino("NW", "1", "B"),
                new CellaAssassino("NW", "2", "B"),
                new CellaAssassino("NW", "3", "N"),
                new CellaAssassino("NW", "4", "B"),
                new CellaAssassino("NW", "5", "N"),
                new CellaAssassino("NE", "6", "B"),
                new CellaAssassino("NE", "7", "N"),
                new CellaAssassino("NE", "8", "N"),
                new CellaAssassino("SE", "9", "N"),
                new CellaAssassino("SW", "10", "N"),
                new CellaAssassino("SW", "11", "N"),
                new CellaAssassino("SW", "12", "B"),
                new CellaAssassino("SW", "13", "N"),
                new CellaAssassino("SW", "14", "B"),
                new CellaAssassino("SE", "15", "B"),
                new CellaAssassino("SE", "16", "N"),
                new CellaAssassino("NE", "17", "B"),
                new CellaAssassino("NE", "18", "N"),
                new CellaAssassino("SE", "19", "B"),
                new CellaAssassino("SE", "20", "B")
            );
        /*Composizione CelleInvestigatore*/
        celleInvestigatore = Arrays.asList(
                new CellaInvestigatore("NW", "AA", false),
                new CellaInvestigatore("NW", "AB", false),
                new CellaInvestigatore("NW", "AC", false),
                new CellaInvestigatore("NW", "AD", true),
                new CellaInvestigatore("NW", "AE", false),
                new CellaInvestigatore("NE", "AF", false),
                new CellaInvestigatore("NE", "AG", false),
                new CellaInvestigatore("NE", "AH", true),
                new CellaInvestigatore("NW", "AI", false),
                new CellaInvestigatore("NW", "AJ", true),
                new CellaInvestigatore("SW", "AK", true),
                new CellaInvestigatore("NE", "AL", true),
                new CellaInvestigatore("NW", "AM", false),
                new CellaInvestigatore("SW", "AN", false),
                new CellaInvestigatore("SW", "AO", false),
                new CellaInvestigatore("SW", "AP", false),
                new CellaInvestigatore("SW", "AQ", false),
                new CellaInvestigatore("SE", "AR", false),
                new CellaInvestigatore("SE", "AS", false),
                new CellaInvestigatore("SW", "AT", false),
                new CellaInvestigatore("NE", "AU", false),
                new CellaInvestigatore("NW", "AV", false),
                new CellaInvestigatore("SE", "AW", false),
                new CellaInvestigatore("SE", "AX", false)
                );
        //Aggiungo le celle nei vertici
        for (CellaAssassino cella : celleAssassino) {
            tabellone.addVertex(cella);
        }
        for (CellaInvestigatore cella : celleInvestigatore) {
            tabellone.addVertex(cella);
        }
        //Creo gli archi che collegano i vertici in modo arbitrario
        tabellone.addEdge(celleAssassino.get(0), celleInvestigatore.get(0));
        tabellone.addEdge(celleAssassino.get(0), celleInvestigatore.get(8));
        tabellone.addEdge(celleAssassino.get(1), celleInvestigatore.get(0));
        tabellone.addEdge(celleAssassino.get(1), celleInvestigatore.get(1));
        tabellone.addEdge(celleAssassino.get(1), celleInvestigatore.get(2));
        tabellone.addEdge(celleAssassino.get(2), celleInvestigatore.get(2));
        tabellone.addEdge(celleAssassino.get(2), celleInvestigatore.get(3));
        tabellone.addEdge(celleAssassino.get(2), celleInvestigatore.get(21));
        tabellone.addEdge(celleAssassino.get(2), celleInvestigatore.get(12));
        tabellone.addEdge(celleAssassino.get(2), celleInvestigatore.get(9));
        tabellone.addEdge(celleAssassino.get(3), celleInvestigatore.get(0));
        tabellone.addEdge(celleAssassino.get(3), celleInvestigatore.get(2));
        tabellone.addEdge(celleAssassino.get(3), celleInvestigatore.get(8));
        tabellone.addEdge(celleInvestigatore.get(8), celleInvestigatore.get(21));
        tabellone.addEdge(celleAssassino.get(4), celleInvestigatore.get(1));
        tabellone.addEdge(celleAssassino.get(4), celleInvestigatore.get(2));
        tabellone.addEdge(celleAssassino.get(4), celleInvestigatore.get(3));
        tabellone.addEdge(celleAssassino.get(4), celleInvestigatore.get(4));
        tabellone.addEdge(celleAssassino.get(5), celleInvestigatore.get(1));
        tabellone.addEdge(celleAssassino.get(5), celleInvestigatore.get(4));
        tabellone.addEdge(celleAssassino.get(5), celleInvestigatore.get(5));
        tabellone.addEdge(celleAssassino.get(6), celleInvestigatore.get(4));
        tabellone.addEdge(celleAssassino.get(6), celleInvestigatore.get(3));
        tabellone.addEdge(celleAssassino.get(6), celleInvestigatore.get(7));
        tabellone.addEdge(celleAssassino.get(7), celleInvestigatore.get(6));
        tabellone.addEdge(celleAssassino.get(7), celleInvestigatore.get(7));
        tabellone.addEdge(celleAssassino.get(7), celleInvestigatore.get(11));
        tabellone.addEdge(celleAssassino.get(7), celleInvestigatore.get(22));
        tabellone.addEdge(celleInvestigatore.get(6), celleInvestigatore.get(5));
        tabellone.addEdge(celleAssassino.get(8), celleInvestigatore.get(9));
        tabellone.addEdge(celleAssassino.get(8), celleInvestigatore.get(10));
        tabellone.addEdge(celleAssassino.get(8), celleInvestigatore.get(11));
        tabellone.addEdge(celleAssassino.get(9), celleInvestigatore.get(9));
        tabellone.addEdge(celleAssassino.get(9), celleInvestigatore.get(10));
        tabellone.addEdge(celleAssassino.get(9), celleInvestigatore.get(12));
        tabellone.addEdge(celleAssassino.get(9), celleInvestigatore.get(13));
        tabellone.addEdge(celleAssassino.get(9), celleInvestigatore.get(15));
        tabellone.addEdge(celleAssassino.get(10), celleInvestigatore.get(12));
        tabellone.addEdge(celleAssassino.get(10), celleInvestigatore.get(13));
        tabellone.addEdge(celleAssassino.get(10), celleInvestigatore.get(14));
        tabellone.addEdge(celleAssassino.get(11), celleInvestigatore.get(14));
        tabellone.addEdge(celleAssassino.get(11), celleInvestigatore.get(16));
        tabellone.addEdge(celleAssassino.get(12), celleInvestigatore.get(15));
        tabellone.addEdge(celleAssassino.get(12), celleInvestigatore.get(16));
        tabellone.addEdge(celleAssassino.get(12), celleInvestigatore.get(18));
        tabellone.addEdge(celleAssassino.get(12), celleInvestigatore.get(19));
        tabellone.addEdge(celleAssassino.get(13), celleInvestigatore.get(16));
        tabellone.addEdge(celleAssassino.get(13), celleInvestigatore.get(19));
        tabellone.addEdge(celleAssassino.get(14), celleInvestigatore.get(18));
        tabellone.addEdge(celleAssassino.get(14), celleInvestigatore.get(19));
        tabellone.addEdge(celleInvestigatore.get(18), celleInvestigatore.get(19));
        tabellone.addEdge(celleAssassino.get(15), celleInvestigatore.get(10));
        tabellone.addEdge(celleAssassino.get(15), celleInvestigatore.get(17));
        tabellone.addEdge(celleAssassino.get(15), celleInvestigatore.get(23));
        tabellone.addEdge(celleAssassino.get(16), celleInvestigatore.get(5));
        tabellone.addEdge(celleAssassino.get(16), celleInvestigatore.get(20));
        tabellone.addEdge(celleInvestigatore.get(6), celleInvestigatore.get(20));
        tabellone.addEdge(celleAssassino.get(17), celleInvestigatore.get(6));
        tabellone.addEdge(celleAssassino.get(17), celleInvestigatore.get(20));
        tabellone.addEdge(celleAssassino.get(17), celleInvestigatore.get(22));
        tabellone.addEdge(celleAssassino.get(18), celleInvestigatore.get(22));
        tabellone.addEdge(celleAssassino.get(18), celleInvestigatore.get(11));
        tabellone.addEdge(celleAssassino.get(18), celleInvestigatore.get(23));
        tabellone.addEdge(celleAssassino.get(19), celleInvestigatore.get(23));
        tabellone.addEdge(celleAssassino.get(19), celleInvestigatore.get(18));
    }

    /**
     * Getter della cella dell'assassino con posizione fornita dall'utente
     * @param i iesima vella da ottenere
     * @return CellaAssassino o Null
     */
    public CellaAssassino getCellaAssassino(int i) {
        if (i >= 0 && i < celleAssassino.size()){
            return celleAssassino.get(i);
        }

        return null;
    }
    public int getMaxCelleAssassino() {
        return celleAssassino.size();
    }

    /**
     * Getter della Cella dell'investigatore con posizione fornita dall'utente
     * @param nome nominativo della cella "AA" "BB"
     * @return Cella Investigatore o Null
     */
    public CellaInvestigatore getCellaInvestigatore(String nome) {
        if (nome == null) {
            erroreUI.erroreRicercaCellaInvestigatore1();
            return null;
        }
        for (CellaInvestigatore cella : celleInvestigatore){
            String nomeCella = cella.getNumero();
            if (nomeCella != null) {
                 if(nomeCella.equals(nome)){
                    return cella;
                }
            } else {
                erroreUI.erroreRicercaCellaInvestigatore2();
            }
        }
        erroreUI.erroreRicercaCellaInvestigatore3(nome);
        return null;
    }
    /**
     * Metodo per trovare i vicini di una cella nel grafo.
     * @param cella la cella di cui trovare i vicini.
     * @return un Set contenente le celle vicine.
     */
    public Set<Cella> findNeighbors(Cella cella) {
        return new HashSet<>(Graphs.neighborListOf(tabellone, cella));
    }

    /**
     * Trova le celle assassine vicine alla data cella assassino.
     * @param cella la cella assassino di partenza
     * @return un Set delle celle assassine vicine
     */
    public Set<CellaAssassino> trovaViciniCelleAssassino(CellaAssassino cella) {
        Set<Cella> vicini = findNeighbors(cella);
        return vicini.stream()
                .filter(c -> c instanceof CellaAssassino)
                .map(c -> (CellaAssassino) c)
                .collect(Collectors.toSet());
    }

    /**
     * Trova le celle investigatore vicine alla data cella investigatore.
     * @param cella la cella investigatore di partenza
     * @return un Set delle celle investigatore vicine
     */
    public Set<CellaInvestigatore> trovaViciniCelleInvestigatore(CellaInvestigatore cella) {
        Set<Cella> vicini = findNeighbors(cella);
        return vicini.stream()
                .filter(c -> c instanceof CellaInvestigatore)
                .map(c -> (CellaInvestigatore) c)
                .collect(Collectors.toSet());
    }

}