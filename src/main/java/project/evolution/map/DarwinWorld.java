package project.evolution.map;

import project.evolution.entities.Animal;
import project.evolution.entities.Plant;
import project.evolution.entities.traits.Direction;
import project.evolution.entities.traits.Vector2d;

import java.util.*;

public class DarwinWorld extends World{
    protected int equatorBottom;
    protected int equatorTop;

    public DarwinWorld(int width, int height, int equatorHeight, int energyForPlant, int everydayPlantAmount, int thrivingAnimaEnergy, int breedingEnergy, int minMutations, int maxMutations, int genotypeSize){
        super(width, height, energyForPlant, everydayPlantAmount, thrivingAnimaEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize);
        this.equatorBottom=Math.max(0,(height-equatorHeight-(height-equatorHeight)%2)/2);
        this.equatorTop=Math.min(height,height-(height-equatorHeight+(height-equatorHeight)%2)/2);
    }

    @Override
    public void move(Animal animal){
        animal.deenergize(1);
        animal.age(1);
        animal.movePrepare();
        Direction currentDirection = animal.getDirection();

        Vector2d currentPosition = animal.getPosition();

        if ((currentPosition.getY()==getMaxHeight() && currentDirection.northDirection()) || (currentPosition.getY()==getMinHeight() && currentDirection.southDirection())){
            animal.giveOppositeDirection();
        }
        else{
            ArrayList<Animal> currentAnimalList = animalsMap.get(currentPosition);
            currentAnimalList.remove(animal);
            if(currentAnimalList.isEmpty()){
                animalsMap.remove(currentPosition);
            }
            else {
                animalsMap.put(currentPosition, currentAnimalList);
            }

            animal.move();
            animal.moveModuloX(this.width);

            ArrayList<Animal> animalList;
            if(animalsMap.containsKey(animal.getPosition())){

                animalList = animalsMap.get(animal.getPosition());
                animalList.add(animal);
            }
            else{
                animalList = new ArrayList<>(List.of(animal));
            }
            animalsMap.put(animal.getPosition(), animalList);
        }
    }

    public Vector2d generatePlatParetoPosition(){
        Random rand = new Random();
        int randomInt = rand.nextInt(10);
        if(randomInt==9 && height>equatorTop){
            return Vector2d.randomPosition(0, width, equatorTop, height);
        }
        else if(randomInt==8 && equatorBottom>0){
            return Vector2d.randomPosition(0, width, 0, equatorBottom);
        }
        return Vector2d.randomPosition(0, width, equatorBottom, equatorTop);
    }

    @Override
    public void generatePlants(){
        for(int i=0; i<everydayPlantAmount; i++){
            Plant plant= new Plant(generatePlatParetoPosition());
            plantsMap.put(plant.getPosition(),plant);
        }
    }
    @Override
    public void generatePlants(int amount){
        for(int i=0; i<amount; i++){
            Plant plant= new Plant(generatePlatParetoPosition());
            plantsMap.put(plant.getPosition(),plant);
        }
    }

    @Override
    public void visualize(){
        Visualizer.visualizer(height,width,animals,plantsMap,equatorBottom,equatorTop);
    }
}
