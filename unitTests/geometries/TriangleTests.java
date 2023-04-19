package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Triangle
 * @author ori shoshana and amir hay
 */

class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
    //=== Equivalence Partitions Tests ===
    //TC01: normal to triangle
        Point p1 = new Point(1, 0, 0);
        Point p2 = new Point(0, 1, 0);
        Point p3 = new Point(0, 0, 1);
        Triangle triangle = new Triangle(p1, p2, p3);
        Vector vec = new Vector(0.5, 0.5, 0.5).normalize();
        assertTrue((triangle.getNormal(new Point(0.5, 0.5, 0.5)).equals(vec)) ||
                (triangle.getNormal(new Point(8.5, 0.5, 0.5)).equals(vec.scale(-1.8))), "ERROR: getNormal() wrong value");
        //TC02 if the vector is normal
        assertTrue(triangle.getNormal(p1).length() == 1, "ERROR: the vector was not normal");
    }
}