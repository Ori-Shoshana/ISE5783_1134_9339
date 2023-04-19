package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class which represents the location of a Tube in space
 *
 * @author Amir Hay and ori
 */
public class Tube extends RadialGeometry {

    /**
     * Ray
     */
    protected Ray axisRay;

    /**
     * Constructor to initialize Tube based object with a radius and a ray
     *
     * @param radius  the radius value
     * @param axisRay a ray value
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

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

    @Override
    public Vector getNormal(Point point) {

        /** projection of P-O on the ray:
         double t = point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir());
         the normal to the ray is the vector from the projection to P:
         return point.subtract(axisRay.getP0()).subtract(axisRay.getDir().scale(t)).normalize();
         double distance = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
         Point o = axisRay.getP0().add(axisRay.getDir().scale(distance));
         return point.subtract(o).normalize();
         we need to do p- o and then normalize it
         p is the point on the tube
         o is the projection of p on the ray
         we need to find o
         o = p0 + t * v
         T = (p - p0) * v */
        System.out.println("point: " + point);
        System.out.println("axisRay.getP0(): " + axisRay.getP0());
        System.out.println("axisRay.getDir(): " + axisRay.getDir());
        if (point.equals(axisRay.getP0())) {
            throw new IllegalArgumentException("point cannot be equal to axisRay.getP0()");
        }
        double t = point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir());
        System.out.println("t: " + t);
        if (t == 0) {
            //The point is against axe start point
            //return the vector from the point to the start of the ray
            return point.subtract(axisRay.getP0()).normalize();
        }
        Point p = axisRay.getP0().add(axisRay.getDir().scale(t));
        //print the point and the projection
        System.out.println("point: " + point);
        System.out.println("projection: " + p);
        return point.subtract(p).normalize();
    }
}
