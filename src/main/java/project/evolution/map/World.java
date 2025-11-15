package project.evolution.map;

import project.evolution.Presenter.MapChangeListener;
import project.evolution.Statistics.Statistics;
import project.evolution.entities.Animal;
import project.evolution.entities.Plant;
import project.evolution.entities.traits.Genotype;
import project.evolution.entities.traits.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class World implements WorldMap{

    protected Map<Vector2d, ArrayList<Animal>> animalsMap;
    protected Map<Vector2d, Plant> plantsMap;
    protected HashSet<Animal> animals;
    protected ArrayList<Animal> deadAnimals;
    protected final int width;
    protected final int height;
    protected final int energyForPlant;
    protected final int everydayPlantAmount;
    protected final int thrivingAnimaEnergy;
    protected final int breedingEnergy;
    protected final int minMutations;
    protected final int maxMutations;
    protected final int genotypeSize;

    protected final List<MapChangeListener> observers = new ArrayList<>();


    public World(int width, int height, int energyForPlant, int everydayPlantAmount, int thrivingAnimaEnergy, int breedingEnergy, int minMutations, int maxMutations, int genotypeSize) {
        this.width = width;
        this.height = height;
        this.energyForPlant = energyForPlant;
        this.everydayPlantAmount = everydayPlantAmount;
        this.thrivingAnimaEnergy = thrivingAnimaEnergy;
        this.breedingEnergy = breedingEnergy;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.genotypeSize = genotypeSize;

        this.animalsMap = new HashMap<>();
        this.plantsMap = new HashMap<>();
        this.animals = new HashSet<>();
        this.deadAnimals = new ArrayList<>();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getMaxHeight(){
        return height-1;
    }

    public int getMinHeight(){
        return 0;
    }

    public void alterAnimalPosition(Animal animal){
        ArrayList<Animal> animals = this.animalsMap.get(animal.getPosition());
        if(animals!=null){
            animals.add(animal);
            animalsMap.put(animal.getPosition(),animals);
        }
        else{
            animals = new ArrayList<>(List.of(animal));
            animalsMap.put(animal.getPosition(), animals);
        }
    }

    public void place(Animal animal){
        alterAnimalPosition(animal);
        animals.add(animal);
    }

    public void visualize(){
        Visualizer.visualizer(height,width,animals,plantsMap);
    }

    public void move(Animal animal){
        animal.deenergize(1);
        animal.age(1);
        animal.move();
    };

    public void moveAll(){
        for(Animal a : animals){
            this.move(a);
        }
    }

    public void kill(Animal animal){
        ArrayList<Animal> animalList = animalsMap.get(animal.getPosition());
        animalList.remove(animal);
        if(animalList.isEmpty()){
            animalsMap.remove(animal.getPosition());
        }
        animals.remove(animal);
        deadAnimals.add(animal);
    }

    public void killAll(){
        Iterator<Animal> animalIterator = animals.iterator();
        while(animalIterator.hasNext()){
            Animal animal = animalIterator.next();
            if(animal.getEnergy()==0){
                animalIterator.remove();
                kill(animal);
            }
        }
    }

    public void breed(Animal male, Animal female){
        male.breed(breedingEnergy);
        female.breed(breedingEnergy);
        double genesProportion = (double) male.getEnergy() /(male.getEnergy()+female.getEnergy());
        Genotype childGenotype=male.getGenotype().getChildGenotype(female.getGenotype(),genesProportion);
        childGenotype.mutate(minMutations,maxMutations);
        Animal child = new Animal(male.getPosition(),childGenotype,breedingEnergy*2);
        this.animals.add(child);
        this.alterAnimalPosition(child);
        male.addChild(child);
        female.addChild(child);
    }

    public void breedAll(){
        for (Map.Entry<Vector2d,ArrayList<Animal>> a : animalsMap.entrySet()) {
            ArrayList<Animal>animalList=a.getValue();
            animalList=getThrivingAnimals(animalList);
            if(animalList!=null) {
                animalList = getBestAnimalPair(animalList);
                if(animalList!=null) {
                    breed(animalList.get(0),animalList.get(1));
                }
            }

        }
    }

    public void feed(Animal animal, Plant plant){
        animal.energize(energyForPlant);
        animal.incrementEatenPlants();
        plantsMap.remove(plant.getPosition());
    }

    public void feedAll(){
        for (Map.Entry<Vector2d,ArrayList<Animal>> a : animalsMap.entrySet()) {
            if(plantsMap.containsKey(a.getKey())) {
                ArrayList<Animal> animalList = a.getValue();
                if (animalList != null) {
                    Animal animal = getBestAnimal(animalList);
                    if (animal != null) {
                        feed(animal,plantsMap.get(a.getKey()));
                    }
                }
            }

        }
    }

    public void notifyAboutStatistics(){
        ArrayList<String> statistics = Statistics.getStatistics(this,3);
        notifyObservers(statistics);
    }

    public ArrayList<Animal> getThrivingAnimals(ArrayList<Animal> animals){
        return animals.stream().filter(animal -> animal.thrivingAnimal(thrivingAnimaEnergy)).collect(Collectors
                .toCollection(ArrayList::new));
    }

    public ArrayList<Animal> getBestAnimalPair(ArrayList<Animal> animals){
        List<Animal> thrivingAnimals = getThrivingAnimals(animals);
        if(thrivingAnimals.size()<2){
            return null;
        }
        Animal male;
        Animal female;
        if(thrivingAnimals.get(0).betterForBreedingThan(thrivingAnimals.get(1))){
            male=thrivingAnimals.get(0);
            female=thrivingAnimals.get(1);
        }
        else{
            male=thrivingAnimals.get(1);
            female=thrivingAnimals.get(0);
        }
        for(Animal a : thrivingAnimals.subList(2,thrivingAnimals.size())){
            if(a.betterForBreedingThan(male)){
                female=male;
                male=a;
            } else if(a.betterForBreedingThan(female)){
                female=a;
            }
        }

        return new ArrayList<>(List.of(male,female));
    }

    public Animal getBestAnimal(ArrayList<Animal> animals){
        if(animals.isEmpty()){
            return null;
        }
        Animal animal=animals.getFirst();
        for(Animal a : animals.subList(1,animals.size())){
            if(a.betterForBreedingThan(animal)){
                animal=a;
            }
        }

        return animal;
    }


    public void generatePlants(){
        for(int i=0; i<everydayPlantAmount; i++){
            Plant plant= new Plant(Vector2d.randomPosition(0, width, 0, height));
            plantsMap.put(plant.getPosition(),plant);
        }
    }

    public void generatePlants(int amount){
        for(int i=0; i<amount; i++){
            Plant plant= new Plant(Vector2d.randomPosition(0, width, 0, height));
            plantsMap.put(plant.getPosition(),plant);
        }
    }

    public boolean haveAnimals(){
        return !animals.isEmpty();
    }

    public HashSet<Animal> getAnimalsSet() {
        return animals;
    }

    public Map<Vector2d, Plant> getPlantsMap() {
        return plantsMap;
    }

    public Map<Vector2d, ArrayList<Animal>> getAnimalsMap() {
        return animalsMap;
    }

    public ArrayList<Animal> getDeadAnimalsList() {
        return deadAnimals;
    }

    public Animal animalAt(Vector2d position){
        if(animalsMap.containsKey(position)){
            return animalsMap.get(position).getLast();
        }
        else{
            return null;
        }
    }

    public Plant plantAt(Vector2d position){
        if(plantsMap.containsKey(position)){
            return plantsMap.get(position);
        }
        else{
            return null;
        }
    }

    public void registerObserver(MapChangeListener observer){
        observers.add(observer);
    }

    public void notifyObservers(ArrayList<String> statistics){
        for(MapChangeListener observer: observers){
            observer.mapChanged(this, statistics);
        }
    }

    public int getGenotypeSize(){
        return genotypeSize;
    }

    @Override
    public int getBreedingEnergy() {
        return breedingEnergy;
    }

    public boolean hasAnimalWithGenotypeOnPosition(Vector2d position, ArrayList<Integer> genotype){
        for(Animal a : animalsMap.get(position)){
            if(a.getGenotype().getGenes().equals(genotype)){
                return true;
            }
        }
        return false;
    }
}
