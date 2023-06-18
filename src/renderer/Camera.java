package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

import static primitives.Util.*;
/**
 * A class that represents a camera.
 *
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
    int numOfRays = 1;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    public Camera setRaynum(int nRays){
        numOfRays =nRays;
        return this;
    }
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
        /** If two vectors are normalized (i.e., their magnitudes are equal to 1),
         *  then their dot product will result in a vector whose magnitude is between 0 and 1.
         *  Taking the absolute value of this vector will result in a normalized vector.*/
        this.vRight = vTo.crossProduct(vUp);
    }

    /**
     * Renders the image using the current image writer and ray tracer.
     * The ray tracer find the color and the image writer colors the pixels
     *
     * @return This camera instance.
     * @throws UnsupportedOperationException If either the image writer or the ray tracer is not initialized.
     */
    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resources", "Camera", "imageWriter");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resources", "Camera", "rayTracerBase");
            }
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            //rendering the image
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {imageWriter.writePixel(j,i,castRay(nX, nY, j, i));
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException();
        }
        return this;
    }

    /**
     * Prints a grid of lines without running over the original image.
     *
     * @param interval The interval between the lines.
     * @param color    The color of the lines.
     * @throws MissingResourceException If the image writer is not initialized.
     */
    public void printGrid(int interval, Color color) throws MissingResourceException {
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("imageWriter", "Camera", "The value of imageWriter is null");
        for (int i = 0; i < imageWriter.getNy(); i++)
            for (int j = 0; j < imageWriter.getNx(); j++)
                if (i % interval == 0 || j % interval == 0)  // color the grid
                    imageWriter.writePixel(j, i, color);
    }

    /**
     * Casts a ray from the camera's location through the given pixel (in order to color a pixel)
     * Computes the color of the ray using the RayTracerBase object and writes it to the corresponding pixel in the ImageWriter object.
     *
     * @param nX     The number of pixels in the x-direction of the view plane.
     * @param nY     The number of pixels in the y-direction of the view plane.
     * @param xIndex The index of the pixel in the x-direction.
     * @param yIndex The index of the pixel in the y-direction.
     */
    private Color castRay(int nX, int nY, int xIndex, int yIndex) {
        if (numOfRays ==1) {
            Ray ray = constructRay(nX, nY, xIndex, yIndex);
            return rayTracer.traceRay(ray);
        }
        else {
            List<Ray> rays = constructRays( nX,  nY,  xIndex,  yIndex);
            return (calcColorSum(rays));
        }

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
     * Constructs a list of rays for a given image pixel.
     *
     * @param nX     The number of pixels in the X direction.
     * @param nY     The number of pixels in the Y direction.
     * @param xPixel      The X index of the pixel.
     * @param yPixel      The Y index of the pixel.
     * @return The list of constructed rays.
     */
    public List<Ray> constructRays(int nX, int nY, int xPixel, int yPixel) {
        Random random = new Random();
        List<Ray> rays = new LinkedList<>();

        // Calculate the center point of the image on the view plane
        Point imageCenter = p0.add(vTo.scale(distance));

        // Calculate the size of each pixel
        double pixelSizeX = width / nX;
        double pixelSizeY = height / nY;

        // Calculate the coordinates of the current pixel relative to the image center
        double Xj = (xPixel - (double) (nX - 1) / 2) * pixelSizeX;
        double Yi = -(yPixel - (double) (nY - 1) / 2) * pixelSizeY;

        // Calculate the point on the view plane corresponding to the current pixel
        Point Pxy = imageCenter;
        
        if (alignZero(Xj) != 0) {
            Pxy = Pxy.add(vRight.scale(Xj));
        }
        if (alignZero(Yi) != 0) {
            Pxy = Pxy.add(vUp.scale(Yi));
        }

        // Calculate the vector from the camera's location to the point on the view plane
        Vector Vij = Pxy.subtract(p0);
        Ray initialRay = new Ray(p0, Vij);
        rays.add(initialRay);

        // Generate additional rays within the pixel
        for (int k = 0; k < numOfRays; k++) {
            // Generate random offsets within the pixel
            double x = random.nextDouble() * pixelSizeX - pixelSizeX / 2;
            double y = random.nextDouble() * pixelSizeY - pixelSizeY / 2;

            // Calculate the new point on the view plane with the random offsets
            Point newPoint = Pxy.movePointOnViewPlane(vUp, vRight, x, y, pixelSizeX, pixelSizeY);

            // Calculate the ray from the camera's location to the new point
            Ray newRay = calcRay(newPoint);
            rays.add(newRay);
        }

        return rays;
    }


    /**
     * Calculates the sum of colors for a list of rays.
     *
     * @param rays The list of rays.
     * @return The sum of colors.
     */
    private Color calcColorSum(List<Ray> rays) {
        Color colorSum = new Color(0, 0, 0);
        for (Ray ray : rays) {
            // Trace each ray and add its color to the sum
            Color calcColor = rayTracer.traceRay(ray);
            colorSum = colorSum.add(calcColor);
        }
        // Reduce the sum of colors by dividing it by the number of rays
        colorSum = colorSum.reduce(rays.size());
        return colorSum;
    }


    /**
     * Calculates a ray given a point.
     *
     * @param point The point to calculate the ray from.
     * @return The calculated ray.
     */
    public Ray calcRay(Point point) {
        Vector newVector = point.subtract(p0);
        return new Ray(p0, newVector);
    }

    /**
     * Writes the image to the image file using the ImageWriter object.
     * Throws a MissingResourceException if the ImageWriter object is null.
     *
     * @throws MissingResourceException If the ImageWriter object is null.
     */
    public void writeToImage() {
        if (this.imageWriter == null) // the image writer is uninitialized
            throw new MissingResourceException("imageWriter", "Camera", "The value of imageWriter is null");
        imageWriter.writeToImage();
    }

    /**
     * Sets the RayTracerBase object to be used for computing the color of a ray.
     *
     * @param rayTracer The RayTracerBase object to be used.
     * @return The camera
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Sets the image writer for this camera.
     *
     * @param imageWriter the image writer to set
     * @return this camera, for method chaining
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
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

}
