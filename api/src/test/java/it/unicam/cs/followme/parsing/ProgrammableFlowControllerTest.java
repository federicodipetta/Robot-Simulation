package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgrammableFlowControllerTest{
    private Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
    private Robot<Direction>r2 =new Robot<>(new Direction(1,1,1));
    private Robot<Direction>r3 =new Robot<>(new Direction(1,2,1));
    private Figure c = new Circle(1,"A");
    private Figure r = new Rectangle(2,4,"B");
    private Cordinates c1 = new Cordinates(0,0);
    private Cordinates c2 = new Cordinates(0,1);
    private Cordinates c3 = new Cordinates(0.5,0.5);
    private Cordinates c4 = new Cordinates(3,2);
    private Cordinates c5 = new Cordinates(1.01593,1.54909);

    @Test
    void repeatTest() throws FollowMeParserException{
        Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
        Map<Robot<Direction>,Cordinates>m=new HashMap<>();
        m.put(r1,c1);
        Environment<Robot<Direction>,Figure> e = new Space<>(m,new HashMap<>());
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram("REPEAT 4\nMOVE 0 1 1\nDONE\n");
        ProgrammableFlowController<Robot<Direction>,Figure> a = handler.buildFlowControllers(List.of(r1),e).get(0);
        Command<Robot<Direction>,Figure> c= a.next();
        int cont=0;
        while(!a.isEnd()){
            e.apply(c);
            c=a.next();
            cont++;
        }
        assertEquals(4,cont);
        assertEquals(new Cordinates(0,4),e.getCoordinate(r1));
    }

    @Test
    public void untilTest() throws FollowMeParserException{
        Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
        Map<Robot<Direction>,Cordinates> m =new HashMap<>();
        Map<Figure,Cordinates> f =new HashMap<>();
        m.put(r1,c1);
        f.put(c,new Cordinates(0,5));
        Environment<Robot<Direction>,Figure> e = new Space<>(m,f);
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram("UNTIL A\nMOVE 0 1 1\nDONE\n");
        ProgrammableFlowController<Robot<Direction>,Figure> a = handler.buildFlowControllers(List.of(r1),e).get(0);
        Command<Robot<Direction>,Figure> c= a.next();
        while(!a.isEnd()){
            e.apply(c);
            c=a.next();
        }
        assertEquals(new Cordinates(0,4),e.getCoordinate(r1));
    }
    @Test
    public void untilRepeatTest() throws FollowMeParserException, IOException{
        Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
        Map<Robot<Direction>,Cordinates> m =new HashMap<>();
        Map<Figure,Cordinates> f =new HashMap<>();
        m.put(r1,c1);
        f.put(c,new Cordinates(0,5));
        Environment<Robot<Direction>,Figure> e = new Space<>(m,f);
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram(Path.of("src/test/resources/programs/untilRepeatTest"));
        ProgrammableFlowController<Robot<Direction>,Figure> a = handler.buildFlowControllers(List.of(r1),e).get(0);
        Command<Robot<Direction>,Figure> c= a.next();
        int count =0;
        while(!e.getCoordinate(r1).equals(new Cordinates(0,4))){
            e.apply(c);
            c=a.next();
            count++;
        }
        assertEquals(new Cordinates(0,4),e.getCoordinate(r1));
        assertEquals(12,count);//numero di operazioni fatte
    }

    @Test
    public void doForeverTest() throws FollowMeParserException{
        Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
        Map<Robot<Direction>,Cordinates> m =new HashMap<>();
        m.put(r1,c1);
        Environment<Robot<Direction>,Figure> e = new Space<>(m,new HashMap<>());
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram("DO FOREVER \n MOVE 0 -1 1 \n DONE ");
        ProgrammableFlowController<Robot<Direction>,Figure> a = handler.buildFlowControllers(List.of(r1),e).get(0);
        for(int i = 0 ; i < 20 ;i++)
            e.apply(a.next());
        assertEquals(new Cordinates(0,-20),e.getCoordinate(r1));
    }
    @Test
    public void followTest() throws FollowMeParserException, IOException{
        Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
        Robot<Direction>r2 =new Robot<>(new Direction(1,1,1));
        Map<Robot<Direction>,Cordinates> m =new HashMap<>();
        m.put(r1,c1);
        m.put(r2,new Cordinates(1,1));
        Map<Figure,Cordinates> fi = new HashMap<>();
        fi.put(new Circle(0.2,"A"),new Cordinates(1,1));
        Environment<Robot<Direction>,Figure> e = new Space<>(m,fi);
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram(Path.of("src/test/resources/programs/FollowProgram"));
        List<ProgrammableFlowController<Robot<Direction>,Figure>> a = handler.buildFlowControllers(List.of(r1,r2),e);
        a.stream().map(ProgramFlowController::next).forEach(e::apply);
        assertEquals(new Cordinates(1,1),e.getCoordinate(r2));
        while(!r1.getLabel().equals("A"))
            e.apply(a.get(0).next());
        assertEquals("A",r1.getLabel());

    }

    @Test
    void continueTest() throws FollowMeParserException, IOException{
        Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
        Map<Robot<Direction>,Cordinates> m =new HashMap<>();
        m.put(r1,c1);
        Environment<Robot<Direction>,Figure> e = new Space<>(m,new HashMap<>());
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram(Path.of("src/test/resources/programs/continue"));
        List<ProgrammableFlowController<Robot<Direction>,Figure>> a = handler.buildFlowControllers(List.of(r1),e);
        for(int i=0; i<3;i++){
            e.apply(a.get(0).next());
            assertEquals(new Cordinates(i+1,0),e.getCoordinate(r1));
        }
    }
}
