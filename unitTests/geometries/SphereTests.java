package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Sphere
 * @author ori shoshana and amir hay
 */
class SphereTests {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {

        Sphere sphere = new Sphere(5, new Point(0, 0, 0));

        // ============ Equivalence Partitions Tests ==============//
        // TC01: tests for calculation of normal to the plane//
        assertEquals(new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 5)), "ERROR: The calculation of normal to the Sphere is not calculated correctly");

        // TC02: if The vector is normal
        assertEquals(1,sphere.getNormal(new Point(1,1,0)).length(),0.000001,"Error the vector was not normal");

    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        p1 = new Point(1.1937129433613967, 0.6937129433613968, 0.6937129433613968);
        result = sphere.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,1,1)));
        assertEquals(result.size(), 1,
                "Wrong number of points");
        assertEquals(List.of(p1), result,
                "Ray crosses sphere");


        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(4, 0, 0),
                        new Vector(1, 0, 0))),
                "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
         p1 = new Point(1.577350269149021, 0.5773502691829591, 0.577350269236897);
         result = sphere.findIntersections(new Ray(new Point(1.267261242, 0.5345224838, 0.8017837257),
         new Vector(0.3100890272, 0.04282778539, -0.2244334565)));
         assertEquals(1, result.size(), "Wrong number of points");
         assertEquals(List.of(p1), result, "Ray crosses sphere at one point");


        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(1,0,1))),
                "Ray's line out of sphere");


        /// **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point(0, 0, 0);
        p2 = new Point(2, 0, 0);
        result = sphere.findIntersections(new Ray(new Point(-1,0,0), new Vector(1,0,0)));
        assertEquals(result.size(), 2, "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");


        // TC14: Ray starts at sphere and goes inside (1 point)
        // new Sphere(1d, new Point (1, 0, 0));
         p1 = new Point(1, 0, -1);
         result = sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, -1)));
         assertEquals(1, result.size(), "Wrong number of points");
         assertEquals(List.of(new Point(1, 0, -1)), result, "Ray crosses sphere");

        // TC15: Ray starts inside (1 point)
        p1 = new Point(1, -1, 0.0);
        result = sphere.findIntersections(new Ray(new Point(1,0.5,0), new Vector(0,-1,0)));
        assertEquals(result.size(), 1, "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC16: Ray starts at the center (1 point)
        p1 = new Point(1.5773502691896257, 0.5773502691896258, 0.5773502691896258);
        result = sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(1,1,1)));
        assertEquals(result.size(), 1, "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");


        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,-1,0), new Vector(0,-1,0))),
                "Ray's line out of sphere");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,-2,0), new Vector(0,-1,0))),
                "Ray's line out of sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(-1,-1,0), new Vector(1,0,0))),
                "Ray's line out of sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1,-1,0), new Vector(1,0,0))),
                "Ray's line out of sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,-1,0), new Vector(-1,0,0))),
                "Ray's line out of sphere");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(0,1,0))),
                "Ray's line out of sphere");

    }
}