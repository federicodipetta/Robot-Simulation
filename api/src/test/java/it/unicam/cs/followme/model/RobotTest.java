package it.unicam.cs.followme.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class RobotTest{

    @Test
    void labelSignalTest(){
        Robot<Direction> r1=new Robot<Direction>(new Direction(1,1,0));
        r1.signalLabel("asso_piglia_tutto");

        assertEquals("asso_piglia_tutto",r1.getLabel());
        r1.signalLabel("NON_SONO_CORRETTA01_:");
        assertNotEquals("NON_SONO_CORRETTA01_:",r1.getLabel());
        r1.signalLabel("_A");
        assertEquals("_A",r1.getLabel());
    }


}
