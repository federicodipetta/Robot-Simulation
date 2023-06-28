package it.unicam.cs.followme.controller;

import it.unicam.cs.followme.io.SimulationEnvironmentLoader;
import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
public class ControllerTest{



    @Test
    void step() throws FollowMeParserException, IOException{
        Controller<Programmable<Direction>,Figure>c = Controller.getSimulationController();
        File prog = new File("src/test/resources/programs/basicMovement");
        File env = new File("src/test/resources/programs/basicEnviorment");
        Map<Programmable<Direction>,Cordinates> m = new HashMap<>();
        Robot<Direction> r1 =new Robot<>(new Direction(1,0,1));
        Robot<Direction> r2 =new Robot<>(new Direction(0,1,1));
        m.put(r1,new Cordinates(0,0));
        m.put(r2,new Cordinates(0, 1));
        c.start(env,prog,m);
        c.step();
        assertEquals(new Cordinates(1,0),c.getRobot().get(r1));
        assertEquals(new Cordinates(1,1),c.getRobot().get(r2));
        c.step();
        assertEquals(new Cordinates(0,0),c.getRobot().get(r1));
        assertEquals(new Cordinates(0,1),c.getRobot().get(r2));
    }
    @Test
    void clear() throws FollowMeParserException, IOException{
        Controller<Programmable<Direction>,Figure>c = Controller.getSimulationController();
        File prog = new File("src/test/resources/programs/basicMovement");
        File env = new File("src/test/resources/programs/basicEnviorment");
        c.start(env, prog, new SimulationRobotMapBuilder().getMap(10));
        assertEquals(2,c.getFigures().size());
        c.setTime_unit(2);
        c.clear();
        assertEquals(0,c.getRobot().size());
        assertEquals(0,c.getFigures().size());
        assertEquals(Controller.DEFAULT_TIME_UNIT,c.getTime_unit());

    }

    @Test
    void start() throws FollowMeParserException, IOException{
        Controller<Programmable<Direction>,Figure>c = Controller.getSimulationController();
        File prog = new File("src/test/resources/programs/basicMovement");
        File env = new File("src/test/resources/programs/basicEnviorment");
        Map<Programmable<Direction>,Cordinates> m = new HashMap<>();
        Robot<Direction> r1 =new Robot<>(new Direction(1,0,1));
        Robot<Direction> r2 =new Robot<>(new Direction(0,1,1));
        c.start(env, prog, m);
        assertEquals(m,c.getRobot());
        assertEquals(2,c.getFigures().size());
    }


}
