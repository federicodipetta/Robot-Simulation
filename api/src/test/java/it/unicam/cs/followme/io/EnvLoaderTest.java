package it.unicam.cs.followme.io;

import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.model.Programmable;
import it.unicam.cs.followme.parsing.ProgrammableFlowController;
import it.unicam.cs.followme.parsing.ProgrammableFlowControllerBuilder;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
public class EnvLoaderTest{

    @Test
    void loaderTest() throws FollowMeParserException, IOException, URISyntaxException{
        FollowMeParser parser = new FollowMeParser(new ProgrammableFlowControllerBuilder<Programmable<Direction>, Figure>());

        SimulationEnvironmentLoader<Programmable<Direction>> ld= new SimulationEnvironmentLoader<>(parser);

        ld.getEnvironment(Path.of("src/test/resources/programs/" +
                "basicEnviorment"),new HashMap<>());
        new File(this.getClass().getResource("wow").toURI());
    }
}
