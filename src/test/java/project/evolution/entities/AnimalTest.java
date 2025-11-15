package project.evolution.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.evolution.entities.traits.Direction;
import project.evolution.entities.traits.Genotype;
import project.evolution.entities.traits.Vector2d;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    private Animal animal;
    private Vector2d initialPosition;
    private Genotype genotype;

    @BeforeEach
    void setUp() {
        initialPosition = new Vector2d(0, 0);
        genotype = new Genotype(1); // Assuming Genotype has a no-args constructor.
        animal = new Animal(initialPosition, genotype, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(initialPosition, animal.getPosition());
        assertEquals(Direction.N, animal.getDirection());
        assertEquals(genotype, animal.getGenotype());
        assertEquals(100, animal.getEnergy());
        assertEquals(0, animal.getAge());
        assertEquals(0, animal.getEatenPlants());
    }

    @Test
    void testIsAt() {
        assertTrue(animal.isAt(initialPosition));
        assertFalse(animal.isAt(new Vector2d(1, 1)));
    }

    @Test
    void testMovePrepare() {
        animal.movePrepare();
        assertNotNull(animal.getDirection());
    }

    @Test
    void testMove() {
        Vector2d expectedPosition = initialPosition.add(Direction.N.toUnitVector2d());
        animal.move();
        assertEquals(expectedPosition, animal.getPosition());
    }

    @Test
    void testDeenergize() {
        animal.deenergize(20);
        assertEquals(80, animal.getEnergy());
    }

    @Test
    void testEnergize() {
        animal.energize(50);
        assertEquals(150, animal.getEnergy());
    }

    @Test
    void testAge() {
        animal.age(5);
        assertEquals(5, animal.getAge());
    }

    @Test
    void testMoveModuloX() {
        animal.moveModuloX(5);
        assertEquals(new Vector2d(0, 0), animal.getPosition());

        animal.move();
        animal.moveModuloX(5);
        assertEquals(new Vector2d(0, 1), animal.getPosition());
    }

    @Test
    void testGiveOppositeDirection() {
        animal.giveOppositeDirection();
        assertEquals(Direction.S, animal.getDirection());
    }

    @Test
    void testBetterForBreedingThan() {
        Animal weakerAnimal = new Animal(new Vector2d(1, 1), genotype, 50);
        assertTrue(animal.betterForBreedingThan(weakerAnimal));
    }

    @Test
    void testThrivingAnimal() {
        assertTrue(animal.thrivingAnimal(80));
        assertFalse(animal.thrivingAnimal(120));
    }

    @Test
    void testBreed() {
        animal.breed(30);
        assertEquals(70, animal.getEnergy());
    }

    @Test
    void testAddChild() {
        Animal child = new Animal(new Vector2d(1, 1), genotype, 50);
        animal.addChild(child);
        assertEquals(1, animal.getChildrenAmount());
    }

    @Test
    void testGetDescendantsAmount() {
        Animal child1 = new Animal(new Vector2d(1, 1), genotype, 50);
        Animal child2 = new Animal(new Vector2d(2, 2), genotype, 50);
        animal.addChild(child1);
        child1.addChild(child2);
        assertEquals(2, animal.getDescendantsAmount());
    }

    @Test
    void testIncrementEatenPlants() {
        animal.incrementEatenPlants();
        assertEquals(1, animal.getEatenPlants());
    }
}
