package it.unicam.cs.followme.util;

/**
 * questa classe di utilities serve per rappresentare una coppia di elementi non nulli
 * @param <C>
 * @param <V>
 */
public class Pair<C,V>{
    private C first;
    private V second;
    public Pair(C first, V second){
        this.first=first;
        this.second=second;
    }

    public C getFirst(){
        return first;
    }

    public V getSecond(){
        return second;
    }
}
