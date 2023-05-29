package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;


/** Cylinder class which represents the location of a cylinder in space
 * @author Amir Hay and ori */
public class Cylinder extends Tube {

    /** the hieght of the Cylinder*/
    private double height;

    /** Constructor to initialize Sphere based object with a radius and height
     * @param radius the radius value
     * @param axisRay the ray
     * @param height the hieght of the Cylinder value */
    public Cylinder(double radius,Ray axisRay, double height) {
        super(radius,axisRay);
        this.height = height;
    }
    /**
     * @return the height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * Calculates and returns the normal vector of the object at the given point.
     * The normal vector is calculated based on the axis of the object and the given point.
     *
     * @param point A {@link Point} object representing the point at which the normal vector is calculated.
     * @return The normal vector as a {@link Vector} object.
     * @throws UnsupportedOperationException if the normal vector cannot be calculated for the object.
     */
    @Override
    public Vector getNormal(Point point) throws UnsupportedOperationException {
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        // Check if the given point is the same as the base point of the axis
        if (point.equals(p0))
            return v;

        // Calculate the projection of (point - p0) onto the axis ray
        Vector u = point.subtract(p0);

        // Calculate the distance from p0 to the object in front of the given point
        double t = alignZero(u.dotProduct(v));

        // If the given point is at the base of the object or at the top of the object
        if (t == 0 || isZero(height - t))
            return v;

        // Calculate the other point on the axis facing the given point
        Point o = p0.add(v.scale(t));

        // Calculate the normalized vector from the given point to the other point on the axis
        return point.subtract(o).normalize();
    }
    /**
     * Calculates and returns a list of intersection points between the Cylinder and a given Ray.
     *
     * @param ray The Ray to intersect with the Cylinder.
     * @param maxDistance the maximum distance between the point to the start of the ray
     * @return A list of Point objects representing the intersection points between the Cylinder and the Ray.
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        return null;
    }
}
