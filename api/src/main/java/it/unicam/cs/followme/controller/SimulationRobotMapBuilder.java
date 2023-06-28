package it.unicam.cs.followme.controller;

import it.unicam.cs.followme.model.Cordinates;
import it.unicam.cs.followme.model.Direction;
import it.unicam.cs.followme.model.Programmable;
import it.unicam.cs.followme.model.Robot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * crea dei una mappa di programmable e coordinate
 */
public class SimulationRobotMapBuilder{

    /**
     * genera una mappa di programmable
     * @param n numero dei 7programmable da generare
     * @param seed seed per il generatore casuale
     * @return la mappa di programmable con le loro posizione generate
     *         in maniera pseudo randomica secondo il seed
     */
    public Map<Programmable<Direction>,Cordinates> getMap(int n, long seed){
        Random random=new Random(seed);
        return getMap(n, random);
    }

    /**
     * genera una mappa di programmable
     * @param n numero dei robot generati
     * @return ritorna una mappa di programmable con la loro posizione generate
     *         in maniera pseudo randomica
     */
    public Map<Programmable<Direction>,Cordinates> getMap(int n){
        return getMap(n, new Random());
    }

    /**
     * genera una mappa di programmable
     * @param n il numero di programmable
     * @param random un oggetto Random che verra' usato per la generazione
     * @return ritorna una mappa di programmable con la loro posizione generate
     *         in maniera pseudo randomica
     */
    public Map<Programmable<Direction>,Cordinates> getMap(int n, Random random){
        Map<Programmable<Direction>,Cordinates>map = new HashMap<>();
        for(int i=0; i<n;i++)
            map.put(new Robot<>(
                    new Direction(random.nextDouble(),random.nextDouble(),random.nextDouble())),
            new Cordinates(random.nextDouble()*random.nextInt(-5,5),random.nextDouble()*random.nextInt(-5, 5))
            );
        return map;
    }
}
