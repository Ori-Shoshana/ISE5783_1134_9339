package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color intensity;

    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public AmbientLight(double Ka) {
        this(Color.BLACK, new Double3(Ka, Ka, Ka));
    }

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public Color getIntensity() {
        return intensity;
    }
}
