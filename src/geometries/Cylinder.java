package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

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

    @Override
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        if (point.equals(p0))
            return v;

        // projection of P-p0 on the ray:
        Vector u = point.subtract(p0);

        // distance from p0 to the o who is in from of point
        double t = alignZero(u.dotProduct(v));

        // if the point is at a base
        if (t == 0 || isZero(height - t))
            return v;

        //the other point on the axis facing the given point
        Point o = p0.add(v.scale(t));

        return point.subtract(o).normalize();
    }
}
