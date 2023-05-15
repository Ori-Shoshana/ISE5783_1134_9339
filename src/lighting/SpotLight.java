package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
/**
 * Represents a spotlight in a 3D scene, which emits light in a specific direction
 * and can be used to highlight specific areas or objects.
 */
public class SpotLight extends PointLight {
    /** The direction in which the spotlight is pointing. */
    Vector direction;
    private double narrowBeam = 1;

    public SpotLight setNarrowBeam(double narrowBeam){
        this.narrowBeam=narrowBeam;
        return this;
    }
    /**
     * Constructs a new spotlight with the given intensity, position, and direction.
     * @param intensity The intensity of the light emitted by the spotlight.
     * @param position The position of the spotlight in 3D space.
     * @param direction The direction in which the spotlight is pointing.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light emitted by this spotlight at the given point in 3D space.
     * The intensity is determined by the angle between the direction of the spotlight and the vector
     * from the spotlight's position to the given point.
     * @param p The point at which to calculate the intensity of the light.
     * @return The color and intensity of the light emitted by this spotlight at the given point.
     */
    @Override
    public Color getIntensity(Point p) {
        double num = Math.max(0, Math.pow(direction.dotProduct(getL(p)),narrowBeam));
        return super.getIntensity(p).scale(num);
    }

    /**
     * Returns the vector from the spotlight's position to the given point.
     * @param p The point to which to calculate the vector.
     * @return The vector from the spotlight's position to the given point.
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}