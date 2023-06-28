package it.unicam.cs.followme.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class SpaceTest{
    private Robot<Direction>r1 =new Robot<>(new Direction(3,0,1));
    private Robot<Direction>r2 =new Robot<>(new Direction(1,1,1));
    private Robot<Direction>r3 =new Robot<>(new Direction(1,2,1));
    private Figure c = new Circle(1,"A");
    private Figure r = new Rectangle(2,4,"B");
    private Cordinates c1 = new Cordinates(0,0);
    private Cordinates c2 = new Cordinates(0,1);
    private Cordinates c3 = new Cordinates(0.5,0.5);
    private Cordinates c4 = new Cordinates(3,2);
    private Cordinates c5 = new Cordinates(1.01593,1.54909);

    @Test
    void buildRobotTest(){
        Map<Robot<Direction>,Cordinates> m=new HashMap<>();
        m.put(r1,c1);
        m.put(r2,c2);
        m.put(r3,c4);
        Space<Robot<Direction>, Figure> s=new Space<>(m,new HashMap<>());
        assertEquals(c1,s.getCoordinate(r1));
        assertEquals(c2,s.getCoordinate(r2));
        assertEquals(c4,s.getCoordinate(r3));
    }

    @Test
    void buildFigureBuild(){
        Map<Figure,Cordinates> m=new HashMap<>();
        m.put(c,c1);
        m.put(r,c2);
        Space<Robot<Direction>, Figure> s=new Space<>(new HashMap<>(),m);
        assertEquals(2,s.getLabels(c1).size());
        List<Figure> figures = s.getLabels(c3);
        assertTrue(figures.contains(c));
        assertTrue(figures.contains(r));
    }

    @Test
    void getLabels1(){
        Map<Figure,Cordinates> m=new HashMap<>();
        m.put(c,c1);
        m.put(r,c2);
        Space<Robot<Direction>, Figure> s=new Space<>(new HashMap<>(),m);
        List<Figure> figures = s.getLabels(c4);
        assertEquals(0,figures.size());
        figures=s.getLabels(c5);
        assertEquals(1,figures.size());
        assertEquals("B",figures.get(0).getLabel());
        figures = s.getLabels(c2);
        assertEquals(2,figures.size());
    }

    @Test
    public void getLabelE(){
        Map<Figure,Cordinates> m=new HashMap<>();
        Map<Robot<Direction>,Cordinates> r=new HashMap<>();
        m.put(c,c1);
        m.put(this.r,c2);
        r.put(r1,c5);
        r.put(r2,c4);
        r.put(r3,c2);
        Environment<Robot<Direction>,Figure> s= new Space<>(r,m);
        assertEquals(2,s.getLabels(r3).size());
        assertEquals(0,s.getLabels(r2).size());
        assertEquals(1,s.getLabels(r1).size());
        assertEquals("B",s.getLabels(r1).get(0).getLabel());
    }

    @Test
    public void applyTest(){
        Map<Robot<Direction>,Cordinates> r=new HashMap<>();
        r.put(r1,c1);
        Environment<Robot<Direction>,Figure> s= new Space<>(r,new HashMap<>());
        r1.setDirection(new Direction(0,1,1));
        s.apply(e->e.changeMap(r1,
                DirectionApplier.DEFAULT_APPLIER.apply(
                        r1.getDirection(),
                        e.getCoordinate(r1),
                        1.0)));
        assertEquals(new Cordinates(0,1),s.getCoordinate(r1));
    }
    @Test
    public void GetProgrammableWithTest1(){
        Map<Figure,Cordinates> m=new HashMap<>();
        Map<Robot<Direction>,Cordinates> r=new HashMap<>();
        m.put(c,c1);
        m.put(this.r,c2);
        r.put(r1,c1);
        r.put(r2,c4);
        r.put(r3,c2);
        Environment<Robot<Direction>,Figure> s= new Space<>(r,m);
        r1.signalLabel("A");
        r2.signalLabel("B");
        assertEquals(1,s.getProgrammableWith("A",c,c1).keySet().size());
    }
    @Test
    public void GetProgrammableWithTest2(){
        Map<Figure,Cordinates> m=new HashMap<>();
        Map<Robot<Direction>,Cordinates> r=new HashMap<>();
        m.put(c,c1);
        m.put(this.r,c2);
        r.put(r1,c1);
        r.put(r2,c4);
        r.put(r3,c2);
        Environment<Robot<Direction>,Figure> s= new Space<>(r,m);
        r1.signalLabel("A");
        r2.signalLabel("A");
        r3.signalLabel("B");
        assertEquals(2,s.getProgrammableWith("A",new Circle(10,""),c1).keySet().size());
        r3.signalLabel("A");
        assertEquals(3,s.getProgrammableWith("A",new Circle(10,""),c1).keySet().size());
    }

    @Test
    public void getProgrammableMapTest(){
        Map<Robot<Direction>,Cordinates> r=new HashMap<>();
        r.put(r1,c1);
        Environment<Robot<Direction>,Figure> s= new Space<>(r,new HashMap<>());
        assertNotSame(r, s.getProgrammableMap());
        assertEquals(r,s.getProgrammableMap());
    }

    @Test
    public void getFiguresMapTest(){
        Map<Figure,Cordinates> m=new HashMap<>();
        m.put(c,c1);
        m.put(r,c2);
        Environment<Robot<Direction>,Figure> s= new Space<>(new HashMap<>(),m);
        assertNotSame(m,s.getFigures());
        assertEquals(m,s.getFigures());
    }

}
