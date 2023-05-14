package lighting;

import primitives.Color;
import primitives.Double3;
/**
 *  * @author Amir Hay and ori shoshana
 * This class represents an ambient light, which is a type of light that
 * contributes a uniform amount of illumination to all objects in the scene.
 */
public class AmbientLight extends Light{

    /**
     * construct the ambient light using a color, and it's attenuation factor with dad constructor.
     * @param Ia the base intensity of the light
     * @param Ka the attenuation factor of the intensity for each rgb color
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

}
