package it.unicam.cs.followme.model;

public class Rectangle implements Figure{

    private final double height;

    private final double base;

    private final String label;



    /**
     *
     * @param label etichetta della figura
     * @param h altezza del rettangolo
     * @param b bade del rettangolo
     */
    public Rectangle(double h, double b, String label){
        if(b<0||h<0) throw new IllegalArgumentException("lunghezze negative");
        this.base=b;
        this.height=h;
        this.label=label;
    }



    /**
     * @return la label del rettangolo
     */
    @Override
    public String getLabel(){
        return label;
    }

    /**
     *
     * @return l'altezza del rettangolo
     */
    public double getHeight(){
        return height;
    }

    /**
     *
     * @return getBase
     */
    public double getBase(){
        return base;
    }

    /**
     * serve per verificare se un punto nello spazio appartene dell'are del rettangolo
     *
     * @param position cordinatga relativa al centro della figura
     * @return true se la posizione si trova all'interno dell'area false altrimenti
     */
    @Override
    public boolean contains(Cordinates position){
        return Math.abs(position.getX())<=this.base/2   &&
               Math.abs(position.getY())<=this.height/2;
    }
    /**
     * @return il tipo della figura dall'enumerazione
     */
    @Override
    public FigureEnumeration getType(){
        return FigureEnumeration.RECTANGLE;
    }
}
