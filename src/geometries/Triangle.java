package geometries;

import primitives.Point;

/**
 * Triangle class which represents the location of a Triangle in space
 * @author Amir Hay and ori */
public class Triangle extends Polygon {
    /**
     * Constructor to initialize Sphere based object with a three point
     * @param point1 first point value
     * @param point2 second point value
     * @param point3 three point value
     */
    public Triangle(Point point1,Point point2,Point point3) {
        super(point1,point2,point3);
    }
}
