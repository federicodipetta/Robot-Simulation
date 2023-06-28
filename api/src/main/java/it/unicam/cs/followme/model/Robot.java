package it.unicam.cs.followme.model;

public class Robot<D extends Direction> implements Programmable<D>{

    private D direction;
    private String label;

    /**
     * data una direzione costruisce un robot
     * @param direction la direzione iniziale del robot
     */
    public Robot(D direction){
        this.direction=direction;
        this.label="";
    }

    /**
     * @return la direzione del robot
     */
    @Override
    public D getDirection(){
        return this.direction;
    }

    /**
     *
     * @param direction la nuova direzione
     */
    @Override
    public void setDirection(D direction){
        this.direction=direction;
    }

    /**
     * @return l'etichetta che sta segnalando
     */
    @Override
    public String getLabel(){
        return this.label;
    }


    /**
     * @param label l'etichetta che il robot deve segnalare
     */
    @Override
    public void signalLabel(String label){
        if(label.matches("^[[a-zA-Z0-9]+ | _+]+"))
            this.label=label;

    }

    /**
     * @param label l'etichetta che il robot deve smettere di segnalare
     */
    @Override
    public void unsignalLabel(String label){
        if(this.label.equals(label)) this.label="";
    }




}
