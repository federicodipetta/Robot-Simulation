package it.unicam.cs.followme.controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BuilderTest{

    @Test
    void buildTest(){
        SimulationRobotMapBuilder builder = new SimulationRobotMapBuilder();
        assertEquals(10,builder.getMap(10).size());
    }
}
