package it.unicam.cs.followme.model;
import it.unicam.cs.followme.util.CordinatesUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TestRectangle{
    private Cordinates c1 = new Cordinates(0,0);
    private Cordinates c2 = new Cordinates(0,1);
    private Cordinates c3 = new Cordinates(0.5,0.5);
    private Cordinates c4 = new Cordinates(3,2);
    private Rectangle r = new Rectangle(1,1,"A");
    private Figure r1 = new Rectangle(2,4,"B");
    private Cordinates c5 = new Cordinates(1.01593,1.54909);

    @Test
    public void testContains(){

        assertTrue(r.contains(c3));
        assertFalse(r.contains(c2));
        assertTrue(r.contains(c1));
        List<String> list=new ArrayList<String>();
    }

    @Test
    public void containsTest2(){

        assertTrue(r1.contains(CordinatesUtil.getRelative(c2,c5)));

    }
}
