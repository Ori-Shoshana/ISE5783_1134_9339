package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Amir hay and Ori
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public List<LightSource> lights =  new LinkedList<>();

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

    /**
     * Sets the list of light sources for this scene.
     *
     * @param lights The list of light sources to set.
     * @return This Scene object with the updated list of light sources.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
