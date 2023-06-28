package it.unicam.cs.followme.model;

/**
 * definisce oggetti che si possono spostare nello spazio, i quali devono avere una direzione e una velocita'
 */
public interface Programmable<D extends Direction>{
    /**
     * @return la direzione del robot
     */
    D getDirection();

    /**
     *
     * @param dircetion la nuova direzione
     */
    void setDirection(D dircetion);

    /**
     * @return la label che sta segnalando
     */
    String getLabel();

    /**
     *
     * @param label l'etichetta che il movable deve segnalare
     */
    void signalLabel(String label);

    /**
     *
     * @param label l'etichetta che il robot deve smettere di segegnalare
     */
    void unsignalLabel(String label);

}
