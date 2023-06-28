package it.unicam.cs.followme.parsing;
import it.unicam.cs.followme.model.Command;
import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.model.Programmable;

/**
 * chi implementa questa interfaccia e' in grado di fornire il prossimo comando
 * di un certo programma dei robot, e' anche in grado di dire quando il programma e'
 * in configurazione finale
 * @param <E> il programmable associato
 * @param <F> le figure associate all'ambiente
 */
public interface ProgramFlowController<E extends Programmable<Direction>,F extends Figure>{
    /**
     *
     * @return il prossimo domando da eseguire
     */
    Command<E,F> next();

    /**
     *
     * @return ritorna true solo se
     */
    boolean isEnd();

    /**
     * sostituisce la time unit con quella specificata
     * @param timeUnit la nuova time unit
     */
    void setTimeUnit(double timeUnit);


}
