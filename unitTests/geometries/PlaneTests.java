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
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2, 3, 4);
        Point p3 = new Point(-1, 2, 0);
        Plane plane = new Plane(p1, p2, p3);
        Vector vec = new Vector(1,-1,0).normalize();
        // TC01: Three non-collinear points
        assertTrue((plane.getNormal().equals(vec) || plane.getNormal().equals(vec.scale(-1.0))),"ERROR: The calculation of normal to the plane is not calculated correctly");

        // TC02: if The vector is normal
        assertEquals(1,plane.getNormal(new Point(2,1,0)).length() ,0.000001,"Error the vector was not normal");

    }

}