package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
/**
 The Geometries class represents a collection of Intersectable shapes.
 It implements the Intersectable interface to allow for finding intersections with a given Ray.
 */
public class Geometries implements Intersectable {

    List<Intersectable> shapes;

    /**
     * Constructs an empty collection of shapes.
     */
    public Geometries() {
        shapes = new LinkedList<Intersectable>();
    }
    /**
     * Constructs a collection of shapes with the given Intersectable objects.
     * @param geometries the shapes to add to the collection
     */
    public Geometries(Intersectable... geometries) {
        this.shapes = new LinkedList<>();
        for (Intersectable geometry : geometries)
            this.shapes.add(geometry);
    }
    /**
     * Adds the given Intersectable shapes to the collection.
     * @param geometries the shapes to add
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries)
            this.shapes.add(geometry);
    }

    /**
     * Finds all intersection points of the given Ray with the shapes in the collection.
     * @param ray the Ray to find intersections with
     * @return a list of intersection Points, or null if there are no intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectPoints = null;
        for (Intersectable geometry : this.shapes) {
            List<Point> insects = geometry.findIntersections(ray);
            if(insects != null){
                if(intersectPoints == null)
                    intersectPoints = new LinkedList<Point>();
                intersectPoints.addAll(insects);
            }
        }
        return intersectPoints;
    }

}