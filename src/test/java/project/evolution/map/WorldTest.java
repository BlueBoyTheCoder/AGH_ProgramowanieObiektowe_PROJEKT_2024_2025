package project.evolution.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.evolution.entities.Animal;
import project.evolution.entities.Plant;
import project.evolution.entities.traits.Genotype;
import project.evolution.entities.traits.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    private World world;
    private Animal testAnimal;

    @BeforeEach
    void setUp() {
        int width = 10;
        int height = 10;
        int energyForPlant = 10;
        int everydayPlantAmount = 2;
        int thrivingAnimalEnergy = 50;
        int breedingEnergy = 20;
        int minMutations = 1;
        int maxMutations = 3;
        int genotypeSize = 8;

        world = new World(width, height, energyForPlant, everydayPlantAmount, thrivingAnimalEnergy, breedingEnergy, minMutations, maxMutations, genotypeSize);

        testAnimal = new Animal(new Vector2d(5, 5), new Genotype(1), 100);
        world.animals.add(testAnimal);
        world.animalsMap = new HashMap<>();
        world.animalsMap.put(testAnimal.getPosition(), new ArrayList<>(List.of(testAnimal)));
    }

    @Test
    void testMove() {
        Vector2d initialPosition = testAnimal.getPosition();
        world.move(testAnimal);

        assertNotEquals(initialPosition, testAnimal.getPosition());
    }

    @Test
    void testGeneratePlantsDefault() {
        world.generatePlants();
        Map<Vector2d, Plant> plants = world.plantsMap;

        assertEquals(2, plants.size());
        for (Vector2d position : plants.keySet()) {
            assertNotNull(position);
            assertNotNull(plants.get(position));
        }
    }

    @Test
    void testGeneratePlantsCustomAmount() {
        world.generatePlants(1);
        Map<Vector2d, Plant> plants = world.plantsMap;

        assertEquals(1, plants.size());
        for (Vector2d position : plants.keySet()) {
            assertNotNull(position);
            assertNotNull(plants.get(position));
        }
    }

    @Test
    void testAlterAnimalPosition() {
        testAnimal.move();
        world.alterAnimalPosition(testAnimal);

        assertTrue(world.animalsMap.containsKey(testAnimal.getPosition()));
        assertTrue(world.animalsMap.get(testAnimal.getPosition()).contains(testAnimal));
    }

    @Test
    void testFeed() {
        Plant testPlant = new Plant(testAnimal.getPosition());
        world.plantsMap.put(testPlant.getPosition(), testPlant);

        world.feed(testAnimal, testPlant);

        assertEquals(110, testAnimal.getEnergy());
        assertFalse(world.plantsMap.containsKey(testPlant.getPosition()));
    }

    @Test
    void testKill() {
        testAnimal.deenergize(100);
        assertEquals(0, testAnimal.getEnergy());

        world.kill(testAnimal);

        assertFalse(world.animals.contains(testAnimal));
        assertTrue(world.deadAnimals.contains(testAnimal));
    }

    @Test
    void testHaveAnimals() {
        assertTrue(world.haveAnimals());
        testAnimal.deenergize(100);
        world.killAll();
        assertFalse(world.haveAnimals());
    }

    @Test
    void testBreed() {
        Animal femaleAnimal = new Animal(new Vector2d(5, 5), new Genotype(1), 100);
        world.animals.add(femaleAnimal);

        world.breed(testAnimal, femaleAnimal);

        assertEquals(3, world.animals.size());
    }
}
