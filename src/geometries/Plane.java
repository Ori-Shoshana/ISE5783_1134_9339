package geometries;

import primitives.*;
import primitives.Ray;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Plane class which represents the location of a plane in space
 *
 * @author Amir Hay and ori
 */
public class Plane implements Geometry {
    /**
     * The point
     */
    private final Point q0;

    /**
     * The noraml
     */
    private final Vector normal;

    /**
     * Constructor to initialize Tube based object with a point and a normal
     *
     * @param q0     the point value
     * @param vector the vector value
     */
    public Plane(Point q0, Vector vector) {
        this.q0 = q0;
        this.normal = vector.normalize();
    }

    /**
     * Constructor to initialize Tube based object with a three point and ...
     *
     * @param point1 first point value
     * @param point2 second point value
     * @param point3 three point value
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q0 = point1;

        Vector U = point1.subtract(point2);
        Vector V = point1.subtract(point3);

        Vector vec = U.crossProduct(V);

        this.normal = vec.normalize();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    public Point getQ0() {
        return q0;
    }

    /**
     * getter
     *
     * Returns the normal vector of the object.
     *
     * @return The normal vector as a {@link Vector} object.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * implementation of {@link geometries.Geometry#getNormal(Point)}
     *
     * Returns the normal vector of the object.
     * @param point A {@link Point} object representing a point on the object. This parameter may be unused
     *              in the implementation of this method.
     * @return The normal vector as a {@link Vector} object.
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    public List<Point> kjcnw(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = normal;

        if (q0.equals(p0)){
            return null;
        }

        Vector P0_Q0 = q0.subtract(p0);

        //numerator
        double np0q0 = alignZero(n.dotProduct(P0_Q0));

        if (isZero(np0q0)){
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        //ray is lying in the plane axis
        if (isZero(nv)){
            return null;
        }

        double t = alignZero(np0q0 / nv);

        if(t <= 0){
            return null;
        }

        Point point = ray.getPoint(t);

        return List.of(point);
    }

}
