package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * The Ray class represents a ray in 3D space, defined by a starting point and a direction.
 *
 * @author ori shoshana and amir hay
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    final private Point p0;
    /**
     * The direction of the ray, normalized to a unit vector.
     */
    final private Vector dir;

    /**
     * Constructs a new Ray with the given starting point and direction.
     *
     * @param p0  the starting point of the ray.
     * @param dir the direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return the starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return the direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Returns the point at the given parameter value t along the ray.
     *
     * @param t the parameter value.
     * @return the point at the given parameter value t along the ray.
     */
    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * Finds the point in the given list that is closest to the starting point of this ray.
     *
     * @param list the list of points to search
     * @return the closest point, or {@code null} if the list is {@code null} or empty
     */
    public Point findClosestPoint(List<Point> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Point closestPoint = list.get(0);
        double closestDistance = closestPoint.distanceSquared(this.p0);

        for (Point currentPoint : list) {
            double currentDistance = currentPoint.distanceSquared(this.p0);
            if (currentDistance < closestDistance) {
                closestPoint = currentPoint;
                closestDistance = currentDistance;
            }
        }

        return closestPoint;
    }
    /**
     * Finds the point in the given list that is closest to the starting point of the ray.
     *
     * @param geoPoints the list of points to search for the closest point
     * @return the point in the list that is closest to the reference point, or null if the input list is empty
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        GeoPoint closestPoint = geoPoints.get(0);
        double distance = closestPoint.point.distance(p0);
        for (GeoPoint geoPoint : geoPoints) {
            if (geoPoint.point.distance(p0) < distance) {
                closestPoint = geoPoint;
                distance = geoPoint.point.distance(p0);
            }
        }
        return closestPoint;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
