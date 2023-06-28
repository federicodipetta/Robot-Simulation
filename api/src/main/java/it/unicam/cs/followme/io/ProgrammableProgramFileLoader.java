package it.unicam.cs.followme.io;

import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Environment;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.model.Programmable;
import it.unicam.cs.followme.parsing.ProgramFlowController;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * chi estende questa interfaccia deve costruire una lista di flow controller dato un file di
 * programmi
 * @param <P> il tipo dei flow controller
 * @param <E> il tipo dei programmable
 * @param <F> il tipo delle figure
 */

public interface ProgrammableProgramFileLoader<P extends ProgramFlowController<E,F>,E extends Programmable<Direction>, F extends Figure>{
    /**
     * @param e l'ambiente
     * @param program il file contenete il programma
     * @return una lista flow controller
     */
    List<P> getFlowControllers(File program, Environment<E,F> e) throws FollowMeParserException, IOException;

    /**
     * @param e l'ambiente
     * @param program il path contenete il file con il programma
     * @return una lista di flow controller
     */
    List<P> getFlowControllers(Path program, Environment<E,F> e) throws FollowMeParserException, IOException;

    /**
     * @param e l'ambiente
     * @param program la stinga contenete il programma
     * @return ritorna la lista di flow controller
     */
    List<P> getFlowControllers(String program, Environment<E,F> e) throws FollowMeParserException;

}
