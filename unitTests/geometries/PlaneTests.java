package geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * Testing Plane
 * @author ori shoshana and amir hay
 */
class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}.
     */
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

    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Three non-collinear points
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 3, 4);
        Point p3 = new Point(-1, 2, 0);
        Plane plane = new Plane(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(), "");
        // generate the test result
        Vector result = plane.getNormal();
        // ensure the result is not the zero vector
        assertFalse(result.equals(Double3.ZERO), "Plane's normal is the zero vector");
    }

}