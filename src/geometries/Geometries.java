package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of Intersectable shapes.
 * It implements the Intersectable interface to allow for finding intersections with a given Ray.
 */
public class Geometries extends Intersectable {

    List<Intersectable> shapes;

    /**
     * Constructs an empty collection of shapes.
     */
    public Geometries() {
        shapes = new LinkedList<Intersectable>();
    }

    /**
     * Constructs a collection of shapes with the given Intersectable objects.
     *
     * @param geometries the shapes to add to the collection
     */
    public Geometries(Intersectable... geometries) {
        this();
       add(geometries);
    }

    /**
     * Adds the given Intersectable shapes to the collection.
     *
     * @param geometries the shapes to add
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(shapes, geometries);
    }

    /**
     * Finds all intersection points of the given Ray with the shapes in the collection.
     *
     * @param ray the Ray to find intersections with
     * @return a list of intersection Points, or null if there are no intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double max) {
        List<GeoPoint> result = new LinkedList<>();
        for (Intersectable geometry : shapes) {
            List<GeoPoint> shapePoints = geometry.findGeoIntersectionsHelper(ray,max);
            if (shapePoints != null) {
                result.addAll(shapePoints);
            }
        }
        if (result.isEmpty()){
            return  null;
        }
        return result;
    }

}