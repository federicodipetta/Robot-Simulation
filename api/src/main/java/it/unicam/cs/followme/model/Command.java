package it.unicam.cs.followme.model;

/**
 * Rappresenta cosa deve fare un programmable in un comando
 */
@FunctionalInterface
public interface Command<E extends Programmable<Direction>, F extends Figure>{
    /**
     * dato un ambiente applica una comando
     * @param e l'ambiente che andra' modificato
     */
    void apply(Environment<E,F> e);

}
