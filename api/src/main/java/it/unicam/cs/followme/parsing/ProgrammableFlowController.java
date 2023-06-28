package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.util.Pair;
import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.util.CordinatesUtil;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.*;
import java.util.function.Function;

/**
 * questa classe ha la responsabilita' di rendere eseguibile il programma, quindi restituendo cosa deve
 * fare il programmable al prossimo step, ogni oggetto ha associato il proprio programmable
 * @param <E> il programmable di questo oggetto e dell'ambiente associato
 * @param <F> il tipo delle figure dell'ambiente associato al programma
 */
public class ProgrammableFlowController<E extends Programmable<Direction>,F extends Figure>
        implements ProgramFlowController<E,F>{

        //la lista dei comandi da interpretare
        List<Pair<RobotCommand,String[]>> program;

        //l'ambiente su cui si deve lavorare
        Environment<E,F> environment;
        //il programmable associato a questo oggetto
        E prog;
        // tiene il conto degli step
        private int step;

        /**
         * la default timeUnit di questo oggetto
         */
        public static final double DEFAULT_TIME_UNIT=1.0;
        private double timeUnit;

        private Stack<Integer> indexstack;

        public ProgrammableFlowController (Environment<E,F> e,
                                           E c,
                                           List<Pair<RobotCommand,String[]>> program,
                                           double time
                                            ){
            if(e==null || c==null || program == null)throw new NullPointerException("parametri nulli");
            this.environment=e;
            this.prog=c;
            this.program=program;
            this.step=0;
            this.indexstack=new Stack<>();
            this.timeUnit=time;
        }

        public ProgrammableFlowController(Environment<E,F> e,
                                          E c,
                                          List<Pair<RobotCommand,String[]>> program){
            this(e,c,program,DEFAULT_TIME_UNIT);
        }

    /**
     * sostituisce la time unit con quella specificata
     *
     * @param timeUnit la nuova time unit
     */
    @Override
    public void setTimeUnit(double timeUnit){
        this.timeUnit=timeUnit;
    }

    /**
     *
     * @return true se il programma e' in una configurazione finale
     *          false altrimenti
     */
    @Override
    public boolean isEnd(){
        return this.step==this.program.size()-1;
    }


    /**
     *
     * @return il prossimo comando da eseguire
     */
    @Override
    public Command<E,F> next(){
        if(isEnd()) return e->{};
        RobotCommand r=this.program.get(this.step).getFirst();
        String[] strings =this.program.get(this.step).getSecond();
        if(isLoopCommand(r)) return loopCommand(r,strings);
        else return notLoopCommand(r,strings);
    }


    /*
     * questo comando gestisce i comandi di loop
     */
    private Command<E,F> loopCommand(RobotCommand command,String[] s){
        return switch(command){
            case UNTIL   ->until(s[0]);
            case REPEAT  ->repeat(s);
            case FOREVER ->doForever();
            case DONE    -> done();
            default -> null;//non raggiungibile
        };
    }

    /*
     * gestisce il comandi che non sono iteratori
     */
    private Command<E,F> notLoopCommand(RobotCommand command, String[] s){
          return switch(command){
             case MOVE -> moveCommand(s);
             case SIGNAL,UNSIGNAL -> signal(s[0]);
             case SKIP -> skipCommand(s);
             case FOLLOW -> followCommand(s[0],s);
             default -> null;//non raggiungibile
         };
    }

    private Command<E,F> repeat(String[] s){
        List<Integer> list= parseToN(s, Integer::parseInt);
        if(list.get(0)>list.get(1)){
            this.program.get(this.step).getSecond()[1]= Integer.toString(list.get(1)+1);
            this.indexstack.push(this.step++);
            return next();
        }
        this.program.get(this.step).getSecond()[1]= Integer.toString(0);
        jumpToNextDone();
        return next();
    }


    private Command<E,F> doForever() {
        this.indexstack.push(this.step);
        this.step++;
        return next();
    }


    private Command<E,F> done(){
        this.step=this.indexstack.pop();
        return next();
    }

    private Command<E,F> until(String label){
        if(this.environment.getLabels(this.prog).stream().map(Figure::getLabel)
                .filter(label::equals).toList().isEmpty())
            this.indexstack.push(this.step++);
        else
            jumpToNextDone();
        return next();
    }

    /*
    va al comando dopo il prossimo done legato alla struttura di iterazione
     */
    private void jumpToNextDone(){
        int cont=1;
        for(this.step=this.step+1;this.step<this.program.size()&&cont>0;this.step++)
            if(isLoopCommand(this.program.get(this.step).getFirst()))
                if(this.program.get(this.step).getFirst()==RobotCommand.DONE)
                    cont--;
                else
                    cont++;
    }

    private Command<E,F> followCommand(String label,String[] s){
        this.step++;
        List<Double> p = List.of(Double.parseDouble(s[1]),Double.parseDouble(s[2]));
        Map<E,Cordinates> map= environment.getProgrammableWith(label,new Circle(p.get(0),""),
                environment.getCoordinate(this.prog));
        if(!map.isEmpty()){
            double x= map.values().stream().map(Cordinates::getX).reduce(0.0,Double::sum)/map.size();
            double y= map.values().stream().map(Cordinates::getY).reduce(0.0,Double::sum)/map.size();
            Cordinates c= CordinatesUtil.getRelative( new Cordinates(x,y),this.environment.getCoordinate(this.prog));
            //essendo che la direzione scelta non ha significato allora si muovera' in una posizione casuale
            if(c.getX()==0&&c.getY()==0)moveRandom(-p.get(0), p.get(0), -p.get(0), p.get(0), p.get(1));
            else prog.setDirection(new Direction(c,p.get(1)));
        } else moveRandom( -p.get(0), p.get(0), -p.get(0), p.get(0), p.get(1) );
        return move();
    }

    private Command<E,F> skipCommand(String[] s){
        int n = Integer.parseInt(s[0]);
        int t= Integer.parseInt(s[1]);
        if(n<=t){
            this.program.get(this.step).getSecond()[1]="0";
            this.step++;
            return next();
        }else {
            this.program.get(this.step).getSecond()[1]=Integer.toString(t+1);
            return move();
        }
    }

    private Command<E,F> signal(String label){
        if(this.program.get(this.step++).getFirst()==RobotCommand.SIGNAL)
            this.prog.signalLabel(label);
        else
            this.prog.unsignalLabel(label);
        return e -> {};//non deve muovere il robot
    }

     private <N extends Number> List<N> parseToN (String[]a , Function<String,N> f){
         return Arrays.stream(a)
                 .map(f).toList();
     }

    private Command<E,F> moveCommand(String[] s){
        List<Double>p=parseToN(s,Double::parseDouble);
        this.step++;
        // se la size non e' 3 allora e' 5
        if(p.size()==3)
            this.prog.setDirection( new Direction(p.get(0),p.get(1),p.get(2)) );
        else
            moveRandom(p.get(0),p.get(1),p.get(2),p.get(3),p.get(4));
        return move();
    }



    private void moveRandom(double x1, double x2, double y1, double y2, double s ){
        this.prog.setDirection(
                new Direction(CordinatesUtil.getRelative(
                        new Cordinates(getRandomBetween(x1,x2) , getRandomBetween(y1,y2)),
                                this.environment.getCoordinate(this.prog)
                ),s));
    }

    private double getRandomBetween(double a, double b){
        Random r=new Random();
        if(a>b){
            double c=a;
            a=b;
            b=c;
        }
        return r.nextDouble(a,b);
    }


    private boolean isLoopCommand(RobotCommand command){
        return switch(command){
            case REPEAT , UNTIL , FOREVER, DONE-> true;
            default -> false;
        };

    }
    /*
    selezionate la direzione di un robot crea il comando di movimento standard
     */
    private Command<E,F> move(){
        return e-> e.changeMap(this.prog,
                DirectionApplier.DEFAULT_APPLIER.apply(
                this.prog.getDirection(),
                e.getCoordinate(this.prog),
                this.timeUnit));
    }
}
