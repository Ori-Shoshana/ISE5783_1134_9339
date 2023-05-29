package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A directional light is a type of light source that emits light in a single direction,
 * regardless of where the objects in the scene are located. This is commonly used to simulate
 * the light of a distant star or the sun.
 */
public class DirectionalLight extends Light implements LightSource{
    /**
     * The direction of the light. This vector represents the direction in which the light is
     * shining, and is normalized to have a length of 1.
     */
    private final Vector direction;

    /**
     * Creates a new directional light source with the given intensity and direction.
     * @param intensity The intensity of the light source.
     * @param direction The direction in which the light source is shining.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Gets the intensity of the light at the given point in space.
     * Since this is a directional light, the intensity is the same everywhere.
     * @param p The point at which to calculate the intensity.
     * @return The intensity of the light at the given point.
     */
    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    /**
     * Gets the direction of the light at the given point in space.
     * Since this is a directional light, the direction is constant everywhere.
     * @param point The point at which to calculate the direction.
     * @return The direction of the light at the given point.
     */
    @Override
    public Vector getL(Point point){
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}