package project.evolution.entities.traits;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testConstructor() {
        Vector2d vector = new Vector2d(5, 10);
        assertEquals(5, vector.getX());
        assertEquals(10, vector.getY());
    }

    @Test
    void testRandomPosition() {
        int xMin = 0, xMax = 10, yMin = 0, yMax = 10;
        Vector2d randomVector = Vector2d.randomPosition(xMin, xMax, yMin, yMax);

        assertTrue(randomVector.getX() >= xMin && randomVector.getX() < xMax);
        assertTrue(randomVector.getY() >= yMin && randomVector.getY() < yMax);
    }

    @Test
    void testToString() {
        Vector2d vector = new Vector2d(3, 7);
        assertEquals("(3,7)", vector.toString());
    }

    @Test
    void testAdd() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(4, 5);
        Vector2d result = vector1.add(vector2);

        assertEquals(new Vector2d(6, 8), result);
    }

    @Test
    void testEquals() {
        Vector2d vector1 = new Vector2d(5, 10);
        Vector2d vector2 = new Vector2d(5, 10);
        Vector2d vector3 = new Vector2d(10, 5);

        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);
    }

    @Test
    void testHashCode() {
        Vector2d vector1 = new Vector2d(5, 10);
        Vector2d vector2 = new Vector2d(5, 10);

        assertEquals(vector1.hashCode(), vector2.hashCode());
    }
}
