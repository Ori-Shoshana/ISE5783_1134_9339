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


    @Test
    public void createSpectacularScene() {
        Scene scene = new Scene("Spectacular Scene").setBackground(new Color(0, 0, 0));
        Camera camera = new Camera(new Point(0, -600, 10), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);

        double radius = 50;
        double height = Math.sqrt(3) * radius;

        // Add spheres with different colors and positions
        scene.geometries.add(
                new Sphere(radius, new Point(0, 0, height * 2.4)).setMaterial(material).setEmission(new Color(255, 0, 0)),
                new Sphere(radius, new Point(-radius, -height, height)).setMaterial(material).setEmission(new Color(0, 255, 0)),
                new Sphere(radius, new Point(radius, -height, height)).setMaterial(material).setEmission(new Color(0, 0, 255)),
                new Sphere(radius, new Point(-radius * 2, -height * 2, 0)).setMaterial(material).setEmission(new Color(255, 255, 0)),
                new Sphere(radius, new Point(0, -height * 2, 0)).setMaterial(material).setEmission(new Color(255, 0, 255)),
                new Sphere(radius, new Point(radius * 2, -height * 2, 0)).setMaterial(material).setEmission(new Color(0, 255, 255)),
                new Sphere(radius, new Point(-radius * 3, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Sphere(radius, new Point(-radius, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 128, 0)),
                new Sphere(radius, new Point(radius, -height * 3, -height)).setMaterial(material).setEmission(new Color(0, 128, 128)),
                new Sphere(radius, new Point(radius * 3, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 0, 128))
        );

        // Add a floor plane with reflection
        Material floorMaterial = new Material().setkD(0.4).setkS(0.2).setnShininess(50).setkT(0).setkR(0.8).setkS(0.2);
        scene.geometries.add(
                new Plane(new Point(-1000, -1000, -height - 1), new Point(1000, -1000, -height - 1), new Point(0, 0, -height - 1))
                        .setMaterial(floorMaterial).setEmission(new Color(50, 50, 50))
        );

        // Add a triangle
        scene.geometries.add(
                new Triangle(
                        new Point(0, -height * 2, 50),
                        new Point(-radius, -height * 2, -height * 2),
                        new Point(radius, -height * 2, -height * 2)
                ).setMaterial(material).setEmission(new Color(255, 255, 255))
        );

        // Add point lights for illumination
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 50, -100)));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-200, -200, 300)));

        camera.setImageWriter(new ImageWriter("spectacular_scene", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void createAttractiveScene() {
        Scene scene = new Scene("Attractive Scene").setBackground(new Color(255, 192, 203));
        Camera camera = new Camera(new Point(0, -600, 10), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);

        double radius = 50;
        double height = Math.sqrt(3) * radius;

        // Add spheres with different colors and positions
        scene.geometries.add(
                new Sphere(radius, new Point(0, 0, height * 2.3)).setMaterial(material).setEmission(new Color(255, 0, 0)),
                new Sphere(radius, new Point(-radius, -height, height)).setMaterial(material).setEmission(new Color(0, 255, 0)),
                new Sphere(radius, new Point(radius, -height, height)).setMaterial(material).setEmission(new Color(0, 0, 255)),
                new Sphere(radius, new Point(-radius * 2, -height * 2, -10)).setMaterial(material).setEmission(new Color(255, 255, 0)),
                new Sphere(radius, new Point(0, -height * 2, -10)).setMaterial(material).setEmission(new Color(255, 0, 255)),
                new Sphere(radius, new Point(radius * 2, -height * 2, -10)).setMaterial(material).setEmission(new Color(0, 255, 255)),
                new Sphere(radius, new Point(-radius * 3, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Sphere(radius, new Point(-radius, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 128, 30)),
                new Sphere(radius, new Point(radius, -height * 3, -height)).setMaterial(material).setEmission(new Color(0, 128, 128)),
                new Sphere(radius, new Point(radius * 3, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 0, 128))
        );

        // Add a floor plane with reflection
        Material floorMaterial = new Material().setkD(0.2).setnShininess(50).setkT(0.65).setkR(0.8).setkS(0.4);
        scene.geometries.add(
                new Plane(new Point(-1000, -1000, -height - 1), new Point(1000, -1000, -height - 1), new Point(0, 0, -height - 1))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10))
        );

        // Add point lights for illumination
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 50, -100)));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-200, -200, 300)));

        camera.setImageWriter(new ImageWriter("attractive_scene", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


}

