package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere class which represents the location of a Sphere in space
 *
 * @author Amir Hay and ori
 */
public class Sphere extends RadialGeometry {
    /**
     * The center point of the sphere.
     */
    private final Point center;

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
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        // Get the starting point of the ray and its direction vector.
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        // Initialize some variables.
        Vector u;
        double tm; // the parameter t where the ray intersects the sphere's center
        double dsquare; // the distance squared between the ray and the sphere's center
        double rsquare = radius * radius; // the radius of the sphere squared

        // Check if the ray starts from the center of the sphere.
        if (center.equals(p0)) {
            tm = 0; // the ray intersects the center at t=0
            dsquare = -tm * tm; // the distance squared between the ray and the center is 0
        } else {
            // Calculate the vector between the center of the sphere and the starting point of the ray.
            u = center.subtract(p0);
            // Calculate the parameter t where the ray intersects the sphere's center.
            tm = v.dotProduct(u);
            // Calculate the distance squared between the ray and the sphere's center.
            dsquare = u.dotProduct(u) - tm * tm;
        }

        // Check if the ray misses the sphere.
        if (dsquare >= rsquare) {
            return null;
        }

        // Calculate the distance from the intersection point(s) to the starting point of the ray.
        double th = Math.sqrt(rsquare - dsquare);

        // Check if the ray intersects the sphere at two points.
        if (alignZero(tm + th) > 0) {
            if (alignZero(tm - th) > 0) {
                // Return a list of two intersection points.
                return List.of( new GeoPoint (this, ray.getPoint(tm + th)), new GeoPoint (this, ray.getPoint(tm - th)));
            }
            // Return a list of one intersection point.
            return List.of(new GeoPoint (this, ray.getPoint(tm + th)));
        }

        // Check if the ray intersects the sphere at one point.
        if (alignZero(tm - th) > 0) {
            // Return a list of one intersection point.
            return List.of(new GeoPoint (this, ray.getPoint(tm - th)));
        }

        // If the ray doesn't intersect the sphere, return null.
        return null;
    }
}
