package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class which represents the location of a Tube in space
 * @author Amir Hay and ori */
public class Tube extends RadialGeometry{

    /** a ray */
    protected Ray axisRay;

    /**
     * Constructor to initialize Tube based object with a radius and a ray
     * @param radius the radius value
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
        return null;
    }
}
