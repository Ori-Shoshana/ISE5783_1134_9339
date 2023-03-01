package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Plane class
 * @author Amir Hay and ori */
public class Plane implements Geometry {
    /** the point */
    private Point  q0;

    /** the noraml */
    private Vector normal;

    /**
     * Constructor to initialize Tube based object with a point and a normal
     * @param q0 the point value
     * @param normal the noraml value
     */
    public Plane(Point q0, Vector normal) {
        this.q0     = q0;
        this.normal = normal;
    }

    /**
     * Constructor to initialize Tube based object with a three point and ...
     * @param point1 first point value
     * @param point2 second point value
     * @param point3 three point value
     */
    public Plane(Point point1,Point point2,Point point3){
        this.q0 = point1;
        this.normal = null;
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

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

}
