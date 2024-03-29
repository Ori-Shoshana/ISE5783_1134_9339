package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import static geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.awt.Color.BLACK;
import static primitives.Util.*;

/**
 * RayTracerBasic class that extends the RayTracer class
 */
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * constructor that calls super constructor
     *
     * @param scene the scene to trace through
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Given a ray, find the closest point of intersection with the scene, and return the color of that point
     *
     * @param ray The ray that we're tracing.
     * @return The color of the closest point.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null)
            return scene.background;

        return calcColor(closestPoint, ray);
    }

    /**
     * Trace the ray and calculates the color of the point that interact with the geometries of the scene
     *
     * @param rays the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    @Override
    public Color TraceRays(List<Ray> rays) {
        Color color = new Color(BLACK);
        for (Ray ray : rays) {
            GeoPoint clossestGeoPoint = findClosestIntersection(ray);
            if (clossestGeoPoint == null)
                color = color.add(scene.background);
            else color = color.add(calcColor(clossestGeoPoint, ray));
        }
        return color.reduce(rays.size());
    }

    /**
     * calculate the color that needed to be returned from the pixel.
     *
     * @param gp  the point to calculate the color for.
     * @param ray the ray to pass to the function that summarise all the effects of the light sources.
     * @return the color to paint the pixel.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
    }


    /**
     * the entrance function to the recursive process of calculating the reflective effect and refractive effect.
     *
     * @param gp    the point of intersection that need the color calculation.
     * @param ray   the ray from the camera to that point.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the color of the pixel with all the refractions and reflections.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * It finds the closest intersection point of a ray with the scene's geometries
     *
     * @param ray The ray that we want to find the closest intersection to.
     * @return The closest intersection point.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null)
            return null;
        //returns closest point
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * function calculates local effects of color on point
     *
     * @param geoPoint geometry point to color
     * @param ray      ray that intersects
     * @param k        k value
     * @return color
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Color color = geoPoint.geometry.getEmission();
        Vector vector = ray.getDir();
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(normal.dotProduct(vector));
        if (nv == 0)
            return color;
        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector lightVector = lightSource.getL(geoPoint.point);
            double nl = alignZero(normal.dotProduct(lightVector));
            if (nl * nv > 0) {
                Double3 ktr = transparency(geoPoint, lightSource, lightVector, normal);

                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(lightIntensity.scale(calcDiffusive(material, nl)),
                            lightIntensity.scale(calcSpecular(material, normal, lightVector, nl, vector)));
                }
            }
        }
        return color;
    }

    /**
     *calculating the color in the global scene, tells what more points we need to
     * check for the color calculations.
     *
     * @param gp    the point of the intersection.
     * @param ray   the ray that intersects with the geometry.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the calculated color.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kr = material.kR, kkr = k.product(kr);
        Ray reflectedRay = constructReflectedRay(gp,n,v);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(reflectedRay, level - 1, kr, kkr)).scale(kr);
        }
        Double3 kt = material.kT, kkt = k.product(kt);
        Ray refractedRay = constructRefractedRay(gp,v,n);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcGlobalEffect(refractedRay, level - 1, kt, kkt)).scale(kt);
        }
        return color;
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
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))
                ? Color.BLACK : calcColor(gp, ray, level - 1, kkx);
    }

    /**
     * function calculates specular color
     *
     * @param material    material of geometry
     * @param normal      normal of geometry
     * @param lightVector light vector
     * @param nl          dot product of normal and light vector
     * @param vector      direction of ray
     * @return specular color
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightVector, double nl, Vector vector) {
        Vector reflectedVector = lightVector.subtract(normal.scale(2 * nl));
        double cosTeta = alignZero(-vector.dotProduct(reflectedVector));
        return cosTeta <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(cosTeta, material.nShininess));

    }

    /**
     * function calculates diffusive color
     *
     * @param material material of geometry
     * @param nl       dot product of normal and light vector
     * @return diffusive color
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * function will construct a reflection ray
     *
     * @param gp geometry point to check
     * @param normal   normal vector
     * @param vector   direction of ray to point
     * @return reflection ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector normal, Vector vector) {
        double vn = vector.dotProduct(normal);
        if(isZero(vn)){
            return null;
        }
        Vector reflectedVector = vector.subtract(normal.scale(2 * vn));
        return new Ray(gp.point, reflectedVector, normal);
    }

    /**
     * Construct and return a refracted ray
     *
     * @param gp The GeoPoint of intersection between the ray and the object
     * @param v the vector from the point to the light source
     * @param n the normal vector of the point of intersection
     * @return The refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }
    /**
     * function will return double that represents transparency
     *
     * @param geoPoint    geometry point to check
     * @param lightSource light source
     * @param l           light vector
     * @param n           normal vector
     * @return transparency value
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double light = lightSource.getDistance(geoPoint.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,light);

        Double3 ktr = Double3.ONE;
        if (intersections == null) return INITIAL_K;

        double distance = lightSource.getDistance(geoPoint.point);

        for (GeoPoint intersection : intersections) {
            Double3 kt = intersection.geometry.getMaterial().kT;
            ktr = ktr.product(kt);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * Performs adaptive super-sampling for a given pixel.
     *
     * @param centerP     The center point of the pixel.
     * @param Width       The width of the pixel.
     * @param Height      The height of the pixel.
     * @param minWidth    The minimum width of a sub-pixel for further sampling.
     * @param minHeight   The minimum height of a sub-pixel for further sampling.
     * @param cameraLoc   The location of the camera.
     * @param Vright      The vector representing the right direction.
     * @param Vup         The vector representing the up direction.
     * @param prePoints   A list of pre-sampled points to avoid redundancy.
     * @return The color computed for the pixel through adaptive super-sampling.
     */
    @Override
    public Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {
        if (Width < minWidth * 2 || Height < minHeight * 2) {
            // If the pixel is smaller than the minimum size, trace a ray through the pixel and return the color.
            return this.traceRay(new Ray(cameraLoc, centerP.subtract(cameraLoc)));
        }

        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<primitives.Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;
        // Iterate over the corners of the pixel and perform sub-sampling
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * Width / 2)).add(Vup.scale(j * Height / 2));
                cornersList.add(tempCorner);
                // Check if the sub-pixel's corner is already sampled
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(cameraLoc, tempCorner.subtract(cameraLoc));
                    nextCenterPList.add(centerP.add(Vright.scale(i * Width / 4)).add(Vup.scale(j * Height / 4)));
                    colorList.add(traceRay(tempRay));
                }
            }
        }

        if (nextCenterPList == null || nextCenterPList.size() == 0) {
            // If no valid sub-pixels were found, return black color.
            return primitives.Color.BLACK;
        }

        boolean isAllEquals = true;
        primitives.Color tempColor = colorList.get(0);
        // Check if all colors in the colorList are almost equal
        for (primitives.Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            // If all colors are equal and there is more than one color, return the first color.
            return tempColor;


        tempColor = primitives.Color.BLACK;
        // Recursively perform adaptive super-sampling on sub-pixels
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingRec(center, Width / 2, Height / 2, minWidth, minHeight, cameraLoc, Vright, Vup, cornersList));
        }
        // Reduce the color by dividing by the number of sub-pixels
        return tempColor.reduce(nextCenterPList.size());
    }
    /**
     * Performs regular super-sampling for a given pixel.
     *
     * @param centerP     The center point of the pixel.
     * @param Width       The width of the pixel.
     * @param Height      The height of the pixel.
     * @param minWidth    The minimum width of a sub-pixel for further sampling.
     * @param minHeight   The minimum height of a sub-pixel for further sampling.
     * @param cameraLoc   The location of the camera.
     * @param Right       The vector representing the right direction.
     * @param Vup         The vector representing the up direction.
     * @param prePoints   A list of pre-sampled points to avoid redundancy.
     * @return The color computed for the pixel through regular super-sampling.
     */
    public Color RegularSuperSampling(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Right, Vector Vup, List<Point> prePoints) {
        List<Color> colorList = new ArrayList<>();

        int numSubPixelsX = (int) Math.ceil(Width / minWidth);
        int numSubPixelsY = (int) Math.ceil(Height / minHeight);

        Random random = new Random();
        // Iterate over sub-pixels and perform regular super-sampling
        for (int i = 0; i < numSubPixelsY; i++) {
            for (int j = 0; j < numSubPixelsX; j++) {
                double offsetX = minWidth * j;
                double offsetY = minHeight * i;

                double randomX = offsetX + random.nextDouble() * minWidth;
                double randomY = offsetY + random.nextDouble() * minHeight;

                Point subPixelPoint = centerP.add(Right.scale(randomX - Width / 2)).add(Vup.scale(randomY - Height / 2));

                // Check if the sub-pixel's point is already sampled
                if (prePoints == null || !isInList(prePoints, subPixelPoint)) {
                    Ray ray = new Ray(cameraLoc, subPixelPoint.subtract(cameraLoc));
                    colorList.add(traceRay(ray));
                }
            }
        }

        if (colorList.isEmpty()) {
            // If no valid sub-pixels were found, return black color.
            return primitives.Color.BLACK;
        }

        Color averageColor = Color.BLACK;
        // Calculate the average color by adding all colors in the colorList
        for (Color color : colorList) {
            averageColor = averageColor.add(color);
        }
        // Reduce the color by dividing by the number of sub-pixels
        return averageColor.reduce(colorList.size());
    }

    /**
     * Find a point in the list
     *
     * @param pointsList the list
     * @param point      the point that we look for
     * @return
     */
    private boolean isInList(List<Point> pointsList, Point point) {
        for (Point tempPoint : pointsList) {
            if (point.equals(tempPoint))
                return true;
        }
        return false;
    }
}
