package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

import geometries.Intersectable.GeoPoint;

/**
 * implementation of the abstract class RayTracerBase
 */
public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectionPoints == null)
            return scene.background;
        GeoPoint point = ray.findClosestGeoPoint(intersectionPoints);
        return calcColor(point);
    }

    /**
     * Calculating the color of a specific point, taking into account the lightning,
     * transparency of the point itself and other affects of the surrounding are of the point in space
     *
     * @param point calculate the color of this point
     * @return for now - the ambient light's intensity
     */
    private Color calcColor(GeoPoint point) {
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }

}