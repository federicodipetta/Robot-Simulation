package it.unicam.cs.followme.model;

public class Circle implements Figure{
    private final double r;

    private final String name;
    /**
     *
     * @param r
     */
    public Circle( double r, String name){
        if(r<0) throw new IllegalArgumentException("un raggio negativo");
        this.r=r;
        this.name=name;
    }
    /**
     * la label contiene la coordinata allora significa che la distanza fra il centro e il punto e'
     * minore o uguale al raggio
     * @param position la posizione da controllare
     */
    @Override
    public boolean contains(Cordinates position){
        double distance = Math.sqrt(
                          Math.pow(  position.getX(), 2)
                                                  +
                          Math.pow(  position.getY(), 2 )
        );

        return distance <= r ;
    }

    @Override
    public String getLabel(){
        return this.name;
    }

    /**
     * @return il tipo della figura dall'enumerazione
     */
    @Override
    public final FigureEnumeration getType(){
        return FigureEnumeration.CIRCLE;
    }

    /**
     * @return il raggio del cerchio
     */
    public double getR(){
        return this.r;
    }

}
