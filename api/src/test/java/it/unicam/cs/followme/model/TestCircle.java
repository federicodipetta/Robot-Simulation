package it.unicam.cs.followme.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCircle{

    @Test
    public void testContains(){
        Cordinates c1 = new Cordinates(0,0);
        Cordinates c2 = new Cordinates(0,1);
        Cordinates c3 = new Cordinates(0.5,0.5);
        Cordinates c4 = new Cordinates(3,2);
        Circle circle = new Circle( 1, "A");
        assertTrue(circle.contains(c2));
        assertTrue(circle.contains(c3));
        assertFalse(circle.contains(c4));
    }

}
