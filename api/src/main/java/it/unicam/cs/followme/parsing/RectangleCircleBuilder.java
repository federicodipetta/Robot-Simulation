package it.unicam.cs.followme.parsing;

import it.unicam.cs.followme.model.Circle;
import it.unicam.cs.followme.model.Cordinates;
import it.unicam.cs.followme.model.Figure;
import it.unicam.cs.followme.model.Rectangle;
import it.unicam.cs.followme.utilities.ShapeData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * questa classe implementa FigureMapBuilder ed e' in grado di costruire cerchi o rettangoli
 */
public class RectangleCircleBuilder implements FigureMapBuilder<Figure>{
    /**
     *
     * @param list una lista di shapeData
     * @return una mappa di figure, Cerchi e Rettangoli, con le loro coordinate del centro
     */
    @Override
    public Map<Figure,Cordinates> getMap(List<ShapeData> list){
        return list.stream()
                .collect(Collectors.toMap(
                        k-> k.shape().equals("RECTANGLE")?new Rectangle(k.args()[3],k.args()[2],k.label())
                        : new Circle(k.args()[2],k.label()),
                        v -> new Cordinates(v.args()[0],v.args()[1])
                        ));
    }
}
