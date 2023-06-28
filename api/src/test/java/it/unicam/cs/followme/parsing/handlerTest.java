package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class handlerTest{
    private Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
    private Robot<Direction>r2 =new Robot<>(new Direction(1,1,1));
    private Robot<Direction>r3 =new Robot<>(new Direction(1,2,1));

    Cordinates c1 = new Cordinates(0,0);
    Cordinates c2 = new Cordinates(0,1);
    Cordinates c3 = new Cordinates(0.5,0.5);
    Cordinates c4 = new Cordinates(3,2);
    private Map<Robot<Direction>,Cordinates> m;
    private Map<Figure,Cordinates> f;

    @Test
    void handlerTest1() throws FollowMeParserException{
        m=new HashMap<>();
        m.put(r1,c1);
        Environment<Robot<Direction>,Figure> e = new Space<>(m,new HashMap<>());
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram("MOVE 0 1 1");
        List<ProgrammableFlowController<Robot<Direction>,Figure>> a = handler.buildFlowControllers(m.keySet().stream().toList(),e);
        e.apply(a.get(0).next());
        assertEquals(e.getCoordinate(r1),c2);
    }


    @Test
    public void handlerTest2() throws FollowMeParserException{
        //testa se il repeat e' costruito in modo corretto
        m=new HashMap<>();
        m.put(r1,c1);
        Environment<Robot<Direction>,Figure> e = new Space<>(m,new HashMap<>());
        ProgrammableFlowControllerBuilder<Robot<Direction>,Figure> handler = new ProgrammableFlowControllerBuilder<>();
        FollowMeParser p= new FollowMeParser(handler);
        p.parseRobotProgram("REPEAT 2\nMOVE 0 1 1\nDONE\n");
        ProgrammableFlowController<Robot<Direction>,Figure> a = handler.buildFlowControllers(List.of(r1),e).get(0);
        Command<Robot<Direction>,Figure> c= a.next();
        int cont=0;
        while(!a.isEnd()){
            e.apply(c);
            c=a.next();
            cont++;
        }
        assertEquals(2,cont);
    }
}
