package it.unicam.cs.followme.model;

import java.util.Objects;

/**
 * questa classe rappresenta un vettore velocita' nello spazio con coordinate relative
 * e un suo modulo, rappresentato dalla velocita' in m/s, quindi quando si applica la traslazione
 * ad punto nello spazio bisogna specificare il tempo in secondi
 */
public class Direction{
    private final double x;
    private final double y;

    private final double speed;

    /**
     * Costruisce la direzione corrispondente, x e y non possono essere entrambi uguali a 0
     * e devono essere compresi tra 1 e -1.
     * Se sono entrambe 0 verra' scelta la direzione 1 1 con velocita' s
     * @param x ascissa
     * @param y ordinata
     * @param s il modulo del vettore
     * @throws IllegalArgumentException  la velocita' e' negativa
     */
    public Direction(double x, double y,double s){
        if(x==0&&y==0)throw new IllegalArgumentException("entrambe le cordinate sono uguali a zero ");
        if(s<0) throw new IllegalArgumentException("velocita' negativa");
        //per normalizzare il valore uso la proprieta' della circonferenza goniometrica di raggio 1
        //cioe' x^2 + y^2 = 1, per mantenere le proporzioni tra x ed y so che (ax)^2 + (ay)^2 = 1
        double a = Math.sqrt(1/ (Math.pow(x,2)+Math.pow(y,2) ));
        this.x=x*a;
        this.y=y*a;
        this.speed=s;
    }

    /**
     * costruttore da una coordinata e una velocita'
     * @param c le coordinate che poi verranno normalizzate rispetto all'origine
     * @param speed il modulo del vettore risultante dalle coordinate
     * @see Direction#Direction(double, double, double)
     */
    public Direction(Cordinates c, double speed){
        this(c.getX(),c.getY(),speed);
    }

    /**
     * applica la direzione prendendo come origine l'origine
     * @return le coordinate della direzione applicate all'origine
     */
    Cordinates apply(){
        return new Cordinates(this.x*this.speed, this.y*this.speed);
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null||getClass()!=o.getClass()) return false;
        Direction direction=(Direction)o;
        return Double.compare(direction.x, x)==0&&Double.compare(direction.y, y)==0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
}
