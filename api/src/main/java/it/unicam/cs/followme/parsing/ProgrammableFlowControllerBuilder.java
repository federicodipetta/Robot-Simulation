package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.util.Pair;
import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Environment;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.model.Programmable;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * questa classe e' in grado di generare una lista di comandi interpretabili da un program flow controller
 */
public class ProgrammableFlowControllerBuilder<E extends Programmable<Direction>,F extends Figure>
        implements ProgramFlowControllerBuilder<ProgrammableFlowController<E,F>,E,F> {

    private List<Pair<RobotCommand,String[]>> list;

    public ProgrammableFlowControllerBuilder(){
        this.list=new ArrayList<>();
    }

    /**
     * This method is the method that is invoked ad the beginning of the parse procedure.
     */
    @Override
    public void parsingStarted(){
        this.list=new ArrayList<>();
    }

    /**
     * This method is the method that is invoked ad the end of the parse procedure.
     */
    @Override
    public void parsingDone(){
        // coppia speciale che significa che i comandi sono finiti
        this.list.add(new Pair<>(null, new String[0]));
    }

    /**
     * Method invoked when a command "MOVE" is parsed.
     *
     * @param args argomenti del comando.
     */
    @Override
    public void moveCommand(double[] args){

        this.list.add(new Pair<>(RobotCommand.MOVE,toString(args)));

    }

    private String[] toString(double[]args){
        String[] s= new String[args.length];
        for(int i=0;i<args.length;i++)
            s[i]=String.valueOf(args[i]);
        return s;
    }
    /**
     * Method invoked when a command "MOVE RANDOM" is parsed.
     *
     * @param args argomenti del comando.
     */
    @Override
    public void moveRandomCommand(double[] args){
        this.list.add(new Pair<>(RobotCommand.MOVE,toString(args)));
    }

    /**
     * Method invoked when a command "SIGNAL" is parsed.
     *
     * @param label label to signal
     */
    @Override
    public void signalCommand(String label){
        this.list.add(new Pair<>(RobotCommand.SIGNAL,new String[]{label}));
    }

    /**
     * Method invoked when a command "UNSIGNAL" is parsed.
     *
     * @param label label to unsignal
     */
    @Override
    public void unsignalCommand(String label){
        this.list.add(new Pair<>(RobotCommand.UNSIGNAL,new String[]{label}));
    }

    /**
     * Method invoked when a command "FOLLOW" is parsed.
     *
     * @param label label to follow
     * @param args  command arguments
     */
    @Override
    public void followCommand(String label, double[] args){
        this.list.add(new Pair<>(RobotCommand.FOLLOW,new String[]{label,toString(args)[0],toString(args)[1]}));
    }

    /**
     * Method invoked when a command "STOP" is parsed.
     */
    @Override
    public void stopCommand(){
        this.list.add(new Pair<>(RobotCommand.STOP,new String[0]));
    }

    /**
     * Method invoked when a command "WAIT" is parsed.
     *
     * @param s number of seconds;
     */
    @Override
    public void waitCommand(int s){
        this.list.add(new Pair<>(RobotCommand.SKIP,new String[]{String.valueOf(s),"0"}));
    }

    /**
     * Method invoked when a command "REPEAT" is parsed.
     *
     * @param n number of iterations.
     */
    @Override
    public void repeatCommandStart(int n){
        this.list.add(new Pair<>(RobotCommand.REPEAT,new String[]{String.valueOf(n),"0"}));
    }

    /**
     * Method invoked when a command "UNTIL" is parsed.
     *
     * @param label name of a label
     */
    @Override
    public void untilCommandStart(String label){
        this.list.add(new Pair<>(RobotCommand.UNTIL,new String[]{label}));
    }

    /**
     * Method invoked when a command "DO FOREVER" is parsed.
     */
    @Override
    public void doForeverStart(){
        this.list.add(new Pair<>(RobotCommand.FOREVER,new String[0]));
    }

    /**
     * Method invoked when a command "DONE" is parsed.
     */
    @Override
    public void doneCommand(){
        this.list.add(new Pair<>(RobotCommand.DONE,new String[0]));
    }


    /**
     * @param programmables i program flow controller builder
     * @param e l'ambiente con il quale costruire i programmi
     * @return ritorna una lista di ProgramFlowController per quel determinato
     */
    @Override
    public List<ProgrammableFlowController<E,F>> buildFlowControllers(List<E> programmables, Environment<E,F> e){
        List<ProgrammableFlowController<E,F>> list = new ArrayList<>(programmables.size());
        for(E p : programmables){
            ArrayList<Pair<RobotCommand,String[]>>list1 = new ArrayList<>(this.list.size());
            for(Pair<RobotCommand,String[]> pair: this.list){
                list1.add(new Pair<>(pair.getFirst(),pair.getSecond().clone()));
            }
            list.add(new ProgrammableFlowController<>(e,p,list1));
        }
        return list;//questa implementazione evita che ogni oggetto abbia la stessa lista
    }
}

