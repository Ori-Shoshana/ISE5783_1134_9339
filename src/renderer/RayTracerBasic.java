package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 *  implementation of the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase{

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersectionPoints = scene.geometries.findIntersections(ray);
        if (intersectionPoints == null)
            return scene.background;
        Point point = ray.findClosestPoint(intersectionPoints);
        return calcColor(point);
    }

    /**
     * Calculating the color of a specific point, taking into account the lightning,
     * transparency of the point itself and other affects of the surrounding are of the point in space
     * @param point calculate the color of this point
     * @return for now - the ambient light's intensity
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}