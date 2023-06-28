package it.unicam.cs.followme.io;


import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * questa interfaccia preso una stringa, un file path, o un file restituisce un ambiente contenete
 * le figure, per i robot la creazione e' lasciata alle implementazioni
 * @param <E> il programmable
 * @param <F> le figure
 */
public interface EnvironmentFileLoader<E extends Programmable<Direction>,F extends Figure>{
    /**
     * @param shape file contenete i dati delle shape
     * @param map mappa contenete i programmable
     * @return un ambiente costruito secondo il file
     */
    Environment<E,F> getEnvironment(File shape, Map<E,Cordinates> map ) throws FollowMeParserException, IOException;

    /**
     *
     * @param shape path del file contenete i dati delle shape
     * @param map mappa contenete i programmable
     * @return un ambiente costruito secondo il path
     */
    Environment<E,F> getEnvironment(Path shape, Map<E,Cordinates> map ) throws FollowMeParserException, IOException;

    /**
     *
     * @param shape una stinga contenete i dati delle shape
     * @param map mappa contenete i programmable
     * @return un ambiente costruito secondo la stringa
     */
    Environment<E,F> getEnvironment(String shape, Map<E,Cordinates> map ) throws FollowMeParserException;

}
