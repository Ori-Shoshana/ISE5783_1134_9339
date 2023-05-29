package lighting;

import primitives.*;

 /**
 * The PointLight class represents a point light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource{
    private final Point position;

    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

     /**
      * Constructs a PointLight object with the given intensity and position.
      *
      * @param intensity the intensity of the light source
      * @param position the position of the light source
      */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
     /**
      * Returns the direction from the light source to a given point in the scene.
      *
      * @param point the point in the scene
      * @return the direction from the light source to the point
      */
    @Override
    public Vector getL(Point point){
        return point.subtract(position).normalize();
    }

     @Override
     public double getDistance(Point point) {
         return position.distance(point);
     }

     /**
      * Returns the intensity of the light at a given point in the scene.
      *
      * @param point the point in the scene
      * @return the intensity of the light at the point
      */

    @Override
    public Color getIntensity(Point point) {
        double d = point.distance(this.position);                           // distance from the light source
        return this.getIntensity().reduce (kC + kL * d + kQ * d * d);
    }
     /**
      * Sets the constant attenuation factor of the light source.
      *
      * @param kC the constant attenuation factor
      * @return this PointLight object
      */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

     /**
      * Sets the linear attenuation factor of the light source.
      *
      * @param kL the linear attenuation factor
      * @return this PointLight object
      */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

     /**
      * Sets the quadratic attenuation factor of the light source.
      *
      * @param kQ the quadratic attenuation factor
      * @return this PointLight object
      */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}