package renderer;

import primitives.*;
import scene.*;

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
}