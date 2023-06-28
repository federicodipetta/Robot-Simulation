package it.unicam.cs.followme.io;

import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.parsing.ProgrammableFlowControllerBuilder;
import it.unicam.cs.followme.model.Environment;
import it.unicam.cs.followme.model.Programmable;
import it.unicam.cs.followme.parsing.ProgrammableFlowController;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * questa classe e' un file loader per i ProgrammableFlowController
 * @param <E> il tipo del programmable
 * @param <F> il tipo della figura
 */
public class SimulationProgramLoader<E extends Programmable<Direction>,F extends Figure>
implements ProgrammableProgramFileLoader<ProgrammableFlowController<E,F>,E,F>{

    private final ProgrammableFlowControllerBuilder<E,F> builder;
    private final FollowMeParser parser;

    public SimulationProgramLoader(ProgrammableFlowControllerBuilder<E,F> parser){
        this.builder= parser;
        this.parser=new FollowMeParser(parser);
    }

    /**
     * @param program il file contenete il programma
     * @return una lista flow controller
     */
    @Override
    public List<ProgrammableFlowController<E,F>> getFlowControllers(File program, Environment<E,F> e) throws FollowMeParserException, IOException{
        this.parser.parseRobotProgram(program);
        return this.builder.buildFlowControllers(
                e.getProgrammableMap().keySet().stream().toList(),e);
    }

    /**
     * @param program il path contenete il file con il programma
     * @return una lista di flow controller
     */
    @Override
    public List<ProgrammableFlowController<E,F>> getFlowControllers(Path program, Environment<E,F> e) throws FollowMeParserException, IOException{
        this.parser.parseRobotProgram(program);
        return this.builder.buildFlowControllers(
                e.getProgrammableMap().keySet().stream().toList(),e);
    }

    /**
     * @param program la stinga contenete il programma
     * @return ritorna la lista di flow controller
     */
    @Override
    public List<ProgrammableFlowController<E,F>> getFlowControllers(String program, Environment<E,F> e) throws FollowMeParserException{
        this.parser.parseRobotProgram(program);
        return this.builder.buildFlowControllers(
                e.getProgrammableMap().keySet().stream().toList(),e);
    }
}
