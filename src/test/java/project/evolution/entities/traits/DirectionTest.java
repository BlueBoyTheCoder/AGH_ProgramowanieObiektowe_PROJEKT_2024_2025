package project.evolution.entities.traits;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void testOpposite() {
        assertEquals(Direction.S, Direction.N.opposite());
        assertEquals(Direction.NW, Direction.SE.opposite());
        assertEquals(Direction.E, Direction.W.opposite());
        assertEquals(Direction.SW, Direction.NE.opposite());
        assertEquals(Direction.S.opposite(), Direction.N);
        assertEquals(Direction.NW.opposite(), Direction.SE);
        assertEquals(Direction.E.opposite(), Direction.W);
        assertEquals(Direction.SW.opposite(), Direction.NE);
    }

    @Test
    void testToUnitVector2d() {
        assertEquals(new Vector2d(0, 1), Direction.N.toUnitVector2d());
        assertEquals(new Vector2d(1, 1), Direction.NE.toUnitVector2d());
        assertEquals(new Vector2d(1, 0), Direction.E.toUnitVector2d());
        assertEquals(new Vector2d(1, -1), Direction.SE.toUnitVector2d());
        assertEquals(new Vector2d(0, -1), Direction.S.toUnitVector2d());
        assertEquals(new Vector2d(-1, -1), Direction.SW.toUnitVector2d());
        assertEquals(new Vector2d(-1, 0), Direction.W.toUnitVector2d());
        assertEquals(new Vector2d(-1, 1), Direction.NW.toUnitVector2d());
    }

    @Test
    void testGeneToDirection() {
        assertEquals(Direction.N, Direction.geneToDirection(0));
        assertEquals(Direction.NE, Direction.geneToDirection(1));
        assertEquals(Direction.E, Direction.geneToDirection(2));
        assertEquals(Direction.SE, Direction.geneToDirection(3));
        assertEquals(Direction.S, Direction.geneToDirection(4));
        assertEquals(Direction.SW, Direction.geneToDirection(5));
        assertEquals(Direction.W, Direction.geneToDirection(6));
        assertEquals(Direction.NW, Direction.geneToDirection(7));
    }

    @Test
    void testDirectionToValue() {
        assertEquals(0, Direction.N.directionToValue());
        assertEquals(1, Direction.NE.directionToValue());
        assertEquals(2, Direction.E.directionToValue());
        assertEquals(3, Direction.SE.directionToValue());
        assertEquals(4, Direction.S.directionToValue());
        assertEquals(5, Direction.SW.directionToValue());
        assertEquals(6, Direction.W.directionToValue());
        assertEquals(7, Direction.NW.directionToValue());
    }

    @Test
    void testRotate() {
        assertEquals(Direction.N, Direction.N.rotate(0));
        assertEquals(Direction.NE, Direction.N.rotate(1));
        assertEquals(Direction.E, Direction.N.rotate(2));
        assertEquals(Direction.SE, Direction.N.rotate(3));
        assertEquals(Direction.S, Direction.N.rotate(4));
        assertEquals(Direction.SW, Direction.N.rotate(5));
        assertEquals(Direction.W, Direction.N.rotate(6));
        assertEquals(Direction.NW, Direction.N.rotate(7));
        assertEquals(Direction.N, Direction.N.rotate(8));
    }

    @Test
    void testNorthDirection() {
        assertTrue(Direction.N.northDirection());
        assertTrue(Direction.NE.northDirection());
        assertTrue(Direction.NW.northDirection());
        assertFalse(Direction.S.northDirection());
        assertFalse(Direction.SE.northDirection());
        assertFalse(Direction.SW.northDirection());
    }

    @Test
    void testSouthDirection() {
        assertTrue(Direction.S.southDirection());
        assertTrue(Direction.SE.southDirection());
        assertTrue(Direction.SW.southDirection());
        assertFalse(Direction.N.southDirection());
        assertFalse(Direction.NE.southDirection());
        assertFalse(Direction.NW.southDirection());
    }
}
