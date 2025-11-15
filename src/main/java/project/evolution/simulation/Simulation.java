package project.evolution.simulation;

import project.evolution.Statistics.CsvWriter;
import project.evolution.Statistics.Statistics;
import project.evolution.entities.Animal;
import project.evolution.entities.traits.Genotype;
import project.evolution.entities.traits.Vector2d;
import project.evolution.map.WorldMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation implements Runnable{
    private String path;
    private boolean pause=false;
    private final int startingAnimalAmount;
    private final int startingAnimalEnergy;
    private final int startingPlantAmount;
    CsvWriter writer;
    private WorldMap map=null;

    public Simulation(int startingAnimalAmount, int startingAnimalEnergy, int startingPlantAmount, WorldMap map, String path) {
        this.startingAnimalAmount=startingAnimalAmount;
        this.startingAnimalEnergy=startingAnimalEnergy;
        this.startingPlantAmount=startingPlantAmount;
        this.path=path;
        this.map = map;
        for(int i=0; i<startingAnimalAmount; i++) {
            map.place(new Animal(Vector2d.randomPosition(0,map.getWidth(),0,map.getHeight()), new Genotype(map.getGenotypeSize()),startingAnimalEnergy));
        }
        map.generatePlants(startingPlantAmount);
        List<String> columns = Arrays.asList("Animals amount", "Plants amount", "Free tiles amount", "Average energy", "Dead animals average age", "Average children amount", "Most popular genotypes");
        try {
            writer = new CsvWriter(path+"data.csv", columns);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void run(){
        while(true) {
            while (!pause) {
                map.killAll();
                map.moveAll();
                map.feedAll();
                map.breedAll();
                map.generatePlants();
                if(!path.isEmpty()) {
                    ArrayList<String> str = Statistics.getStatistics(map, 3);
                    writer.saveDateToFile(str);
                }
                map.notifyAboutStatistics();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setPause() {
        pause = !pause;
    }

    public void setSaveToCsv(String path) {
        this.path = path;
    }
}
