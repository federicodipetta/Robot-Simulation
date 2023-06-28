package it.unicam.cs.followme.model;

import it.unicam.cs.followme.util.CordinatesUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @param <E> programmable che conterra' l'ambiente
 * @param <F> figure che conterra' l'ambiente
 */
public class Space<E extends Programmable<Direction>, F extends Figure> implements Environment<E,F>{
    /**
     * mappa contenete i programmable con le cordinate a loro associate
     */
    private Map<E,Cordinates> progs;

    /**
     * mappa contenente le figure con una loro mappa associata
     */
    private final Map<F,Cordinates> figures;

    /**
     * costruisce un ambiente a partire dalle due mappe
     * @param ps mappa dei programmable
     * @param fs mappa delle figure
     */
    public Space(Map<E,Cordinates> ps, Map<F,Cordinates>fs){
        if(ps==null|| fs==null)throw new NullPointerException("una delle mappe nulla");
        this.progs=new HashMap<>(ps);
        this.figures=new HashMap<>(fs);
    }

    /**
     * serve per ritornare tutte le label che contengono quella position
     *
     * @param position la posizione che deve essere controllata
     * @return la lista delle label che contengono quel punto, se non è contenuto
     * da nessuna label la lista e' vuota
     */
    @Override
    public List<F> getLabels(Cordinates position){
        return this.figures.entrySet().stream()
                .filter(e-> e.getKey().contains(CordinatesUtil.getRelative(e.getValue(),position)))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * funziona esattamente come getLabels(Cordinates position) ma dando il programmable a cui sono associate
     * le coordinate
     * @param  programmable il programmable all'interno del environment che si vuole controllare
     * @return ritorna la lista delle figure che contengono il programmable
     * @see Environment#getLabels(Cordinates)
     */
    @Override
    public List<F> getLabels(E programmable){
        return getLabels(this.getCoordinate(programmable));
    }



    /**
     * lo stato verra' modificato dopo la seguente operazione
     * @param move la mossa da applicare
     */
    @Override
    public void apply(Command<E,F> move){
        move.apply(this);
    }

    /**
     * @return la mappa delle figure
     */
    @Override
    public Map<F,Cordinates> getFigures(){
        return new HashMap<>(this.figures);
    }

    /**
     * questo metodo data un area restituisce tutti i programmable che segnalano la label
     * all'interno di questo ambiente
     *
     * @param label      la label che devono segnalare i programmable
     * @param figure     l'area d'interesse
     * @param cordinates il centro della figura
     * @return ritorna una mappa contenete i programmable e le loro coordinate
     * @throws NullPointerException se la label passata e' nulla
     */
    @Override
    public Map<E,Cordinates> getProgrammableWith(String label, Figure figure, Cordinates cordinates){
        return this.progs.entrySet().stream()
                .filter(e -> label.equals(e.getKey().getLabel()))
                .filter(e -> figure.contains(CordinatesUtil.getRelative(cordinates,e.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * sostituisce la posizione di un prog se presente
     *
     * @param p programmable da sostituire le coordinate
     * @param cordinates le nuove coordinate del p
     * @return true se il change e' andato a buon fine
     */
    @Override
    public boolean changeMap(E p,Cordinates cordinates ){
        if(cordinates==null||p==null)throw new NullPointerException("il programmable o le coordinate sono nulli");
        this.progs.replace(p,cordinates);
        return true;
    }

    /**
     * @return la mappa dei movable con le coordinate associate
     */
    @Override
    public Map<E,Cordinates> getProgrammableMap(){
        return new HashMap<>(this.progs);
    }

    /**
     * @param programmable il programmable del quale vogliamo le coordinate
     * @return le coordinate del programmable, null se non è presente
     */
    @Override
    public Cordinates getCoordinate(E programmable){
        return this.progs.get(programmable);
    }
}
