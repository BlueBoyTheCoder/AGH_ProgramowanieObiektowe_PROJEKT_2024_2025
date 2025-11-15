package project.evolution.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.evolution.entities.Animal;
import project.evolution.entities.Plant;
import project.evolution.entities.traits.Genotype;
import project.evolution.entities.traits.Vector2d;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DarwinWorldTest {

    private DarwinWorld darwinWorld;
    private Animal testAnimal;

    @BeforeEach
    void setUp() {
        int width = 10;
        int height = 5;
        int equatorHeight = 4;
        int energyForPlant = 10;
        int everydayPlantAmount = 2;
        int thrivingAnimalEnergy = 50;
        int breedingEnergy = 20;
        int minMutations = 1;
        int maxMutations = 3;
        int genotypeSize = 8;

        darwinWorld = new DarwinWorld(width, height, equatorHeight, energyForPlant, everydayPlantAmount, thrivingAnimalEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize);

        testAnimal = new Animal(new Vector2d(4, 4), new Genotype(new ArrayList<>(List.of(0,0))), 100);
        darwinWorld.animals.add(testAnimal);
        darwinWorld.animalsMap = new HashMap<>();
        darwinWorld.animalsMap.put(testAnimal.getPosition(), new ArrayList<>(List.of(testAnimal)));
    }

    @Test
    void testMoveAnimalNorthBoundary() {
        assertEquals(new Vector2d(4, 4), testAnimal.getPosition());
        darwinWorld.moveAll();
        assertEquals(new Vector2d(4, 4), testAnimal.getPosition());

        darwinWorld.moveAll();
        assertEquals(new Vector2d(4, 3), testAnimal.getPosition());
    }

    @Test
    void testGeneratePlatParetoPosition() {
        Vector2d position = darwinWorld.generatePlatParetoPosition();
        assertNotNull(position);

        boolean inEquatorZone = position.getY() >= darwinWorld.equatorBottom && position.getY() <= darwinWorld.equatorTop;
        boolean inUpperZone = position.getY() > darwinWorld.equatorTop && position.getY() < darwinWorld.height;
        boolean inLowerZone = position.getY() >= 0 && position.getY() < darwinWorld.equatorBottom;

        assertTrue(inEquatorZone || inUpperZone || inLowerZone);
    }

    @Test
    void testGeneratePlantsDefault() {
        darwinWorld.generatePlants();
        Map<Vector2d, Plant> plants = darwinWorld.plantsMap;

        assertEquals(2, plants.size());
        for (Vector2d position : plants.keySet()) {
            assertNotNull(position);
            assertNotNull(plants.get(position));
        }
    }

    @Test
    void testGeneratePlantsCustomAmount() {
        darwinWorld.generatePlants(1);
        Map<Vector2d, Plant> plants = darwinWorld.plantsMap;

        assertEquals(1, plants.size());
        for (Vector2d position : plants.keySet()) {
            assertNotNull(position);
            assertNotNull(plants.get(position));
        }
    }

    @Test
    void testMoveUpdatesAnimalMap() {
        Vector2d initialPosition = testAnimal.getPosition();
        darwinWorld.move(testAnimal);

        assertTrue(darwinWorld.animalsMap.containsKey(initialPosition));
        assertTrue(darwinWorld.animalsMap.containsKey(testAnimal.getPosition()));

        ArrayList<Animal> animalsAtNewPosition = darwinWorld.animalsMap.get(testAnimal.getPosition());
        assertNotNull(animalsAtNewPosition);
        assertTrue(animalsAtNewPosition.contains(testAnimal));
    }
}