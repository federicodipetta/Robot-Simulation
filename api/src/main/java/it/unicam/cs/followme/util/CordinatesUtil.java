package it.unicam.cs.followme.util;

import it.unicam.cs.followme.model.Cordinates;

public class CordinatesUtil{
    /**
     * calcola la c2 relativamente a c1
     * @param c1 la coordinata alla quale c2 sara' relativa
     * @param c2 c2 la coordinata che diventera' relativa
     * @return la coordinata relativa a c1
     */
    public static Cordinates getRelative(Cordinates c1, Cordinates c2){
        return new Cordinates(c1.getX()-c2.getX(),c1.getY()-c2.getY());
    }
}
