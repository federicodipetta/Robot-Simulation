package it.unicam.cs.followme.io;

import it.unicam.cs.followme.controller.SimulationRobotMapBuilder;
import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.model.Programmable;
import it.unicam.cs.followme.parsing.ProgramFlowControllerBuilder;
import it.unicam.cs.followme.parsing.ProgrammableFlowController;
import it.unicam.cs.followme.parsing.ProgrammableFlowControllerBuilder;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ProgLoadTest{

    @Test
    void loadTest() throws FollowMeParserException, IOException{
        ProgrammableFlowControllerBuilder<Programmable<Direction>,Figure>
                bld =new ProgrammableFlowControllerBuilder<>();
        FollowMeParser parser = new FollowMeParser(bld);

        SimulationProgramLoader<Programmable<Direction>,Figure>
                pld=new SimulationProgramLoader<>(bld);

        SimulationEnvironmentLoader<Programmable<Direction>> ld= new SimulationEnvironmentLoader<>(parser);

        SimulationRobotMapBuilder builder = new SimulationRobotMapBuilder();
        List<ProgrammableFlowController<Programmable<Direction>,Figure>>list = pld.getFlowControllers(Path.of("src/test/resources/programs/untilRepeatTest"
                ),ld.getEnvironment(Path.of("src/test/resources/programs/" +
                "basicEnviorment"),builder.getMap(10)));

        assertEquals(10,list.size());
    }
}
