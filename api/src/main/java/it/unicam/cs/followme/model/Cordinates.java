package it.unicam.cs.followme.model;

import java.util.Objects;

public class Cordinates{

    private final double x;
    private final double y;

    /**
     * costruisce una coordinata date x e y
     * @param x la coordinata delle x
     * @param y la coordinata delle y
     */
    public Cordinates(double x, double y){
        this.x=x;
        this.y=y;
    }

    /**
     * @return ritorna la x
     */
    public double getX(){
        return x;
    }

    /**
     * @return ritorna la y
     */
    public double getY(){
        return y;
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null||getClass()!=o.getClass()) return false;
        Cordinates that=(Cordinates)o;
        return Double.compare(that.x, x)==0&&Double.compare(that.y, y)==0;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public String toString(){
        return "( "+ x + " , "+ y +" )";
    }
}
