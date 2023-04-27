package geometries;

/**
 * An abstract class representing a radial geometry shape. Radial geometry shapes have a single radius value that
 * determines their size.
 * @author Amir Hay and ori
 */
public abstract class RadialGeometry implements Geometry {
    /**
     * The radius of the radial geometry.
     */
    protected double radius;
    /**
     * Constructor to initialize a radial geometry object with a radius value.
     *
     * @param radius The radius value used to initialize the object.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
    /**
     * Returns the radius value of the radial geometry object.
     *
     * @return The radius value as a double.
     */
    public double getRadius() {
        return radius;
    }
}
