package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTests {

    @Test
    void testAdd() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(
                new Point(0, 1, 0),
                p1.add(new Vector(-1, -2, -3)),
                "ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
        assertThrows(IllegalArgumentException.class,
                () -> new Point(1, 1, 1).subtract(new Point(1, 1, 1)));
    }

    @Test
    void testDistance() {
    }

    @Test
    void testDistanceSquared() {
    }

    @Test
    void testTestEquals() {
    }
}