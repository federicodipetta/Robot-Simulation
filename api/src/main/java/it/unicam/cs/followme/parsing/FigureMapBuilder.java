package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.model.Cordinates;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.utilities.ShapeData;

import java.util.List;
import java.util.Map;

/**
 * data una lista di ShapeData genera una mappa di figure con le proprio coordinate
 * @param <F> le figure
 */
public interface FigureMapBuilder<F extends Figure>{
    Map<F,Cordinates> getMap(List<ShapeData> list);
}
