package geometries;

import primitives.Point;
import primitives.Vector;

/** Cylinder class which represents the location of a cylinder in space
 * @author Amir Hay and ori */
public class Cylinder extends RadialGeometry {

    /** the hieght of the Cylinder*/
    private double height;

    /** Constructor to initialize Sphere based object with a radius and height
     * @param radius the radius value
     * @param height the hieght of the Cylinder value */
    public Cylinder(double radius, double height) {
        super(radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
