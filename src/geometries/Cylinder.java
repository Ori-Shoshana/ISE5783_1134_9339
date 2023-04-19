package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


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
        return null;
    }
}
