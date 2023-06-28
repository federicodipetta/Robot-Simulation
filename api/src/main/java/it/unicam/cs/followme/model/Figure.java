package it.unicam.cs.followme.model;


/**
 * una Figure è un oggetto nello spazio con un area
 */
public interface Figure {
    /**
     * serve per verificare se un punto nello spazio appartene all'area
     * @param position
     * @return true se la posizione si trova all'interno dell'area false altrimenti
     */
    boolean contains(Cordinates position);

    /**
     *
     * @return la label della figura
     */
    String getLabel();

    /**
     * @return il tipo della figura dall'enumerazione
     */
    FigureEnumeration getType();
}
