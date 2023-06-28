package it.unicam.cs.followme.model;

/**
 * come applicare una direzione
 */
@FunctionalInterface
public interface DirectionApplier<D extends Direction,C extends Cordinates>{
    /**
     * di default le direzione vengono applicate sommando le coordinate della direzione applicata a un punto
     * (0,0) per la time unit
     */
    DirectionApplier<Direction,Cordinates> DEFAULT_APPLIER =(d,c,x)->{
      Cordinates a =  d.apply();
      return new Cordinates(c.getX()+a.getX()*x,
                          c.getY()+a.getY()*x);
    };
    /**
     *
     * @param direction la direzione
     * @param c le coordinate a cui applicare la traslazione
     * @return le coordinate traslate
     */
    Cordinates apply(D direction, C c,double timeUnit);
}
