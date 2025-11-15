package project.evolution.Presenter;

import project.evolution.map.WorldMap;

import java.util.ArrayList;

@FunctionalInterface
public interface MapChangeListener {

    void mapChanged(WorldMap worldMap, ArrayList<String> statistics);
}
