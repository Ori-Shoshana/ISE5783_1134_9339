package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

public class Camera {
    /**
     * a class that represents a camera.
     */
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;


    public Camera(Point p0, Vector vTo, Vector vUp) throws IllegalArgumentException {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("The vectors are not orthogonal");
        }
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }


    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
        /** Point pc = p0.add(vTo.scale(distance));     // center of the view plane
         double Ry = height / nY;                      // Ratio - pixel height
         double Rx = width / nX;                       // Ratio - pixel width

         double yJ = alignZero(-(i - (nY - 1) / 2d) * Ry);       // move pc Yi pixels
         double xJ = alignZero((j - (nX - 1) / 2d) * Rx);        // move pc Xj pixels

         Point PIJ = pc;
         if (!isZero(xJ)) PIJ = PIJ.add(vRight.scale(xJ));
         if (!isZero(yJ)) PIJ = PIJ.add(vUp.scale(yJ));

         return new Ray(p0, PIJ.subtract(p0));*/
    }

    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

}
