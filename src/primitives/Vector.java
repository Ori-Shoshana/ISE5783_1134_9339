package primitives;

/** class vector that represents a vector in the space
 * @author ori shoshana and amir hay */
public class Vector extends Point {
    /** Constructor to initialize vector that receives three numbers (coordinates)
     * @param x first number value
     * @param y second number value
     * @param z third number value
     * and throws an exception if all the values are zero*/
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The vector cannot be vector 0");
    }
    /** Constructor to initialize vector that receives Variable of type double3
     * @param coordinates type double3 (contains three values)
     * and throws an exception if all the values are zero*/
    public Vector(Double3 coordinates) {
        super(coordinates);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The vector cannot be vector 0");
    }
    /** Sum two vectors (by their values)
     * @param  vector The vector that is added to the current vector
     * @return   A new vector with the values after the addition */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }
    /** Multiplies a vector by a scalar
     * @param  scalar (double) The scalar multiply the vector by it
     * @return   A new vector with the values after the multiplication */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }
    /** Multiplies a vector by a vector
     * @param  vector  The vector that multiplies the vector
     * @return   A new vector with the values after the multiplication */
    public Vector crossProduct(Vector vector) {
        double x = xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2;
        double y = xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3;
        double z = xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1;
        return new Vector(x, y, z);
    }
    /** Calculates the squared length of the vector
     * @return   the length of the vector */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }
    /** Calculates the length of the vector
     * @return   the length of the vector */
    public double length() {
        return Math.sqrt(lengthSquared());
    }
    /** Calculates the normalized vector
     * @return   the normalized vector */
    public Vector normalize() {
        return new Vector(xyz.reduce(this.length()));
    }
    /** Multiplies a vector by a vector
     * @param  vector  The vector that multiplies the vector
     * @return   A new vector with the values after the multiplication */
    public double dotProduct(Vector vector) {
        return xyz.d1 * vector.xyz.d1 + xyz.d2 * vector.xyz.d2 + xyz.d3 * vector.xyz.d3;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Vector vec)) return false;
        return xyz.equals(vec.xyz);
    }

    @Override
    public String toString() {
        return "Vector{" + "xyz=" + xyz + '}';
    }
}
