package primitives;

import java.util.Objects;

/**
 * The Ray class represents a ray in 3D space, defined by a starting point and a direction.
 *
 * @author ori shoshana and amir hay
 */
public class Ray {
    /**
     The starting point of the ray.
     */
    final private Point p0;
    /**
     The direction of the ray, normalized to a unit vector.
     */
    final private Vector dir;

    /**
     Constructs a new Ray with the given starting point and direction.
     @param p0 the starting point of the ray.
     @param dir the direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     Returns the starting point of the ray.
     @return the starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     Returns the direction of the ray.
     @return the direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     Returns the point at the given parameter value t along the ray.
     @param t the parameter value.
     @return the point at the given parameter value t along the ray.
     */
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
