package geometries;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

class PlaneTests {

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