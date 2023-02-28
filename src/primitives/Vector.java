package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
    }

    Vector(Double3 coordinates) {
        super(coordinates);
    }

    Vector add(Vector vec) {
        return new Vector(xyz.add(vec.xyz));
    }

    Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    Vector crossProduct(Vector vec) {
        double x = xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2;
        double y = xyz.d3 * vec.xyz.d1 - xyz.d1 * vec.xyz.d3;
        double z = xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1;
        return new Vector(x, y, z);
    }

    double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    double length() {
        return Math.sqrt(lengthSquared());
    }

    Vector normalize() {
        double len = length();
        return new Vector(xyz.d1 / len, xyz.d2 / len, xyz.d3 / len);
    }

    double dotProduct(Vector vec) {
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
