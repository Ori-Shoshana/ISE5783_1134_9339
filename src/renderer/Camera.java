package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

/**
 * A class that represents a camera.
 * @author ori and amir
 */
public class Camera {

    private Point p0; // Camera location
    private Vector vUp; // Upward direction
    private Vector vTo; // Forward direction
    private Vector vRight; // Right direction
    private double width; // Width of the view plane
    private double height; // Height of the view plane
    private double distance; // Distance of the view plane from the camera

    /**
     * Constructs a camera with the given location, forward direction, and upward direction.
     * The upward direction must be orthogonal to the forward direction.
     *
     * @param p0  The location of the camera.
     * @param vTo The forward direction of the camera.
     * @param vUp The upward direction of the camera.
     * @throws IllegalArgumentException If the upward direction is not orthogonal to the forward direction.
     */
    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("The vectors are not orthogonal");
        }
        this.p0 = p0;

        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        /**
        * If two vectors are normalized (i.e., their magnitudes are equal to 1), then their dot product will result in a vector whose magnitude is between 0 and 1. Taking the absolute value of this vector will result in a normalized vector.
         */
        this.vRight = vTo.crossProduct(vUp);
    }

    /**
     * Sets the size of the view plane.
     *
     * @param width  The width of the view plane.
     * @param height The height of the view plane.
     * @return This camera object.
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance of the view plane from the camera.
     *
     * @param distance The distance of the view plane from the camera.
     * @return This camera object.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a ray that passes through the specified pixel on the view plane.
     *
     * @param nX The number of pixels in the X direction.
     * @param nY The number of pixels in the Y direction.
     * @param j  The index of the pixel in the X direction.
     * @param i  The index of the pixel in the Y direction.
     * @return The ray that passes through the specified pixel on the view plane.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
         Point pc = p0.add(vTo.scale(distance));     // The point center of the view plane
         double Ry = height / nY;                      //  The pixel height
         double Rx = width / nX;                       //  The pixel width

         double yJ = alignZero(-(i - (nY - 1) / 2d) * Ry);
         double xJ = alignZero((j - (nX - 1) / 2d) * Rx);

         Point PIJ = pc;

         if (!isZero(xJ))
             PIJ = PIJ.add(vRight.scale(xJ));
         if (!isZero(yJ))
             PIJ = PIJ.add(vUp.scale(yJ));

         return new Ray(p0, PIJ.subtract(p0));
    }

    /**
     * Returns the location of the camera.
     *
     * @return The location of the camera
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the up direction of the camera.
     *
     * @return The up direction of the camera
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Returns the direction the camera is facing.
     *
     * @return The direction the camera is facing
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * Returns the right direction of the camera.
     *
     * @return The right direction of the camera
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * Returns the width of the view plane.
     *
     * @return The width of the view plane
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the view plane.
     *
     * @return The height of the view plane
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the distance of the view plane from the camera.
     *
     * @return The distance of the view plane from the camera
     */
    public double getVPDistance() {
        return distance;
    }

}
