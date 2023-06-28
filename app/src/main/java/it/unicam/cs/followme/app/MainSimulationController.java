package it.unicam.cs.followme.app;

import it.unicam.cs.followme.controller.Controller;
import it.unicam.cs.followme.controller.SimulationRobotMapBuilder;
import it.unicam.cs.followme.model.*;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.*;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainSimulationController{

    /*
    * e' usata per gestire lo scorrimento dell'interfaccia
    */
    private double entropyX;
    /*
    * e' usata per gestire lo scorrimento nell'interfaccia
     */
    private double entropyY;
    private List<Circle> robot;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    private double cartesianWidth;
    private double cartesianHeight;
    
    private Map<Label,Programmable<Direction>> map;
    
    public static final int WIDTH = 600;
    public static final int HEIGHT = 700;
    @FXML
    private Button step;

    @FXML
    private Button up;

    @FXML
    private Button down;

    @FXML
    private Button left;

    @FXML
    private Button right;

    @FXML
    private Button openFile;

    @FXML
    private Button setTU;

    @FXML
    private Button startFor;

    @FXML
    private Button zoomIn;

    @FXML
    private Button zoomOut;

    @FXML
    private Button clear;

    @FXML
    private TextField sfTextField;

    @FXML
    private TextField tuTextField;

    @FXML
    private Group cartesian;

    private final Controller<Programmable<Direction>,Figure> modelController=Controller.getSimulationController();

    private Map<Label,Figure> figureMap;





    public void initialize() {
        this.figureMap=new HashMap<>();
        this.map =new HashMap<>();
        this.entropyX=0;
        this.entropyY=0;
        init();
    }

    @FXML
    public void pressedKey(Event event){
        KeyEvent keyEvent=(KeyEvent)event;
    if(keyEvent.getCode()==KeyCode.W) up();
    if(keyEvent.getCode()==KeyCode.A) left();
    if(keyEvent.getCode()==KeyCode.D) right();
    if(keyEvent.getCode()==KeyCode.S) down();


    }

    private void right(){
        double dist= xAxis.getUpperBound()-xAxis.getLowerBound();
        changeBounds(0.10*dist,0,0.10*dist,0);
        entropyX+=(0.10*dist);
        initAll();
    }

    private void up(){
        double dist= yAxis.getUpperBound()-yAxis.getLowerBound();
        changeBounds(0,0.10*dist,0,0.10*dist);
        this.entropyY+=(0.10*dist);
        initAll();
    }

    private void left(){
        double dist= xAxis.getUpperBound()-xAxis.getLowerBound();
        changeBounds(-0.10*dist,0,-0.10*dist,0);
        entropyX+=(-0.10*dist);
        initAll();
    }

    private void down(){
        double dist= yAxis.getUpperBound()-yAxis.getLowerBound();
        changeBounds(0,-0.10*dist,0,-0.10*dist);
        this.entropyY+=(-0.10*dist);
        initAll();
    }

    @FXML
    public void onStep(Event event){
        step();
    }

    @FXML
    public void onClear(Event event){
        clear();
    }

    @FXML
    public void onOpenFile(Event event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RobotSelectionFXML.fxml"));
        Parent parent = loader.load();
        SimulationController cont = loader.getController();
        Stage stage = (Stage) (((Node)event.getSource()).getScene().getWindow());
        Scene scene = new Scene(parent,500,600);
        stage.setScene(scene);
        stage.setTitle("CREATE SIMULATION");
        stage.setResizable(false);
        stage.show();
    }


    @FXML
    public void onDown(Event event){
        down();
    }
    @FXML
    public void onUp(Event event){
        up();
    }
    @FXML
    public void onLeft(Event event){
        left();
    }
    @FXML
    public void onRight(Event event){
        right();
    }

    @FXML
    public void onSetTU(Event event){
        try{
            this.setTU(Double.parseDouble(tuTextField.getText()));

        }catch(NumberFormatException e){
            alertError("NUMBER FORMAT",e.getMessage());
        }

    }
    @FXML
    public void onStartFor(Event event) throws InterruptedException{
        int n;
        try{
            n=Integer.parseInt(this.sfTextField.getText());
        }catch(NumberFormatException e){
            alertError("NUMBER FORMAT",e.getMessage());
            n=0;
        }
        for(int i=0;i<n;i++){
            step();
        }

    }
    @FXML
    public void onZoomIn(Event Event){
        if(this.xAxis.getUpperBound()-this.xAxis.getLowerBound()>20){
            changeBounds(+10,+10,-10,-10);
        }
        initAll();
    }

    @FXML
    public void onZoomOut(Event Event){
        if(this.xAxis.getUpperBound()-this.xAxis.getLowerBound()<1000){
            changeBounds(-10,-10,+10,+10);
        }
        initAll();
    }


    private void changeBounds(double lx,double ly,double ux,double uy){
        this.xAxis.setUpperBound(xAxis.getUpperBound()+ux);
        this.yAxis.setUpperBound(yAxis.getUpperBound()+uy);
        this.yAxis.setLowerBound(yAxis.getLowerBound()+ly);
        this.xAxis.setLowerBound(xAxis.getLowerBound()+lx);
    }

    public void setConfig(File prog,File env,int n) throws FollowMeParserException, IOException{
        SimulationRobotMapBuilder builder=new SimulationRobotMapBuilder();
        modelController.start(env,prog,builder.getMap(n));
        initAll();
    }

    public void setTU(double t){
        modelController.setTime_unit(t);
    }
    /*
    * questo metodo serve per andare avanti di uno step nella simulazione
    */
    private void step(){
        this.modelController.step();
        initRobot();
 //       this.map.keySet().forEach(this::moveRobot);
    }

    /*
    * inizializza un piano cartesiano, con gli assi
    */
    private void init(){
        xAxis=new NumberAxis(-10, 10, 1);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setMinorTickVisible(false);
        xAxis.setPrefWidth(WIDTH);//larghezza dell'asse
        xAxis.setLayoutY((HEIGHT-100 )/ 2.0);// serve per metterlo al centro
        yAxis = new NumberAxis(-10, 10, 1);
        yAxis.setSide(Side.LEFT);
        yAxis.setMinorTickVisible(false);//minor tick ad off
        yAxis.setPrefHeight(HEIGHT-100);// diminuisce l'altezza per via delle due Hbox
        yAxis.layoutXProperty().bind(// mette l'asse delle y al centro
                Bindings.subtract(
                        ((WIDTH) / 2) + 1,
                        yAxis.widthProperty()
                )
        );
        cartesian.getChildren().addAll(xAxis,yAxis);
    }

    private void initAll(){
        initRobot();
        initFigure();
    }

    private void initRobot(){
        if(this.map.size()>0)
            this.cartesian.getChildren().removeAll(this.map.keySet());
        map = this.modelController.getRobot().entrySet().stream().
                collect(Collectors.toMap(e->{Label l =new Label( e.getKey().getLabel(),
                                new Circle(2,Color.RED)
                        );
                    l.setLayoutX(mapX(e.getValue().getX()));
                    l.setLayoutY(mapY(e.getValue().getY()));
                    return l;
                    },
                        Map.Entry::getKey
                ));

            this.map.keySet().forEach(e->{
                if(isInX(e.getLayoutX())&&isInY(e.getLayoutY()))
                    this.cartesian.getChildren().add(e);
            });
    }
    /*
    data una misura appartenente a quella del modello la riporta nella rispettiva
    dell'interfaccia
     */
    private double mapX(double x) {
        double lx = xAxis.getPrefWidth() / 2;// prendo la larghezza e la divido così sono nel punto 0
        //prendo la lunghezza e la divido per la distanza tra i bound  cosi' da sapere
        //quanto vale ogni tick
        double tx = xAxis.getPrefWidth() /
                (xAxis.getUpperBound() -
                        xAxis.getLowerBound());
        // ritorno la x - l'entropia della x cioe' il fattore di spostamento orizzontale
        // lo moltiplico per quanto vale ogni tick + dove si trova l'origine
        return (x-entropyX )* tx + lx;
    }
    /*
    data una misura appartenente a quella del modello la riporta nella rispettiva
    dell'interfaccia
     */
    private double mapY(double y) {
        // prendo la larghezza e la divido così sono nel punto 0
        double ly = yAxis.getPrefHeight() / 2;
        //prendo la lunghezza e la divido per la distanza tra i bound cosi' da sapere
        //quanto vale ogni tick
        double ty = yAxis.getPrefHeight() /
                (yAxis.getUpperBound() -
                        yAxis.getLowerBound());
        // ritorno la t - l'entropia della y cioe' il fattore di spostamento verticale
        // lo moltiplico per quanto vale ogni tick + dove si trova l'origine
        return -(y -this.entropyY )* ty + ly-7;//aggiustamento
        //-y poiche' nell'interfaccia le coordinate delle y sono rovesciate rispetto al modello
    }
    /*
    controlla se un punto si trova orizzontalmente nella simulazione
     */
    private boolean isInX(double x){
        return x<mapX(xAxis.getUpperBound())
                && x>mapX(xAxis.getLowerBound());
    }
    /*
    controlla se un punto si trova verticalmente nella simulazione,
    ci sono vari fattori di aggiustamento dovuti alle Hbox
     */
    private boolean isInY(double y){
        return y>mapY(yAxis.getUpperBound())+1
                && y<mapY(yAxis.getLowerBound())-10;
    }

    private void addCircle(Map.Entry<Figure,Cordinates>e){
        it.unicam.cs.followme.model.Circle circle=(it.unicam.cs.followme.model.Circle)e.getKey();
        Circle c = new Circle
                (mapX(circle.getR())-mapX(0),Color.BLUE);
        c.setOpacity(0.5);
        Label label = new Label(circle.getLabel(),c);
        label.setLayoutX(mapX(e.getValue().getX())-c.getRadius());
        label.setLayoutY(mapY(e.getValue().getY())-c.getRadius()/1.35);
        this.figureMap.put(label,circle);
        //controllo se il cerchio e' nella simulazione
        if(isInX(label.getLayoutX()-c.getRadius())&&isInX(c.getRadius()+label.getLayoutX())
           && isInY(label.getLayoutY()-c.getRadius())&&isInY(c.getRadius()+label.getLayoutY()))
            this.cartesian.getChildren().add(label);
    }

    private void initFigure(){
        if(this.figureMap.size()>0)this.cartesian.getChildren().removeAll(this.figureMap.keySet());
        this.figureMap=new HashMap<>();
        List<Map.Entry<Figure,Cordinates>> figures = this.modelController.getFigures().entrySet().stream().toList();
        if(figures.size()!=0)
            figures.forEach(e->{
                if(e.getKey().getType()==FigureEnumeration.CIRCLE)
                    this.addCircle(e);
                else
                    this.addRectangle(e);
            });
    }

    private void addRectangle(Map.Entry<Figure,Cordinates> e){
        Rectangle rectangle=(Rectangle)e.getKey();
        javafx.scene.shape.Rectangle rectangle1 = new javafx.scene.shape.Rectangle
                (mapX(rectangle.getBase())-mapX(0),-mapY(rectangle.getHeight())+mapY(0),Color.RED);
        rectangle1.setOpacity(0.5);

        Label label = new Label(rectangle.getLabel(),rectangle1);
        label.setLayoutX(mapX(e.getValue().getX())-rectangle1.getWidth()/2);
        label.setLayoutY(mapY(e.getValue().getY())-rectangle1.getHeight()/2/1.35);
        this.figureMap.put(label,rectangle);
        if(isInX(label.getLayoutX()-rectangle1.getWidth()/2)&&isInX(label.getLayoutX()+rectangle1.getWidth()/2)&&
                isInY(label.getLayoutY()-rectangle1.getHeight()/2)&&isInY(label.getLayoutY()+rectangle1.getHeight()/2))
            this.cartesian.getChildren().add(label);

    }
    private void clear(){
        this.modelController.clear();
        this.cartesian.getChildren().removeAll(this.cartesian.getChildren());
        initialize();
    }

    Alert a = new Alert(Alert.AlertType.NONE);
    private void alertError(String title,String message){
        a.setAlertType(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setContentText(message);
        a.show();
    }

}
