package renderer;

import primitives.*;
import scene.*;

import java.util.List;

/**
 *  represents a ray tracer
 *  1) traces rays through a scene
 *  2) finding a color of an object that intersects closest to the ray
 */
public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Tracing a ray through a scene and finding the color of the object closest to the head of the ray
     * @param ray The ray to trace the scene with
     * @return The color of the object the ray 'sees' first
     */
    public abstract Color traceRay(Ray ray);

    /**
     * Trace the ray and calculates the color of the point that interact with the geometries of the scene
     * @param rays the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    public abstract Color TraceRays(List<Ray> rays);

    /**
     * Checks the color of the pixel with the help of individual rays and averages between
     * them and only if necessary continues to send beams of rays in recursion
     * @param centerP center pixl
     * @param Width Length
     * @param Height width
     * @param minWidth min Width
     * @param minHeight min Height
     * @param cameraLoc Camera location
     * @param Vright Vector right
     * @param Vup vector up
     * @param prePoints pre Points
     * @return Pixel color
     */
    public abstract Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);
    public abstract Color RegularSuperSampling(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);

}