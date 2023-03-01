package geometries;

import primitives.Point;
import primitives.Vector;

/** This class will serve all geometries classes based on vector
 * @author Amir Hay and ori */
public interface Geometry {
    public Vector getNormal(Point point);
}
