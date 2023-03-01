package primitives;

import com.sun.source.tree.ReturnTree;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The vector cannot be vector 0");
    }

    public Vector(Double3 coordinates) {
        super(coordinates);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The vector cannot be vector 0");
    }

    public Vector add(Vector vec) {
        return new Vector(xyz.add(vec.xyz));
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    public Vector crossProduct(Vector vec) {
        double x = xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2;
        double y = xyz.d3 * vec.xyz.d1 - xyz.d1 * vec.xyz.d3;
        double z = xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1;
        return new Vector(x, y, z);
    }

    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        return new Vector(xyz.reduce(this.length()));
    }

    public double dotProduct(Vector vec) {
        return xyz.d1 * vec.xyz.d1 + xyz.d2 * vec.xyz.d2 + xyz.d3 * vec.xyz.d3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vec)) return false;
        return xyz.equals(vec.xyz);
    }

    @Override
    public String toString() {
        return "Vector{" + "xyz=" + xyz + '}';
    }
}
