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
    public List<Point> findIntersections(Ray ray) {

        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector u;
        double tm;
        double dsquare;
        double rsquare = radius * radius;
        if (center.equals(p0)) {
            tm = 0;
            dsquare = -tm * tm;
        }
        else {
            u = center.subtract(p0);
            tm = v.dotProduct(u);
            dsquare = u.dotProduct(u) - tm * tm;
        }
        if (dsquare >= rsquare) {
            return null;
        }
        double th = Math.sqrt(rsquare - dsquare);
        if (alignZero(tm + th) > 0) {
            if (alignZero(tm - th) > 0) {
                return List.of(ray.getPoint(tm + th), ray.getPoint(tm - th));
            }
            return List.of(ray.getPoint(tm + th));
        }
        if (alignZero(tm - th) > 0) {
            return List.of(ray.getPoint(tm - th));
        }
        return null;
        //return List.of((tm+th!=0?p0.add(v.scale(tm+th)):null),p0.add(v.scale(tm-th)));
    }
}
