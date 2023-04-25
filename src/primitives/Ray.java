package primitives;

import java.util.Objects;

/**
 * class Ray that represents a straight line in space
 *
 * @author ori shoshana and amir hay
 */
public class Ray {
    /**
     * A point
     */
    final private Point p0;
    /**
     * A vector
     */
    final private Vector dir;

    /**
     * Constructor to initialize Ray
     * @param p0  (point)
     * @param dir (vector)
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * @return the point
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @return the point
     */
    public Vector getDir() {
        return dir;
    }

    //יצרנו בישביל פונציה שתחשב חיתוך של קרן לצורה
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
