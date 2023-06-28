package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Environment;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.model.Programmable;
import it.unicam.cs.followme.utilities.FollowMeParserHandler;

import java.util.List;

/**
 * chi implementa questa interfaccia e' in grado di fornire una lista di
 * ProgramFlowController data una lista di programmable e un ambiente
 * @param <P> il program flow controller
 * @param <E> il programmable da associare al P e dell'ambiente
 * @param <F> le figure dell'ambiente
 */
public interface ProgramFlowControllerBuilder<P extends ProgramFlowController<E,F>,
        E extends Programmable<Direction>,F extends Figure>
        extends FollowMeParserHandler{
    /**
     * @param e l'ambiente da associare ai flow controllers
     * @param programmables i program flow controller builder
     * @return ritorna una lista di ProgramFlowController per quel determinato
     */
    List<P> buildFlowControllers(List<E> programmables, Environment<E,F> e);

}
