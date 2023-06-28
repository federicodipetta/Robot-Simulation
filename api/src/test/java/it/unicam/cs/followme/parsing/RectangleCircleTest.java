package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleCircleTest{

    @Test
    public void mapBuildTest() throws FollowMeParserException, IOException{
        RectangleCircleBuilder b= new RectangleCircleBuilder();
        FollowMeParser f = new FollowMeParser(new ProgrammableFlowControllerBuilder<>());
        Map<Figure,Cordinates> m= b.getMap(f.parseEnvironment(Path.of("src\\test\\resources\\programs\\basicEnviorment")));
        Environment<Robot<Direction>,Figure> e = new Space<>(new HashMap<>(),m);
        assertEquals("A", e.getLabels(new Cordinates(0,0)).get(0).getLabel());
        assertEquals("A", e.getLabels(new Cordinates(1.505,1.123)).get(0).getLabel());
    }
}
