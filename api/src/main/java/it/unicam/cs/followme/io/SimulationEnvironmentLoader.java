package it.unicam.cs.followme.io;


import it.unicam.cs.followme.parsing.FigureMapBuilder;
import it.unicam.cs.followme.parsing.RectangleCircleBuilder;
import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class SimulationEnvironmentLoader<E extends Programmable<Direction>>
        implements EnvironmentFileLoader<E,Figure>{
    private final FollowMeParser parser;

    private final FigureMapBuilder<Figure> fb;

    public SimulationEnvironmentLoader(FollowMeParser f){
        this.parser=f;
        this.fb=new RectangleCircleBuilder();
    }

    /**
     * @param shape file contenete i dati delle shape
     * @return un ambiente costruito secondo il file
     */
    @Override
    public Environment<E,Figure> getEnvironment(File shape, Map<E,Cordinates> map ) throws FollowMeParserException, IOException{
        return new Space<>(map,fb.getMap(this.parser.parseEnvironment(shape)));
    }

    /**
     * @param shape path del file contenete i dati delle shape
     * @return un ambiente costruito secondo il path
     */
    @Override
    public Environment<E,Figure> getEnvironment(Path shape, Map<E,Cordinates> map ) throws FollowMeParserException, IOException{
        return new Space<>(map,fb.getMap(this.parser.parseEnvironment(shape)));
    }

    /**
     * @param shape una stinga contenete i dati delle shape
     * @return un ambiente costruito secondo la stringa
     */
    @Override
    public Environment<E,Figure> getEnvironment(String shape, Map<E,Cordinates> map ) throws FollowMeParserException{
        return new Space<>(map,fb.getMap(this.parser.parseEnvironment(shape)));
    }
}
