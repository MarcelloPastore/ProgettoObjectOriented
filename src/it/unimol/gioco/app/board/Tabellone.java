package it.unimol.gioco.app.board;


import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.Serializable;
import java.util.*;


/**
 * La classe Tabellone rappresenta il campo di gioco dei Giocatori
 */
public class Tabellone implements Serializable {
    private final Graph<Cella, DefaultEdge> tabellone;
    private List<CellaAssassino> celleAssassino;
    private List<CellaInvestigatore> celleInvestigatore;

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
     * @param i i-esima cella da ottenere
     * @return CellaAssassino o Null
     */
    public CellaAssassino getCellaAssassino(int i) {
        if (i >= 0 && i < celleAssassino.size()){
            return celleAssassino.get(i);
        } else return null;
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
        for (CellaInvestigatore cella : celleInvestigatore){
            String nomeCella = cella.getNumero();
            if(nomeCella.equals(nome.toUpperCase())){
                return cella;
            }
        }
        return null;
    }

     /**
     * Restituisce una lista di CelleAssassino immediatamente vicine alla cella data in input,
     * dove "immediatamente vicine" significa che sono collegate attraverso una singola CellaInvestigatore.
     *
     * @param cellaInput la cella assassino di partenza
     * @return List<CellaAssassino> lista delle celle assassino immediatamente vicine senza duplicati
     */
    public List<CellaAssassino> getCelleAssassineVicine(CellaAssassino cellaInput) {
        if (cellaInput == null) {
            return new ArrayList<>();
        }

        // Usiamo un Set per le celle assassino vicine per evitare duplicati
        Set<CellaAssassino> celleVicine = new HashSet<>();

        // Primo livello: trova le celle investigatore collegate
        Set<Cella> celleInvestigatore = new HashSet<>();
        celleInvestigatore.addAll(Graphs.successorListOf(tabellone, cellaInput));
        celleInvestigatore.addAll(Graphs.predecessorListOf(tabellone, cellaInput));

        // Per ogni cella investigatore, trova le celle assassino collegate
        for (Cella cellaInv : celleInvestigatore) {
            if (cellaInv instanceof CellaInvestigatore) {
                Set<Cella> celleCollegate = new HashSet<>();
                celleCollegate.addAll(Graphs.successorListOf(tabellone, cellaInv));
                celleCollegate.addAll(Graphs.predecessorListOf(tabellone, cellaInv));

                for (Cella cella : celleCollegate) {
                    if (cella instanceof CellaAssassino && !cella.equals(cellaInput)) {
                        celleVicine.add((CellaAssassino) cella);
                    }
                }
            }
        }
        // Convertiamo il Set in List per mantenere la firma del metodo
        return new ArrayList<>(celleVicine);
    }

    public List<CellaInvestigatore> getCelleInvestigatoriVicine(CellaInvestigatore cellaInput) {
        if (cellaInput == null) {
            return new ArrayList<>();
        }

        // Usiamo un Set per le celle investigatore vicine per evitare duplicati
        Set<CellaInvestigatore> celleVicine = new HashSet<>();

        // Primo livello: trova le celle assassino collegate
        Set<Cella> celleAssassino = new HashSet<>();
        celleAssassino.addAll(Graphs.successorListOf(tabellone, cellaInput));
        celleAssassino.addAll(Graphs.predecessorListOf(tabellone, cellaInput));

        // Per ogni cella assassino, trova le celle investigatore collegate
        for (Cella cellaAss : celleAssassino) {
            if (cellaAss instanceof CellaAssassino) {
                Set<Cella> celleCollegate = new HashSet<>();
                celleCollegate.addAll(Graphs.successorListOf(tabellone, cellaAss));
                celleCollegate.addAll(Graphs.predecessorListOf(tabellone, cellaAss));

                for (Cella cella : celleCollegate) {
                    if (cella instanceof CellaInvestigatore && !cella.equals(cellaInput)) {
                        celleVicine.add((CellaInvestigatore) cella);
                    }
                }
            }
        }

        return new ArrayList<>(celleVicine);
    }

}