package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        List<Point> point = this.plane.findIntersections(ray);
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double t1 = v.dotProduct(n1);
        double t2 = v.dotProduct(n2);
        double t3 = v.dotProduct(n3);


        if ((t1>0 && t2>0 && t3>0) || (t1<0 && t2<0 && t3<0))
            return List.of(new GeoPoint(this,point.get(0)));

        return null;
    }
}
