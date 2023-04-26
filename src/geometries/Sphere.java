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
     * a point
     */
    private final  Point center;

    /**
     * Constructor to initialize Sphere based object with a radius and center point
     *
     * @param radius the radius value
     * @param center a point value
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

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
     * Calculates the normal vector of a surface at a given point on a sphere.
     *
     * @param point The point on the surface of the sphere for which the normal vector needs to be calculated.
     * @return The normalized normal vector at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        // Calculate the vector from the center of the sphere to the given point
        Vector vec = point.subtract(center);

        // Normalize the vector to obtain the normal vector
        return vec.normalize();
    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
