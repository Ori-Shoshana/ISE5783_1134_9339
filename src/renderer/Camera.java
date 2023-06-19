package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

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
    private Point centerPoint;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int antiAliasing = 1;
    private boolean adaptive = false;
    private int numOfThreads = 1;

    public Camera setAntiAliasing(int nRays){
        antiAliasing = nRays;
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

        if (p0 == null || vRight == null
                || vUp == null || vTo == null || distance == 0
                || width == 0 || height == 0 || centerPoint == null
                || imageWriter == null || rayTracer == null) {
            throw new MissingResourceException("Missing camera data", Camera.class.getName(), null);
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();


        Pixel.initialize(nY, nX, 1);


        if (!adaptive) {
            while (numOfThreads-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        imageWriter.writePixel(pixel.col, pixel.row, rayTracer.TraceRays(constructRays(nX,
                                nY, pixel.col, pixel.row)));
                }).start();
            }
            Pixel.waitToFinish();
        }
        else {
            while (numOfThreads-- > 0) {
                new Thread(() -> {
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                        imageWriter.writePixel(pixel.col, pixel.row, SuperSampling(nX,
                                nY, pixel.col, pixel.row, antiAliasing, false));
                }).start();
            }
            Pixel.waitToFinish();
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
     * Constructs a list of rays for a given image pixel.
     *
     * @param nX     The number of pixels in the X direction.
     * @param nY     The number of pixels in the Y direction.
     * @param //xPixel      The X index of the pixel.
     * @param //yPixel      The Y index of the pixel.
     * @return The list of constructed rays.
     */

    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        List<Ray> rays = new LinkedList<>();
        Point centralPixel = getCenterOfPixel(nX, nY, j, i);
        double rY = height / nY / antiAliasing;
        double rX = width / nX / antiAliasing;
        double x, y;

        for (int rowNumber = 0; rowNumber < antiAliasing; rowNumber++) {
            for (int colNumber = 0; colNumber < antiAliasing; colNumber++) {
                y = -(rowNumber - (antiAliasing - 1d) / 2) * rY;
                x = (colNumber - (antiAliasing - 1d) / 2) * rX;
                Point pIJ = centralPixel;
                if (y != 0) pIJ = pIJ.add(vUp.scale(y));
                if (x != 0) pIJ = pIJ.add(vRight.scale(x));
                rays.add(new Ray(p0, pIJ.subtract(p0)));
            }
        }
        return rays;
    }

    /**
     * Checks the color of the pixel with the help of individual rays and averages between them and only
     * if necessary continues to send beams of rays in recursion
     * @param nX amount of pixels by length
     * @param nY amount of pixels by width
     * @param j The position of the pixel relative to the y-axis
     * @param i The position of the pixel relative to the x-axis
     * @param numOfRays The amount of rays sent
     * @return Pixel color
     */
    private Color SuperSampling(int nX, int nY, int j, int i,  int numOfRays, boolean adaptiveAlising)  {
        Vector Vright = vRight;
        Vector Vup = vUp;
        Point cameraLoc = this.getP0();
        int numOfRaysInRowCol = (int)Math.floor(Math.sqrt(numOfRays));
        if(numOfRaysInRowCol == 1)  return rayTracer.traceRay(constructRayThroughPixel(nX, nY, j, i));

        Point pIJ = getCenterOfPixel(nX, nY, j, i);

        double rY = alignZero(height / nY);
        // the ratio Rx = w/Nx, the width of the pixel
        double rX = alignZero(width / nX);


        double PRy = rY/numOfRaysInRowCol;
        double PRx = rX/numOfRaysInRowCol;
        if (adaptiveAlising)
            return rayTracer.AdaptiveSuperSamplingRec(pIJ, rX, rY, PRx, PRy,cameraLoc,Vright, Vup,null);
        else
            return rayTracer.RegularSuperSampling(pIJ, rX, rY, PRx, PRy,cameraLoc,Vright, Vup,null);
    }

    /**
     * get the center point of the pixel in the view plane
     * @param nX number of pixels in the width of the view plane
     * @param nY number of pixels in the height of the view plane
     * @param j  index row in the view plane
     * @param i  index column in the view plane
     * @return the center point of the pixel
     */
    private Point getCenterOfPixel(int nX, int nY, int j, int i) {
        // calculate the ratio of the pixel by the height and by the width of the view plane
        // the ratio Ry = h/Ny, the height of the pixel
        double rY = alignZero(height / nY);
        // the ratio Rx = w/Nx, the width of the pixel
        double rX = alignZero(width / nX);

        // Xj = (j - (Nx -1)/2) * Rx
        double xJ = alignZero((j - ((nX - 1d) / 2d)) * rX);
        // Yi = -(i - (Ny - 1)/2) * Ry
        double yI = alignZero(-(i - ((nY - 1d) / 2d)) * rY);

        Point pIJ = centerPoint;

        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }
        return pIJ;
    }

    /**
     * construct ray through a pixel in the view plane
     * nX and nY create the resolution
     * @param nX number of pixels in the width of the view plane
     * @param nY number of pixels in the height of the view plane
     * @param j  index row in the view plane
     * @param i  index column in the view plane
     * @return ray that goes through the pixel (j, i)  Ray(p0, Vi,j)
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pIJ = getCenterOfPixel(nX, nY, j, i); // center point of the pixel

        //Vi,j = Pi,j - P0, the direction of the ray to the pixel(j, i)
        Vector vIJ = pIJ.subtract(p0);
        return new Ray(p0, vIJ);
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
        // every time that we chang the distance from the view plane
        // we will calculate the center point of the view plane aging
        centerPoint = p0.add(vTo.scale(this.distance));
        return this;
    }

    /**
     * set the adaptive
     * @return the Camera object
     */
    public Camera setadaptive(boolean adaptive) {
        this.adaptive = adaptive;
        return this;
    }

    /**
     * set the threadsCount
     * @return the Camera object
     */
    public Camera setMultiThreading(int threadsCount) {
        this.numOfThreads = threadsCount;
        return this;

    }
}
