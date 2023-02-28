package primitives;

import java.util.Objects;

public class Ray {
    Point p0;
    Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir;
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

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}
