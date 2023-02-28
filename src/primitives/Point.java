package primitives;

import java.util.Objects;

public class Point {
    final Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    public Point(Double3 coordinates) {
        xyz = coordinates;
    }


    public Point add(Vector vec) {
        return new Point(xyz.add(vec.xyz));
    }

    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    public double distanceSquared(Point p) {
        Double3 diff = xyz.subtract(p.xyz);
        return diff.d1 * diff.d1 + diff.d2 * diff.d2 + diff.d3 * diff.d3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}
