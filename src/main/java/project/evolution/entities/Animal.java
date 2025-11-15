package project.evolution.entities;

import project.evolution.entities.traits.Direction;
import project.evolution.entities.traits.Genotype;
import project.evolution.entities.traits.Vector2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Animal implements Entity{

    private Genotype genotype;
    private Vector2d position;
    private Direction direction;
    private int energy;
    private int age;
    private int eatenPlants;
    private List<Animal> children = new ArrayList<>();

    public Animal(Vector2d position, Genotype genotype, int energy) {
        this.position=position;
        this.genotype=genotype;
        this.direction=Direction.N;
        this.energy=energy;
        this.age=0;
        this.eatenPlants=0;
    }


    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Direction getDirection(){
        return direction;
    }

        public Genotype getGenotype(){
        return genotype;
    }

    public int getEnergy(){
        return energy;
    }

    public int getAge(){
        return age;
    }

    public  int getChildrenAmount(){
        return children.size();
    }

    public int getEatenPlants() {
        return eatenPlants;
    }

    public int getDescendantsAmount() {
        if(this.children.isEmpty()){
            return 0;
        }
        HashSet<Animal> animalSet = new HashSet<>();
        animalSet.addAll(this.children);
        getDescendantsAmountRecursive(animalSet);
        return animalSet.size();
    }

    public void getDescendantsAmountRecursive(HashSet<Animal> animalSet) {
        if(!this.children.isEmpty()){
            for(Animal ch : children){
                animalSet.addAll(ch.children);
            }
        }

    }

    public void movePrepare(){
        this.direction=direction.rotate(genotype.use());
    }

    public void move(){
        this.position=position.add(this.direction.toUnitVector2d());
    }

    public void deenergize(int energy){
        this.energy-=energy;
    }

    public void energize(int energy){
        this.energy+=energy;
    }

    public void age(int age){
        this.age+=age;
    }

    public void moveModuloX(int modulo){
        int x = (this.position.getX()+modulo)%modulo;
        int y = this.position.getY();
        this.position=new Vector2d(x,y);
    }

    public void giveOppositeDirection(){
        this.direction=direction.opposite();
    }

    public boolean betterForBreedingThan(Animal animal){
        if(this.energy>animal.energy){
            return true;
        }
        else if (this.energy==animal.energy) {
            if(this.age>animal.age){
                return true;
            }
            else if (this.age==animal.age){
                if(this.getChildrenAmount()>animal.getChildrenAmount()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean thrivingAnimal(int thrivingAnimalEnergy){
        return this.energy>=thrivingAnimalEnergy;
    }

    public void breed(int energyLoss){
        this.energy-=energyLoss;
    }

    public void addChild(Animal chiled){
        this.children.add(chiled);
    }

    public void incrementEatenPlants(){
        eatenPlants+=1;
    }

//    @Override
//    public String getResourceName() {
//        return "animal.png";
//    }

    @Override
    public String getResourceName() {
        return switch(this.direction){
            case N -> "animal0.png";
            case NE -> "animal1.png";
            case E -> "animal2.png";
            case SE -> "animal3.png";
            case S -> "animal4.png";
            case SW -> "animal5.png";
            case W -> "animal6.png";
            case NW -> "animal7.png";
            default -> "animal.png";
        };
    }

    @Override
    public String getEnhancedResourceName(){
        return "animalEnhanced.png";
    }

}
