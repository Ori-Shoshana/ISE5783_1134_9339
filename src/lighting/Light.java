package lighting;

import primitives.Color;

/**
 * @author Amir Hay and ori shoshana
 * The abstract base class for all types of lights in a scene
 *  This class provides a method to retrieve the intensity of the light
 */
abstract class Light {
    /**
     * The intensity of the light.
     */
    private Color intensity;

    /**
     * Constructs a new light with the given intensity.
     *
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Returns the intensity of the light.
     *
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}