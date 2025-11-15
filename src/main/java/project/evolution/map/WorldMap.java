package project.evolution.map;

import project.evolution.Presenter.MapChangeListener;
import project.evolution.entities.Animal;
import project.evolution.entities.Plant;
import project.evolution.entities.traits.Vector2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public interface WorldMap {

    int getHeight();

    int getWidth();

    void move(Animal animal);

    void place(Animal animal);

    void visualize();

    HashSet<Animal> getAnimalsSet();

    Map<Vector2d, Plant> getPlantsMap();

    Map<Vector2d, ArrayList<Animal>> getAnimalsMap();

    ArrayList<Animal> getDeadAnimalsList();

    Animal animalAt(Vector2d position);

    Plant plantAt(Vector2d position);

    void registerObserver(MapChangeListener observer);

    void notifyObservers(ArrayList<String> notification);

    void killAll();

    void moveAll();

    void feedAll();

    void breedAll();

    void generatePlants();

    void generatePlants(int startingPlantAmount);

    boolean haveAnimals();

    int getGenotypeSize();

    void notifyAboutStatistics();

    int getBreedingEnergy();

    boolean hasAnimalWithGenotypeOnPosition(Vector2d position, ArrayList<Integer> genotype);
}

