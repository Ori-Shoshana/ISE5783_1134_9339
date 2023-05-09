package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class will serve all geometries classes based on vector
 *
 * @author Amir Hay and ori
 */
public abstract class Geometry extends Intersectable{
    protected Color emission = Color.BLACK;
    /**
     * Returns the emission color of the geometry.
     * @return The emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }
    /**
     * Sets the emission color of the geometry to the specified color.
     *
     * @param emission The new emission color of the geometry.
     * @return A reference to the modified geometry object.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
    /**
     * Returns the normal vector to the object at a given point.
     *
     * @param point The {@link Point} object representing the point on the object to get the normal vector from.
     * @return The normal vector as a {@link Vector} object.
     */
    public abstract Vector getNormal(Point point);
}
