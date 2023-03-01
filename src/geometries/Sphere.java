package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere class which represents the location of a Sphere in space
 * @author Amir Hay and ori */
public class Sphere extends RadialGeometry {

    /** a point */
    private Point center;

    /**
     * Constructor to initialize Sphere based object with a radius and center point
     * @param radius the radius value
     * @param center a point value
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
