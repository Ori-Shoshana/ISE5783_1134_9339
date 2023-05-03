package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * @author Amir hay and Ori
 */
public class Scene {
    public String name;
    public Color background;

    AmbientLight ambientLight;
    Geometries geometries;

    public Scene(String name) {
        this.name = name;
        this.background = Color.BLACK;
        this.ambientLight = AmbientLight.NONE;
        this.geometries = new Geometries();
    }

    /**
     * set the Background color of the scene
     * @param background the color to set
     * @return this Scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set the ambientLight of the scene
     * @param ambientLight the AmbientLight to set
     * @return this Scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * set the geometries of the scene
     * @param geometries the geometries to set
     * @return this Scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
