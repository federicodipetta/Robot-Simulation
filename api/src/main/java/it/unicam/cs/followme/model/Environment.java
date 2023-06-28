package it.unicam.cs.followme.model;


import java.util.List;
import java.util.Map;

/**
 * questa classe descrive un interfaccia per un ambiente nel quale all'interno vi sono programmable e figure
 * l'ambiente ha la responsabilità di tenere traccia delle posizioni degli item al suo iterno
*/
public interface Environment<E extends Programmable<Direction>, F extends Figure>{
    /**
     * serve per ritornare tutte le label che contengono quella position
     * @param position la posizione che deve essere controllata
     * @return la lista delle label che contengono quel punto, se non è contenuto
     * da nessuna label la lista e' vuota
     */
    List<F> getLabels(Cordinates position);

    /**
     * funziona esattamente come getLabels(Cordinates position) ma dando il programmable a cui sono associate
     * le coordinate
     * @param  programmable il programmable all'interno del enviorment che si vuole controllare
     * @return ritorna la lista delle figure che contengono il programmable
     * @see Environment#getLabels(Cordinates)
     */
    List<F> getLabels(E programmable);

    /**
     *
     * @param move il comando da applicare a questo ambiente
     */
     void apply(Command<E,F> move);

    /**
     * questo metodo data un area restituisce tutti i programmable che segnalano la label
     * all'interno di questo ambiente
     * @param label la label che devono segnalare i programmable
     * @param figura l'area d'interesse
     * @param cordinates il centro della figura
     * @return ritorna una mappa contenete i programmable e le loro coordinate
     */
    Map<E, Cordinates> getProgrammableWith(String label, Figure figura, Cordinates cordinates);

    /**
     * sostituisce la mappa dell'ambiente con la mappa data
     * @param programmable il programmable
     * @param newCordinates le nuove coordinate del programmable
     * @return true se il change e' andato a buon fine
     */
    boolean changeMap(E programmable, Cordinates newCordinates);

    /**
     *
     * @return la mappa dei movable con le coordinate associate
     */

    Map<E,Cordinates> getProgrammableMap();

    /**
     * @return la mappa delle figure
     */
    Map<F,Cordinates> getFigures();

    /**
     *
     * @param programmable il programmable del quale vogliamo le coordinate
     * @return le coordinate del programmable, null se non è presente
     */
    Cordinates getCoordinate(E programmable);

}
