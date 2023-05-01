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
        // TC01: tests for calculation of normal to the sphere//
        assertEquals(new Vector(0, 0, 1), sphere.getNormal(new Point(0, 0, 5)), "ERROR: The calculation of normal to the Sphere is not calculated correctly");

        // TC02: if The vector is normal
        assertEquals(1,sphere.getNormal(new Point(1,1,0)).length(),0.000001,"Error the vector was not normal");

    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1, new Point (2, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(4, 0, 0), new Vector(-4, 0, 4))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(1.2692820528320028, 0, 0.6826794867919993);
        Point p2 = new Point(2.9660120648150565, 0, 0.2584969837962359);
        List<Point> result = sphere.findIntersections(new Ray(new Point(4, 0, 0),
                new Vector(-4, 0, 1)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        p1 = new Point(1.1300909166052993, 0, 0.49321211119293445);
        result = sphere.findIntersections(new Ray(new Point(1.5,0,0), new Vector(-1.5,0,2)));
        assertEquals(result.size(), 1,
                "Wrong number of points");
        assertEquals(List.of(p1), result,
                "Ray crosses sphere");


        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0.5, 0, 0),
                        new Vector(-0.5, 0, 0.2))),
                "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts outside the sphere (0 point)
         result = sphere.findIntersections(new Ray(new Point(1, 0, 1),
         new Vector(-1, 0, 1)));
        assertNull(result, "Wrong number of points");
         assertEquals(null, result, "Ray crosses sphere at one point");


        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1.2928932188134528, 0, 0.7071067811865472), new Vector(-1.29,0,1.29))),
                "Ray's line out of sphere");


        /// **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point(1.2928932188134528, 0, 0.7071067811865472);
        p2 = new Point(2.7071067811865475, 0, -0.7071067811865475);
        result = sphere.findIntersections(new Ray(new Point(3,0,-1), new Vector(-3,0,3)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");


        // TC14: Ray starts at sphere and goes inside (1 point)
        // new Sphere(1d, new Point (1, 0, 0));
         p1 = new Point(1.2928932188134528, 0, 0.7071067811865472);
         result = sphere.findIntersections(new Ray(new Point(2.7071067811865475, 0, -0.7071067811865475), new Vector(-2.71, 0, 2.71)));
         assertEquals(1, result.size(), "Wrong number of points");
         assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC15: Ray starts inside (1 point)
        p1 = new Point(1.2928932188134525, 0, 0.7071067811865475);
        result = sphere.findIntersections(new Ray(new Point(1.6,0,0.4), new Vector(-1.6,0,1.6)));
        assertEquals(result.size(), 1, "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC16: Ray starts at the center (1 point)
        p1 = new Point(1.2928932188134525, 0, 0.7071067811865475);
        result = sphere.findIntersections(new Ray(new Point(2,0,0), new Vector(-2,0,2)));
        assertEquals(result.size(), 1, "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(-1,0,1))),
                "Ray's line out of sphere");

        // TC18: Ray starts at sphere and goes inside (1 points)
         p1 = new Point(1.2, 0, 0.6);
         result = sphere.findIntersections(new Ray(new Point(3,0,0), new Vector(-3,0,1)));
         assertEquals(result.size(), 1, "Wrong number of points");
         assertEquals(List.of(p1), result, "Ray crosses sphere");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1.65,0.23,-1.17), new Vector(-3.82,-0.53,5.02))),
                "Ray's line out of sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(1.23,0.17,-0.62), new Vector(-3.15,0.43,4.13))),
                "Ray's line out of sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,1), new Vector(-1.55,0.21,2.04))),
                "Ray's line out of sphere");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,1), new Vector(1,0,1))),
                "Ray's line out of sphere");

    }
}