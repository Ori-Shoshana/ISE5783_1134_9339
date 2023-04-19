package primitives;

import java.util.Objects;

/** Point class which represents the location of a point in space
 * @author Amir Hay and ori */
public class Point {
    /** the 3D values of the point
     */
    final Double3 xyz;

    /** Constructor to initialize Point based object with three number values
     * @param x first number value
     * @param y second number value
     * @param z third number value */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /** Constructor to initialize Point based object with a coordinates
     * @param coordinates the point value */
    public Point(Double3 coordinates) {
        xyz = coordinates;
    }

    /** add vector to point
     * @param vector the vector
     * @return the point white the add of the vector */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /** subtracts point by point
     * @param point the point
     * @return a vector of the subtracts
     * @throws IllegalArgumentException in case of resulting Vector(0,0,0)
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /** Calculate distance from one point to another
     * @param point the second point
     * @return the euclidean distance between two points
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /** Calculates distance from point to point in a square
     * @param point the second point
     * @return  the distance from point to point in a square */
    public double distanceSquared(Point point) {
        Double3 diff = xyz.subtract(point.xyz);
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
