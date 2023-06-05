package renderer;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import static java.awt.Color.*;
import scene.Scene;
public class level7 {
    @Test
    public void multipleShapes() {
        Scene scene = new Scene("Multiple Shapes").setBackground(new Color(255, 255, 255));
        Camera camera = new Camera(new Point(0, -600, 10), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);
        Material material2 = new Material().setkD(0.4).setkS(0.3).setnShininess(50).setkT(0.4).setkR(0);
        Material material1 = new Material().setkD(0.4).setkS(0.3).setnShininess(50).setkT(0.7).setkR(0);

        scene.geometries.add(
                new Sphere(100, new Point(0, 0, 50)).setMaterial(material).setEmission(new Color(red)),
                new Sphere(100, new Point(200, -100, 50)).setMaterial(material).setEmission(new Color(green)),
                new Sphere(100, new Point(-200, -100, 50)).setMaterial(material).setEmission(new Color(blue)),
                new Sphere(50, new Point(0, 0, -100)).setMaterial(material).setEmission(new Color(orange)),
                new Sphere(50, new Point(0, -300, -80)).setMaterial(material2).setEmission(new Color(cyan)),
                new Plane(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                        .setMaterial(material1).setEmission(new Color(255, 0, 0)),
        new Plane(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                .setMaterial(material1).setEmission(new Color(255, 0, 0))
        );

        scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 50, -100)));

        camera.setImageWriter(new ImageWriter("final level 7", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}

