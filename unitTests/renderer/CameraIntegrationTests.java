package renderer;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * camera integration tests with a sphere, plane, and triangle.
 *
 * @author  ori shoshana & amir hay
 */
public class CameraIntegrationTests {
    static final Point ZERO_POINT = new Point(0, 0, 0);

    public int intersectionCount(Geometry geometry, Camera camera) {
        int intersections = 0;
        for (int i = 0; i < 3; ++i) {
            for (int y = 0; y < 3; ++y) {
                List<Point> intersectionPoints = geometry.findIntersections(camera.constructRay(3, 3, y, i));
                if (intersectionPoints != null) {
                    intersections += intersectionPoints.size();
                }
            }
        }
        return intersections;
    }

    /**
     * integration tests for constructing a ray through a pixel with a sphere
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void SphereIntegration() {

        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
                3);
        Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1)
                .setVPSize(3, 3);

        // TC01: 2 intersection points
        assertEquals(2,intersectionCount(new Sphere(1, new Point(0, 0, -3)), camera),"ERROR : Wrong amount of intersections" );

        // TC02: 18 intersection points
        assertEquals(18,intersectionCount(new Sphere(2.5, new Point(0, 0, -2.5)), camera2), "ERROR : Wrong amount of intersections");

        // TC03: 10 intersection points
        assertEquals(10,intersectionCount(new Sphere(2, new Point(0, 0, -2)), camera2) , "ERROR : Wrong amount of intersections");

        // TC04: 9 intersection points
        assertEquals(9,intersectionCount(new Sphere(4, new Point(0, 0, -1)), camera2), "ERROR : Wrong amount of intersections");

        // TC05: 0 intersection points
        assertEquals(0,intersectionCount(new Sphere(0.5, new Point(0, 0, 1)), camera), "ERROR : Wrong amount of intersections");

    }

    //private void assertEquals(String string, int i, int intersections) {}

    /**
     * integration tests for constructing a ray through a pixel with a plane
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void PlaneIntegration() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
                3);
        // Tc01: 9 intersection points - plane against camera
        assertEquals(9,intersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 0, 2)), camera), "ERROR : Wrong amount of intersections");

        // TC02: 9 intersection points - plane with small angle
        assertEquals(9,intersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 2, 4)), camera), "ERROR : Wrong amount of intersections");

        // TC03: 6 intersection points - plane parallel to lower rays
        assertEquals(6,intersectionCount(new Plane(new Point(0, 0, -1), new Vector(0, 2, 2)), camera), "ERROR : Wrong amount of intersections");

    }

    /**
     * integration tests for constructing a ray through a pixel with a triangle
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    public void TriangleIntegration() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(1).setVPSize(3,
                3);
        // TC01: 1 intersection point - small triangle
        assertEquals(1,intersectionCount(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), camera),
                "ERROR : Wrong amount of intersections");

        // TC02: 2 intersection points - medium triangle
        assertEquals(2,intersectionCount(new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), camera),
                "ERROR : Wrong amount of intersections");

    }
}