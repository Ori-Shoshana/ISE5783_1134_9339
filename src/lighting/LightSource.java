package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * An interface representing a source of light in a scene.
 */
public interface LightSource {

    /**
     * Returns the intensity of the light at a given point in the scene.
     * @param p The point to calculate the intensity at.
     * @return The color representing the intensity of the light.
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction of the light at a given point in the scene.
     * @param p The point to calculate the direction from.
     * @return The vector representing the direction of the light.
     */
    public Vector getL(Point p);

}