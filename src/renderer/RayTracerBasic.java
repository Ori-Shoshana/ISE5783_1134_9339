package renderer;

import primitives.*;
import geometries.Geometry;
import lighting.LightSource;
import geometries.Intersectable.GeoPoint;
import scene.Scene;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;

/**
 * RayTracerBasic is an implementation of the abstract class RayTracerBase,
 * which calculates the color of a given ray's intersection with the scene using the
 * Phong reflection model for local effects.
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;


    /**
     * Constructs a new RayTracerBasic object with a given scene.
     *
     * @param scene the scene to render
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /**
     * Checks if a given point is unshaded by a light source.
     *
     * @param gp    The geometric point to be checked for shading.
     * @param light The light source illuminating the scene.
     * @param l     The direction vector from the point to the light source.
     * @param n     The normal vector at the point.
     * @param nl    The dot product between the normal vector and the direction vector.
     * @return {@code true} if the point is unshaded, {@code false} otherwise.
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);

        double maxDistance = light.getDistance(point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, maxDistance);

        return intersections == null;

        /*for (GeoPoint intersection : intersections)
            if(intersection.point.distance(lightRay.getP0()) < light.getDistance(intersection.point))
            return false;*/
        /* if there are points in the intersections list that are closer to the point
        than light source – return false
        otherwise – return true*/
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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color of the intersection point using the Phong reflection model.
     *
     * @param point the intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * the entrance function to the recursive process of calculating the reflective effect and refractive effect.
     *
     * @param intersection    the point of intersection that need the color calculation.
     * @param ray   the ray from the camera to that point.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the color of the pixel with all the refractions and reflections.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray);
        return 1 == level ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Computes the local effects at the intersection point, such as the diffusive and specular effects.
     *
     * @param geoPoint the intersection point
     * @return the color of the local effects
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {

        Geometry geometry = geoPoint.geometry;
        Point point = geoPoint.point;
        Color color = geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = geometry.getNormal(point);

        double nv = alignZero(n.dotProduct(v));

        if (nv == 0) return color;

        Material material = geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                if (unshaded(geoPoint, lightSource, l, n, nl)) {//////////////////////////////////////////////
                    Color iL = lightSource.getIntensity(point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection of a given material.
     *
     * @param material The material to calculate the diffuse reflection for.
     * @param nl       The dot product of the normal vector and light vector.
     * @return The diffuse reflection of the material.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * Calculates the specular reflection of a given material.
     *
     * @param material The material to calculate the specular reflection for.
     * @param n        The normal vector of the geometry.
     * @param l        The light vector.
     * @param nl       The dot product of the normal vector and light vector.
     * @param v        The view vector.
     * @return The specular reflection of the material.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        return material.kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.nShininess));
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcColorGLobalEffect(constructReflectedRay(gp, v, n),level, k, material.kR).add(calcColorGLobalEffect(constructRefractedRay(gp, v, n),level, k, material.kT));
    }

    /**
     * calculating a global effect color
     *
     * @param ray   the ray that intersects with the geometry.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @param kx    the attenuation factor of reflection or transparency
     * @return the calculated color.
     */
    private Color calcColorGLobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null)
            return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))? Color.BLACK : calcColor(gp, ray, level - 1, kkx);
    }

    /**
     * finding the closet geopoint on a ray
     *
     * @param ray the ray to search on
     * @return the closet geopoint on the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Construct and return a refracted ray
     *
     * @param gp The GeoPoint of intersection between the ray and the object
     * @param v  the vector from the point to the light source
     * @param n  the normal vector of the point of intersection
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, n, v);
    }

    /**
     * function will construct a reflection ray
     *
     * @param gp     geometry point to check
     * @param normal normal vector
     * @param vector direction of ray to point
     * @return reflection ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector normal, Vector vector) {
        Vector reflectedVector = vector.subtract(normal.scale(2 * vector.dotProduct(normal)));
        return new Ray(gp.point, reflectedVector, normal);
    }

}