package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Tube class represents a tube object in a 3D space.
 * It is defined by a radius and an axis ray.
 *
 * @author Amir Hay and ori
 */
public class Tube extends RadialGeometry {

    /** The axis ray of the tube. */
    protected Ray axisRay;

    /**
     * Constructs a new Tube object with the specified radius and axis ray.
     *
     * @param radius the radius of the tube.
     * @param axisRay the axis ray of the tube.
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }
    /**
     * Returns the axis ray of the tube.
     *
     * @return the axis ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * Calculates the normal vector of a surface at a given point, based on an axis ray.
     *
     * @param point The point on the surface for which the normal vector needs to be calculated.
     * @return The normalized normal vector at the given point.
     * @throws IllegalArgumentException If the given point is equal to the starting point of the axis ray.
     */
    @Override
    public Vector getNormal(Point point) {

        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        // Check if the given point is equal to the starting point of the axis ray
        if (point.equals(p0)) {
            throw new IllegalArgumentException("point cannot be equal to p0");
        }

        // Calculate the parameter t by taking the dot product of the vector from the
        // starting point of the axis ray to the given point with the direction vector of the axis ray
        double t = alignZero(point.subtract(p0).dotProduct(v));

        // If t is 0, return the normalized vector from the given point to the starting point of the axis ray
        if (t == 0) {
            // The point is against the axis start point
            // Return the vector from the given point to the start of the ray, normalized
            return point.subtract(p0).normalize();
        }

        // Calculate the projection of the given point onto the axis ray by adding the scaled
        // direction vector of the axis ray to the starting point of the axis ray
        Point p = p0.add(v.scale(t));

        // Return the normalized vector from the given point to the calculated projection as the normal vector
        return point.subtract(p).normalize();
    }
    /**
     * Finds the intersections of the given ray with the tube.
     *
     * @param ray the ray to find intersections with.
     * @return a list of points representing the intersection points, or null if there are no intersections.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
