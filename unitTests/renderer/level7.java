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

//    @Test
//    public void createAttractiveScene() {
//        Scene scene = new Scene("Attractive Scene").setBackground(new Color(255, 192, 203));
//        Camera camera = new Camera(new Point(0, -650, 0), new Vector(0, 1, 0), new Vector(0, 0, 1));
//        camera.setVPSize(150, 150).setVPDistance(100).setAntiAliasing(200);
//
//        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);
//
//        double radius = 50;
//        double height = Math.sqrt(3) * radius;
//
//        Point A1 = new Point(-280,0,150);
//        Point B1 = new Point(-250,50,150);
//        Point C1 = new Point(-300,200,400);
//        Point D1 = new Point(-150,150,150);
//        Point E1 = new Point(-500,150,150);
//        Point F1 = new Point(-340,300,150);
//
//        Point A2 = new Point(280,0,150);
//        Point B2 = new Point(250,50,150);
//        Point C2 = new Point(300,200,400);
//        Point D2 = new Point(150,150,150);
//        Point E2 = new Point(500,150,150);
//        Point F2 = new Point(340,300,150);
//        // Add spheres with different colors and positions
//        scene.geometries.add(
//                new Sphere(radius, new Point(0, 0, height * 2.3)).setMaterial(material).setEmission(new Color(255, 0, 0)),
//                new Sphere(radius, new Point(-radius, -height, height)).setMaterial(material).setEmission(new Color(0, 255, 0)),
//                new Sphere(radius, new Point(radius, -height, height)).setMaterial(material).setEmission(new Color(0, 0, 255)),
//                new Sphere(radius, new Point(-radius * 2, -height * 2, -10)).setMaterial(material).setEmission(new Color(255, 255, 0)),
//                new Sphere(radius, new Point(0, -height * 2, -10)).setMaterial(material).setEmission(new Color(255, 0, 255)),
//                new Sphere(radius, new Point(radius * 2, -height * 2, -10)).setMaterial(material).setEmission(new Color(0, 255, 255)),
//                new Sphere(radius, new Point(-radius * 3, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 0, 128)),
//                new Sphere(radius, new Point(-radius, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 128, 30)),
//                new Sphere(radius, new Point(radius, -height * 3, -height)).setMaterial(material).setEmission(new Color(0, 128, 128)),
//                new Sphere(radius, new Point(radius * 3, -height * 3, -height)).setMaterial(material).setEmission(new Color(128, 0, 128))
//        );
//
//        scene.geometries.add(
//               // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
//                new Triangle(A1,E1,D1).setMaterial(material).setEmission(new Color(128, 0, 128)),
//                new Triangle(F1,E1,D1).setMaterial(material).setEmission(new Color(128, 0, 128)),
//                new Triangle(A1,E1,C1).setMaterial(material).setEmission(new Color(255, 0, 0)),
//                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
//                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
//                new Triangle(A1,D1,C1).setMaterial(material).setEmission(new Color(120, 49, 0)),
//                new Triangle(F1,E1,C1).setMaterial(material).setEmission(new Color(42, 0, 100)),
//                new Triangle(F1,D1,C1).setMaterial(material).setEmission(new Color(0, 235, 0))
//        );
//
//        scene.geometries.add(
//                // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
//                new Triangle(A2,E2,D2).setMaterial(material).setEmission(new Color(128, 0, 128)),
//                new Triangle(F2,E2,D2).setMaterial(material).setEmission(new Color(128, 0, 128)),
//                new Triangle(A2,E2,C2).setMaterial(material).setEmission(new Color(255, 0, 0)),
//                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
//                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
//                new Triangle(A2,D2,C2).setMaterial(material).setEmission(new Color(120, 49, 0)),
//                new Triangle(F2,E2,C2).setMaterial(material).setEmission(new Color(42, 0, 100)),
//                new Triangle(F2,D2,C2).setMaterial(material).setEmission(new Color(0, 235, 0))
//        );
//
//
//
//        // Add a floor plane with reflection
//        Material floorMaterial = new Material().setkD(0.2).setnShininess(50).setkT(0.65).setkR(0.8).setkS(0.4);
//        scene.geometries.add(
//                new Plane(new Point(-1000, -1000, -height - 1), new Point(1000, -1000, -height - 1), new Point(0, 0, -height - 1))
//                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
//                new Triangle(new Point(-500, 500, 0), new Point(0, 500, 0), new Point(0, 500, 500))
//                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
//                new Triangle(new Point(-500, 500, 0), new Point(-500, 500, 500), new Point(0, 500, 500))
//                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
//                new Triangle(new Point(500, 500, 0), new Point(0, 500, 0), new Point(0, 500, 500))
//                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
//                new Triangle(new Point(500, 500, 0), new Point(500, 500, 500), new Point(0, 500, 500))
//                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10))
//
//
//        );
//
//        // Add point lights for illumination
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 50, -100)));
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-200, -200, 300)));
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 0, height * 2.3)));
//
//        camera.setImageWriter(new ImageWriter("attractive_scene", 800, 800))
//                .setRayTracer(new RayTracerBasic(scene))
//                .renderImage()
//                .writeToImage();
//    }

    @Test
    public void antianalisingAttractiveScene() {
        Scene scene = new Scene("Attractive Scene").setBackground(new Color(255, 192, 203));
        Camera camera = new Camera(new Point(0, -650, 0), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);

        double radius = 50;
        double height = Math.sqrt(3) * radius;

        Point A1 = new Point(-280,0,150);
        Point B1 = new Point(-250,50,150);
        Point C1 = new Point(-350,200,400);
        Point D1 = new Point(-150,150,150);
        Point E1 = new Point(-500,150,150);
        Point F1 = new Point(-350,300,150);

        Point A2 = new Point(280,0,150);
        Point B2 = new Point(250,50,150);
        Point C2 = new Point(300,200,400);
        Point D2 = new Point(150,150,150);
        Point E2 = new Point(500,150,150);
        Point F2 = new Point(340,300,150);

        Point A3 = new Point(0,0,350);
        Point B3 = new Point(-50,50,350);
        Point C3 = new Point(0,200,600);
        Point D3 = new Point(-150,150,350);
        Point E3 = new Point(170,150,350);
        Point F3 = new Point(0,300,350);

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

        scene.geometries.add(
                // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A1,E1,D1).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(F1,E1,D1).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A1,E1,C1).setMaterial(material).setEmission(new Color(255, 0, 0)),
                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
                new Triangle(A1,D1,C1).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F1,E1,C1).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F1,D1,C1).setMaterial(material).setEmission(new Color(0, 235, 0))
        );

        scene.geometries.add(
                // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A2,E2,D2).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(F2,E2,D2).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A2,E2,C2).setMaterial(material).setEmission(new Color(255, 0, 0)),
                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
                new Triangle(A2,D2,C2).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F2,E2,C2).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F2,D2,C2).setMaterial(material).setEmission(new Color(0, 235, 0))
        );

        scene.geometries.add(
                // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
              //  new Triangle(A3,E3,D3).setMaterial(material).setEmission(new Color(128, 0, 128)),
             //   new Triangle(F3,E3,D3).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A3,E3,C3).setMaterial(material).setEmission(new Color(255, 0, 0)),
                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
                new Triangle(A3,D3,C3).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F3,E3,C3).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F3,D3,C3).setMaterial(material).setEmission(new Color(0, 235, 0))
        );



        // Add a floor plane with reflection
        Material floorMaterial = new Material().setkD(0.2).setnShininess(50).setkT(0.65).setkR(0.8).setkS(0.4);
        scene.geometries.add(
                new Plane(new Point(-1000, -1000, -height - 1), new Point(1000, -1000, -height - 1), new Point(0, 0, -height - 1))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(-500, 500, 0), new Point(-100, 500, 0), new Point(-100, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(-500, 500, 0), new Point(-500, 500, 400), new Point(0, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(500, 500, 0), new Point(100, 500, 0), new Point(100, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(500, 500, 0), new Point(500, 500, 400), new Point(100, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10))


        );

        // Add point lights for illumination
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 50, -100)));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-200, -200, 300)));
        // scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0,200,599)));
       // scene.lights.add(new SpotLight(new Color(green),new Point(0,0,0),new Vector(0,1,50)));
        camera.setImageWriter(new ImageWriter("antianalisingAttractive_scene", 800, 800)).setAntiAliasing(10).setMultiThreading(4).setadaptive(true)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void NotAntianalisingAttractiveScene() {
        Scene scene = new Scene("Attractive Scene").setBackground(new Color(255, 192, 203));
        Camera camera = new Camera(new Point(0, -650, 0), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);

        double radius = 50;
        double height = Math.sqrt(3) * radius;

        Point A1 = new Point(-280,0,150);
        Point B1 = new Point(-250,50,150);
        Point C1 = new Point(-350,200,400);
        Point D1 = new Point(-150,150,150);
        Point E1 = new Point(-500,150,150);
        Point F1 = new Point(-350,300,150);

        Point A2 = new Point(280,0,150);
        Point B2 = new Point(250,50,150);
        Point C2 = new Point(300,200,400);
        Point D2 = new Point(150,150,150);
        Point E2 = new Point(500,150,150);
        Point F2 = new Point(340,300,150);

        Point A3 = new Point(0,0,350);
        Point B3 = new Point(-50,50,350);
        Point C3 = new Point(0,200,600);
        Point D3 = new Point(-150,150,350);
        Point E3 = new Point(170,150,350);
        Point F3 = new Point(0,300,350);

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

        scene.geometries.add(
                // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A1,E1,D1).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(F1,E1,D1).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A1,E1,C1).setMaterial(material).setEmission(new Color(255, 0, 0)),
                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
                new Triangle(A1,D1,C1).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F1,E1,C1).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F1,D1,C1).setMaterial(material).setEmission(new Color(0, 235, 0))
        );

        scene.geometries.add(
                // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A2,E2,D2).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(F2,E2,D2).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A2,E2,C2).setMaterial(material).setEmission(new Color(255, 0, 0)),
                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
                new Triangle(A2,D2,C2).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F2,E2,C2).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F2,D2,C2).setMaterial(material).setEmission(new Color(0, 235, 0))
        );

        scene.geometries.add(
                // new Triangle(A,B,D).setMaterial(material).setEmission(new Color(128, 0, 128)),
                //  new Triangle(A3,E3,D3).setMaterial(material).setEmission(new Color(128, 0, 128)),
                //   new Triangle(F3,E3,D3).setMaterial(material).setEmission(new Color(128, 0, 128)),
                new Triangle(A3,E3,C3).setMaterial(material).setEmission(new Color(255, 0, 0)),
                //new Triangle(A,B,C).setMaterial(material).setEmission(new Color(0, 255, 220)),
                //new Triangle(B,D,C).setMaterial(material).setEmission(new Color(42, 235, 0)),
                new Triangle(A3,D3,C3).setMaterial(material).setEmission(new Color(120, 49, 0)),
                new Triangle(F3,E3,C3).setMaterial(material).setEmission(new Color(42, 0, 100)),
                new Triangle(F3,D3,C3).setMaterial(material).setEmission(new Color(0, 235, 0))
        );



        // Add a floor plane with reflection
        Material floorMaterial = new Material().setkD(0.2).setnShininess(50).setkT(0.65).setkR(0.8).setkS(0.4);
        scene.geometries.add(
                new Plane(new Point(-1000, -1000, -height - 1), new Point(1000, -1000, -height - 1), new Point(0, 0, -height - 1))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(-500, 500, 0), new Point(-100, 500, 0), new Point(-100, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(-500, 500, 0), new Point(-500, 500, 400), new Point(0, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(500, 500, 0), new Point(100, 500, 0), new Point(100, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10)),
                new Triangle(new Point(500, 500, 0), new Point(500, 500, 400), new Point(100, 500, 400))
                        .setMaterial(floorMaterial).setEmission(new Color(30, 30, 10))


        );

        // Add point lights for illumination
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 50, -100)));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(-200, -200, 300)));
        // scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0,200,599)));
        // scene.lights.add(new SpotLight(new Color(green),new Point(0,0,0),new Vector(0,1,50)));
        camera.setImageWriter(new ImageWriter("NotAntianalisingAttractive_scene", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void antianalising(){
        Scene scene = new Scene("trying").setBackground(new Color(255, 192, 203));


        Point a= new Point(100,100,0);
        Point b= new Point(100,-100,0);
        Point c= new Point(-100,-100,0);
        Point d= new Point(-100,100,0);
        Point sphere1= new Point(30,-50,40);
        Point sphere2= new Point(30,50,40);
        Point light1= new Point(200,-16,200);
        Point light2= new Point(200,16,200);
        Point camera10= new Point(600,0,200);
        Camera camera100 = new Camera(camera10, new Vector(-6, 0, -1.5).normalize(), new Vector(-1.5, 0, 6).normalize())   //
                .setVPSize(300, 300).setVPDistance(700)                                                                       //
                .setRayTracer(new RayTracerBasic(scene));

        scene.geometries.add( //
                new Polygon(a,b,c,d)// flor
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setkS(0.5).setkD(0.5).setnShininess(60)), //


                new Sphere(30d,sphere1) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
                new Sphere(30d,sphere2) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)));
        scene.lights.add( //
                new SpotLight(new Color(WHITE), light1, new Vector(-170, -20, -160)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE),light2, new Vector(-170, 20, -160)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add( //
                new SpotLight(new Color(WHITE), new Point(-180,-40,255), new Vector(156, -10, -215)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE),light2, new Vector(156, 10, -215)) //
                        .setKl(4E-4).setKq(2E-5));

        camera100.setImageWriter(new ImageWriter("antianalising", 600, 600)).setAntiAliasing(1) //
                .renderImage() //
                .writeToImage();



    }

    @Test
    public void notAntianalsing(){
        Scene scene = new Scene("trying").setBackground(new Color(255, 192, 203));

        //scene.setAmbientLight(new AmbientLight(new Color(lightGray), 0.15));


        Point a= new Point(100,100,0);
        Point b= new Point(100,-100,0);
        Point c= new Point(-100,-100,0);
        Point d= new Point(-100,100,0);
        Point sphere1= new Point(30,-50,40);
        Point sphere2= new Point(30,50,40);
        Point light1= new Point(200,-16,200);
        Point light2= new Point(200,16,200);
        Point camera10= new Point(600,0,200);
        Camera camera100 = new Camera(camera10, new Vector(-6, 0, -1.5).normalize(), new Vector(-1.5, 0, 6).normalize())   //
                .setVPSize(300, 300).setVPDistance(700)                                                                       //
                .setRayTracer(new RayTracerBasic(scene));

        scene.geometries.add( //
                new Polygon(a,b,c,d)// flor
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setkS(0.5).setkD(0.5).setnShininess(60)), //


                new Sphere(30d,sphere1) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)), //
                new Sphere(30d,sphere2) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(30)));
        scene.lights.add( //
                new SpotLight(new Color(WHITE), light1, new Vector(-170, -20, -160)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE),light2, new Vector(-170, 20, -160)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add( //
                new SpotLight(new Color(WHITE), new Point(-180,-40,255), new Vector(156, -10, -215)) //
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new SpotLight(new Color(WHITE),light2, new Vector(156, 10, -215)) //
                        .setKl(4E-4).setKq(2E-5));

        camera100.setImageWriter(new ImageWriter("notAntianalsing", 600, 600)) //
                .renderImage() //
                .writeToImage();



    }
}

