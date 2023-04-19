package geometries;

/**
 * This class will serve all geometries classes that have a radius based on radius
 *
 * @author Amir Hay and ori
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * The Radius
     */


    protected double radius;

    /**
     * Constructor to initialize RadialGeometry based object with a radius
     *
     * @param radius the radius value
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
