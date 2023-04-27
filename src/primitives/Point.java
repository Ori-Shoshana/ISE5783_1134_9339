package primitives;

import java.util.Objects;

/**
 * A 3D point in space, defined by its x, y, and z coordinates.
 *
 * @author Amir Hay and ori */
    public class Point {

          /** The 3D coordinates of the point. */
        final Double3 xyz;

    /**
     * Constructs a new Point object with the specified x, y, and z coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
        public Point(double x, double y, double z) {
            xyz = new Double3(x, y, z);
        }

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param coordinates The coordinates of the point.
     */
        public Point(Double3 coordinates) {
            xyz = coordinates;
        }

    /**
     * Adds the specified vector to this point and returns a new Point object
     * representing the result.
     *
     * @param vector The vector to add to this point.
     * @return A new Point object representing the result of the addition.
     */
     public Point add(Vector vector) {
            return new Point(xyz.add(vector.xyz));
        }

    /**
     * Subtracts the specified point from this point and returns a new Vector object
     * representing the result.
     *
     * @param point The point to subtract from this point.
     * @return A new Vector object representing the result of the subtraction.
     * @throws IllegalArgumentException If the resulting vector has magnitude zero.
     */

     public Vector subtract(Point point) {
            return new Vector(xyz.subtract(point.xyz));
        }

    /**
     * Calculates the Euclidean distance between this point and the specified point.
     *
     * @param point The point to which the distance is calculated.
     * @return The Euclidean distance between this point and the specified point.
     */
        public double distance(Point point) {
            return Math.sqrt(distanceSquared(point));
        }

    /**
     * Calculates the squared Euclidean distance between this point and the specified point.
     *
     * @param point The point to which the distance is calculated.
     * @return The squared Euclidean distance between this point and the specified point.
     */
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
