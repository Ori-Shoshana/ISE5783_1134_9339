package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere class which represents the location of a Sphere in space
 *
 * @author Amir Hay and ori
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    private final  Point center;

    /**
     * Constructor for a sphere object.
     *
     * @param radius The radius of the sphere.
     * @param center The center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }
    /**
     * Returns the center point of the sphere.
     *
     * @return The center point as a {@link Point} object.
     */
    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }


    /**
     * Calculates and returns the normal vector to the surface of the sphere at a given point.
     *
     * @param point The point on the surface of the sphere at which to calculate the normal vector.
     * @return The normal vector as a {@link Vector} object.
     */
    @Override
    public Vector getNormal(Point point) {
        // Calculate the vector from the center of the sphere to the given point
        Vector vec = point.subtract(center);

        // Normalize the vector to obtain the normal vector
        return vec.normalize();
    }
    /**
     * Finds the intersections of a given {@link Ray} with the sphere.
     *
     * @param ray The {@link Ray} object used to find the intersection.
     * @return A list of {@link Point} objects representing the intersection points, or null if no intersection is found.
     */
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
