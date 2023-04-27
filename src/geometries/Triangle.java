package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Represents a triangle in 3D space defined by three {@link Point}s.
 *
 * @author Amir Hay and ori
 */
public class Triangle extends Polygon {
    /**
     Constructs a triangle object from three points.
     @param point1 The first point of the triangle.
     @param point2 The second point of the triangle.
     @param point3 The third point of the triangle.
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);
    }
    /**
     Returns a list of intersection points between the triangle and a given ray.
     @param ray the ray to find intersection points with.
     @return a list of intersection points between the triangle and the given ray, or {@code null} if there are no
     intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
