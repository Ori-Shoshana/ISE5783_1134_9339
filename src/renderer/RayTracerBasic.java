package renderer;

import primitives.*;
import geometries.Geometry;
import lighting.LightSource;
import geometries.Intersectable.GeoPoint;
import scene.Scene;
import static primitives.Util.alignZero;

import java.util.List;

/**
 * RayTracerBasic is an implementation of the abstract class RayTracerBase,
 * which calculates the color of a given ray's intersection with the scene using the
 * Phong reflection model for local effects.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructs a new RayTracerBasic object with a given scene.
     *
     * @param scene the scene to render
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Calculates the color of the intersection point of a given ray with the scene
     * using the Phong reflection model.
     *
     * @param ray the ray to trace
     * @return the color of the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectionPoints == null)
            return scene.background;
        GeoPoint point = ray.findClosestGeoPoint(intersectionPoints);
        return calcColor(point,ray);
    }

    /**
     * Calculates the color of the intersection point using the Phong reflection model.
     *
     * @param point the intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return this.scene.ambientLight.getIntensity().
                add(this.calcLocalEffects(point, ray));
    }

    /**
     * Computes the local effects at the intersection point, such as the diffusive and specular effects.
     *
     * @param geoPoint the intersection point
     * @return the color of the local effects
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {

        Geometry geometry = geoPoint.geometry;
        Point point=geoPoint.point;
        Color color=geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = geometry.getNormal(point);

        double nv = alignZero(n.dotProduct(v));

        if (nv == 0) return color;

        Material material = geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Color iL = lightSource.getIntensity(point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }
    /**

     Calculates the diffuse reflection of a given material.
     @param material The material to calculate the diffuse reflection for.
     @param nl The dot product of the normal vector and light vector.
     @return The diffuse reflection of the material.
     */
    private Double3 calcDiffusive(Material material,double nl){
        return material.kD.scale(Math.abs(nl));
    }
    /**

     Calculates the specular reflection of a given material.
     @param material The material to calculate the specular reflection for.
     @param n The normal vector of the geometry.
     @param l The light vector.
     @param nl The dot product of the normal vector and light vector.
     @param v The view vector.
     @return The specular reflection of the material.
     */
    private Double3 calcSpecular(Material material,Vector n,Vector l,double nl,Vector v){
        Vector r=l.subtract(n.scale(2*nl));
        return material.kS.scale(Math.pow(Math.max(0,v.scale(-1).dotProduct(r)),material.nShininess));
    }


}