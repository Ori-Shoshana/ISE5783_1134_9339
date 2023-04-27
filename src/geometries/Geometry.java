package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * This class will serve all geometries classes based on vector
 *
 * @author Amir Hay and ori
 */
public interface Geometry extends Intersectable{
    /**
     * Returns the normal vector to the object at a given point.
     *
     * @param point The {@link Point} object representing the point on the object to get the normal vector from.
     * @return The normal vector as a {@link Vector} object.
     */
    public Vector getNormal(Point point);
}
