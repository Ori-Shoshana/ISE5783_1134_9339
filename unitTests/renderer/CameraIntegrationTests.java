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

    public void intersectionCount(Geometry geometry, Camera camera, int expected, String testName) {
        int intersections = 0;
        for (int i = 0; i < 3; ++i) {
            for (int y = 0; y < 3; ++y) {
                List<Point> intersectionPoints = geometry.findIntersections(camera.constructRay(3, 3, y, i));
                if (intersectionPoints != null) {
                    intersections += intersectionPoints.size();
                }
            }
        }
        assertEquals( expected , intersections , "ERROR" + testName + ": Wrong amount of intersections");
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
        intersectionCount(new Sphere(1, new Point(0, 0, -3)), camera, 2, "TC01");

        // TC02: 18 intersection points
        intersectionCount(new Sphere(2.5, new Point(0, 0, -2.5)), camera2, 18, "TC02");

        // TC03: 10 intersection points
        intersectionCount(new Sphere(2, new Point(0, 0, -2)), camera2, 10, "TC03");

        // TC04: 9 intersection points
        intersectionCount(new Sphere(4, new Point(0, 0, -1)), camera2, 9, "TC04");

        // TC05: 0 intersection points
        intersectionCount(new Sphere(0.5, new Point(0, 0, 1)), camera, 0, "TC05");

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
        intersectionCount(new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)), camera, 9, "TC01");

        // TC02: 9 intersection points - plane with small angle
        intersectionCount( new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)), camera, 9, "TC02");

        // TC03: 6 intersection points - plane parallel to lower rays
        intersectionCount( new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), camera, 6, "TC03");

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
        intersectionCount(new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), camera, 1,
                "TC01");

        // TC02: 2 intersection points - medium triangle
        intersectionCount(new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2)), camera, 2,
                "TC02");

    }
}