
package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The abstract base class for all intersectable geometry objects in 3D space.
 */
public abstract class Intersectable {

    /**
     * A class representing a point on a geometry object and the geometry object itself.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructs a new GeoPoint with the given geometry object and point.
         *
         * @param geometry The geometry object.
         * @param point    The point on the geometry object.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * Compares this GeoPoint to the given object for equality.
         *
         * @param obj The object to compare to this GeoPoint.
         * @return true if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint) {
                return this.geometry.equals(((GeoPoint) obj).geometry) && this.point.equals(((GeoPoint) obj).point);
            }
            return false;
        }

        /**
         * Returns a string representation of this GeoPoint.
         *
         * @return A string representation of this GeoPoint.
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds all intersection points between the given ray and this geometry object.
     *
     * @param ray The ray to intersect with this geometry object.
     * @return A list of intersection points between the ray and this geometry object, or an empty list if there are no intersections.
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = this.findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     * @param ray ray pointing towards the graphic object
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray} in a range of distance
     * @param ray ray pointing towards the graphic object
     * @param maxDistance the maximum distance between the point to the start of the ray
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * helper of findGeoIntersections
     * @param ray ray pointing towards the graphic object
     * @param maxDistance the maximum distance between the point to the start of the ray
     * @return immutable list of intersection geo points {@link GeoPoint}
     */
    protected abstract List<GeoPoint>
    findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
