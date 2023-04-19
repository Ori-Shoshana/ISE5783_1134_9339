package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {
    @Test
    void testPlane() {
        // ====Boundary Values Tests ====
        // TC10: two points are the same
        Point p1 = new Point( 1,  2, 3);
        Point p2 = new Point( 1,  2,  3);
        Point p3= new Point( 2,  3,  4);
        assertThrows (IllegalArgumentException.class, ()->new Plane (p1, p2, p3), "ERROR: two points are the same");
        //TC11: three points are on the same line
        Point p4 = new Point(1,  1,  1);
        Point p5 = new Point( 1,  1,  2);
        Point p6 = new Point( 1,  1,  3);
        assertThrows (IllegalArgumentException.class, () -> new Plane (p4, p5, p6), "ERROR: three points are on the same line");
    }
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