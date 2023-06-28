package it.unicam.cs.followme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest{

    @Test
    void normalizeTest(){
        Direction d1 = new Direction(1,1,1);
        Direction d2 = new Direction(0.5,0.5,1);

        assertEquals(d1.apply(),d2.apply());

    }
}
