package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Geometries
 * @author ori shoshana and amir hay
 */
class GeometriesTests {

    /**
     This method tests the findIntersections method of the Geometries class.
     It creates a Geometries object that contains a Sphere, a Plane, and a Triangle.
     Then, it performs Equivalence Partitions and Boundary Values Tests on the findIntersections method.
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries(new Sphere(3,new Point(0,0,1.13)),
                new Plane(new Point(0, 0, 5), new Point(-15,0,0),new Point(0,-10,0)),
                new Triangle(new Point(-2, 0, 0), new Point(1, 0, 0), new Point(-1.18, 3.52, 0)));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Some shapes are cut (but not all the shapes)
        Ray r1 = new Ray(new Point(0,0,-0.63), new Vector(-3.05,5.88,7.65));
        List<Point> res1 = geometries.findIntersections(r1);
        assertEquals(3, res1.size(), "Wrong number of points");


        // =============== Boundary Values Tests ==================
        //TC11: There are no shapes at all
        Geometries g4 = new Geometries();
        Ray r2 = new Ray(new Point(1, 0, 1), new Vector(1, 5, 3));
        assertNull(g4.findIntersections(r2), "There are no shapes");

        //TC12: All the shapes are cut
        Ray r3 = new Ray(new Point(0, 0, -3), new Vector(-3.05, 5.88, 7.65));
        List<Point> res2 = geometries.findIntersections(r3);
        assertEquals(4, res2.size(), "All shapes are cut");

        //TC13: All the shapes are not cut
        Ray r4 = new Ray(new Point(0, 10, 0), new Vector(10, 2, 0));
        List<Point> res3 = geometries.findIntersections(r4);
        assertNull(res3, "All shapes are not cut");

        //TC14: Only one shape is cut
        Ray r5 = new Ray(new Point(0, 10, 0), new Vector(7.43, 0.36, 12.65));
        List<Point> res4 = geometries.findIntersections(r5);
        assertEquals(1, res4.size(), "Only one shape is cut");

    }
}