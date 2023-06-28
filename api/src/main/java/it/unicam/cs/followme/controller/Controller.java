package it.unicam.cs.followme.controller;
import it.unicam.cs.followme.io.EnvironmentFileLoader;
import it.unicam.cs.followme.io.ProgrammableProgramFileLoader;
import it.unicam.cs.followme.io.SimulationEnvironmentLoader;
import it.unicam.cs.followme.io.SimulationProgramLoader;
import it.unicam.cs.followme.parsing.ProgrammableFlowControllerBuilder;
import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.parsing.ProgramFlowController;
import it.unicam.cs.followme.parsing.ProgrammableFlowController;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * il controller per il modello
 * @param <E>il programmable che deve gestire
 * @param <F>la figura che deve gestire
 */

public class Controller<E extends Programmable<Direction>,F extends Figure>{
    /**
     * time unit di default
     */
    public final static double DEFAULT_TIME_UNIT=1.0;
    /**
     * time unit attuale
     */
    private double time_unit;
    /**
     * l'ambiente che deve gestire
     */
    private Environment<E,F> environment;
    /**
     * il file loader per l'ambiente
     */
    private final EnvironmentFileLoader<E,F> envloader;
    /**
     * il file loadre per il programma
     */
    private final ProgrammableProgramFileLoader<ProgrammableFlowController<E,F>,E,F> progloader;

    /**
     *
     * @return un controller per la simulazione
     */
    public static Controller<Programmable<Direction>,Figure> getSimulationController(){
        ProgrammableFlowControllerBuilder<Programmable<Direction>,Figure>
                builder= new ProgrammableFlowControllerBuilder<>();
        FollowMeParser parser=new FollowMeParser(builder);
        return new Controller<>(
                new SimulationEnvironmentLoader<>(parser),
                new SimulationProgramLoader<>(builder)
                );
    }

    /**
     * la lista di controllori
     */

    private List<ProgrammableFlowController<E,F>> list;

    /**
     * costruttore di un controller dati dei file loader
     * @param envloader loader per l'ambiente
     * @param progloader loader per il programma
     */
    public Controller(EnvironmentFileLoader<E,F> envloader,
               ProgrammableProgramFileLoader<ProgrammableFlowController<E,F>,E,F> progloader){
        this.envloader=envloader;
        this.progloader=progloader;
        this.environment=new Space<>(new HashMap<>(),new HashMap<>());
        this.time_unit=DEFAULT_TIME_UNIT;
        this.list=new ArrayList<>();
    }

    /**
     *
     * @param envloader loader dell'ambiente
     * @param progloader loader dei programmi
     * @param time_unit time unit
     */

    public Controller(EnvironmentFileLoader<E,F> envloader,
                                 ProgrammableProgramFileLoader<ProgrammableFlowController<E,F>,E,F> progloader,int time_unit){
        this(envloader, progloader);
        this.time_unit=time_unit;
    }

    /**
     * @return la time unit attuale del controller
     */
    public double getTime_unit(){
        return time_unit;
    }

    /**
     * cambia la time unit dei flowController
     * @param time_unit la nuova time unit
     */
    public void setTime_unit(double time_unit){
        this.time_unit=time_unit;
        this.list.forEach(e->e.setTimeUnit(time_unit));
    }

    /**
     *  fa proseguire di uno step la simulazione
     */
    public void step(){
            this.list.stream()
                    .map(ProgramFlowController::next)
                    .forEach(this.environment::apply);
    }

    /**
     * @return la mappa delle figure dell'ambiente
     */
    public Map<F,Cordinates> getFigures(){
        return this.environment.getFigures();
    }

    /**
     * @return la mappa dei programmable
     */
    public Map<E,Cordinates> getRobot(){
        return this.environment.getProgrammableMap();
    }

    /**
     * pulisce la simulazione, e l'ambiente viene resettato
     */
    public void clear(){
        this.environment=new Space<>(new HashMap<>(), new HashMap<>());
        this.time_unit=DEFAULT_TIME_UNIT;
        this.list=new ArrayList<>();
    }

    /**
     * genera l'ambiente della simulazione
     * @param figure file contenete le figure
     * @param map mappa dei robot
     * @throws FollowMeParserException se il figure e' scritto in maniera errata
     * @throws IOException se ci sono problemi con la lettura del file
     */
    public void generateEnvironment(File figure ,Map<E,Cordinates> map ) throws FollowMeParserException, IOException{
        this.environment=this.envloader.getEnvironment(figure, map );
    }

    /**
     *
     * @param program file contenente il programma dei robot
     * @throws FollowMeParserException se il file non e' scritto in maniera corretta
     * @throws IOException se ci sono problemi con la lettura del file
     */
    public void setProgram(File program) throws FollowMeParserException, IOException{
        this.list=this.progloader.getFlowControllers(program, this.environment);
    }

    /**
     * inizializza un controller dati i parametri
     * @param figure file delle figure
     * @param program file dei programmi
     * @param map mappa dei programmable
     * @throws FollowMeParserException se i file non sono scritti in maniera corretta
     * @throws IOException se ci sono problemi con la lettura dei fie
     * @see Controller#setProgram(File)
     * @see Controller#generateEnvironment(File, Map)
     */
    public void start(File figure, File program, Map<E,Cordinates> map) throws FollowMeParserException, IOException{
        this.generateEnvironment(figure, map);
        this.setProgram(program);
    }

}
