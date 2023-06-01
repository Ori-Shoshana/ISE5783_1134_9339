package renderer;


import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import org.junit.jupiter.api.Test;
import static java.awt.Color.*;
import static primitives.Double3.ZERO;

import org.junit.jupiter.api.Test;

import geometries.*;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

import scene.*;

public class level7 {

    @Test
    public void multiColoredShapes() {
        Scene scene = new Scene("Multi-colored Shapes").setBackground(new Color(255, 255, 255));
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPSize(500, 500).setVPDistance(1000);

        scene.geometries.add(
                new Sphere(100, new Point(0, 0, -200)).setEmission(new Color(255, 0, 0)),
                new Sphere(80, new Point(-150, 0, -200)).setEmission(new Color(0, 255, 0)),
                new Sphere(60, new Point(150, 0, -200)).setEmission(new Color(0, 0, 255)),
                new Triangle(new Point(-200, -200, -200), new Point(-200, 200, -200), new Point(0, 0, -400))
                        .setEmission(new Color(255, 255, 0)),
                new Triangle(new Point(200, -200, -200), new Point(200, 200, -200), new Point(0, 0, -400))
                        .setEmission(new Color(0, 255, 255))
        );

        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(200, 200, 200), new Vector(-1, -1, -1)));

        camera.setImageWriter(new ImageWriter("multiColoredShapes", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void complexGeometricComposition() {
        Scene scene = new Scene("Complex Geometric Composition").setBackground(new Color(255, 255, 255));
        Camera camera = new Camera(new Point(0, -600, 10), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(500, 500).setVPDistance(100);

        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);

        scene.geometries.add(
                new Sphere(100, new Point(0, 0, 0)).setMaterial(material),
                new Sphere(80, new Point(200, -100, 50)).setMaterial(material),
                new Sphere(80, new Point(-200, -100, 50)).setMaterial(material),
                new Triangle(new Point(-300, -300, -200), new Point(300, -300, -200), new Point(0, 300, -200))
                        .setMaterial(material),
                new Plane(new Point(0, 0, -300), new Vector(0, 0, 1)).setEmission(new Color(255, 255, 255))
        );

        //scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, -50, 25), new Vector(0, 2, -1));
        camera.setImageWriter(new ImageWriter("multiColoredShapes2", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void multipleShapes() {
        Scene scene = new Scene("Multiple Shapes").setBackground(new Color(255, 255, 255));
        Camera camera = new Camera(new Point(0, -600, 10), new Vector(0, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setkD(0.4).setkS(1).setnShininess(50).setkT(0).setkR(0.5).setkS(0.5);
        Material material1 = new Material().setkD(0.4).setkS(0.3).setnShininess(50).setkT(0.7).setkR(0);

        scene.geometries.add(
                new Sphere(100, new Point(0, 0, 0)).setMaterial(material).setEmission(new Color(100, 100, 100)),
                new Sphere(100, new Point(200, -100, 50)).setMaterial(material).setEmission(new Color(100, 100, 100)),
                new Sphere(100, new Point(-200, -100, 50)).setMaterial(material).setEmission(new Color(100, 100, 100)),
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                        .setMaterial(material1).setEmission(new Color(255, 0, 0)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(material1).setEmission(new Color(0, 255, 0))
        );

        scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(0, 0, -1)));
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(200, 50, -100)));

        camera.setImageWriter(new ImageWriter("multipleShapes3", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void multipleShapes4() {
        Scene scene = new Scene("Multiple Shapes");
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(500, 500).setVPDistance(1000);

        scene.geometries.add(
                new Sphere(50d, new Point(-200, -200, -50)).setEmission(new Color(255, 0, 0)),
                new Sphere(50d, new Point(200, -200, -50)).setEmission(new Color(0, 255, 0)),
                new Sphere(50d, new Point(-200, 200, -50)).setEmission(new Color(0, 0, 255)),
                new Sphere(50d, new Point(200, 200, -50)).setEmission(new Color(255, 255, 0)),
                new Triangle(new Point(-150, -150, -150), new Point(150, -150, -150), new Point(0, 150, -150))
                        .setEmission(new Color(255, 0, 255)),
                new Triangle(new Point(-150, -150, -150), new Point(0, 150, -150), new Point(-150, 150, -150))
                        .setEmission(new Color(0, 255, 255)),
                new Triangle(new Point(150, -150, -150), new Point(0, 150, -150), new Point(150, 150, -150))
                        .setEmission(new Color(255, 255, 255)),
                new Plane(new Point(0, 0, -200), new Vector(0, 0, 1))
                        .setEmission(new Color(128, 128, 128))
        );

        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, 0, 500), new Vector(0, 0, -1))
                .setKl(0.0001).setKq(0.000005));

        camera.setImageWriter(new ImageWriter("multipleShapes4", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


    @Test
    public void spiral() {
        Scene scene = new Scene("Spiral");
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(500, 500).setVPDistance(1000);

        double radius = 100;
        double height = 10;
        double rotationAngle = 10;

        for (int i = 1; i < 360; i += rotationAngle) {
            double x = radius * Math.cos(Math.toRadians(i));
            double y = radius * Math.sin(Math.toRadians(i));
            double z = height * (i / rotationAngle);

            // Check for zero vector
            Vector normal = new Vector(x, y, z).normalize();
            if (normal.equals(ZERO)) {
                normal = new Vector(0, 0, 1); // Assign a non-zero default vector
            }

            scene.geometries.add(new Triangle(new Point(x+1, y+1, z+1),
                    new Point(x + 10, y + 10, z),
                    new Point(x - 10, y - 10, z))
                    .setEmission(new Color(i % 255, 0, 0)));
        }

        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, 0, 500), new Vector(0, 0, -1))
                .setKl(0.0001).setKq(0.000005));

        camera.setImageWriter(new ImageWriter("spiral1", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void snookerTable() {
        Scene scene = new Scene("Snooker Table");
        Camera camera = new Camera(new Point(0, -3000, 1000), new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setVPSize(1000, 1000).setVPDistance(1000);

        // Add the snooker table as a plane
        //Plane table =
        scene.geometries.add(new Plane(new Point(0, 0, 0), new Vector(0, 0, 1))
               // .setDiffuse(new Color(40, 110, 70))
                //.setSpecular(0.3)
                .setMaterial(new Material()
                        .setnShininess(50)));
        // scene.geometries.add(table);

        // Add the snooker balls
        int numRows = 3;
        int ballsPerRow = 4;
        double ballRadius = 50;
        double spacing = 150;

        // Calculate the table dimensions
        double tableWidth = (ballsPerRow - 1) * spacing + 2 * ballRadius;
        double tableLength = (numRows - 1) * spacing + 2 * ballRadius;

        // Calculate the starting position for the first ball
        double startX = -tableWidth / 2 + ballRadius;
        double startY = -tableLength / 2 + ballRadius;

        // Create the snooker balls in a grid pattern
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < ballsPerRow; col++) {
                double x = startX + col * spacing;
                double y = startY + row * spacing;
                double z = ballRadius;

                // Adjust the position to create a pyramid-like pattern
                if (row % 2 == 0) {
                    x += col % 2 == 0 ? spacing / 2 : -spacing / 2;
                } else {
                    x += col % 2 == 0 ? -spacing / 2 : spacing / 2;
                }

                // Create a sphere for each snooker ball
               // Sphere ball =
                scene.geometries.add( new Sphere(ballRadius, new Point(x, y, z))
                        .setEmission(new Color(255,255,255))
                        .setMaterial(new Material()
                        //.setDiffuse(0.7)
                        //.setSpecular(0.4)
                                .setnShininess(30)));

               // scene.geometries.add(ball);
            }
        }

        // Add a light source above the table
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(0, 0, 1000))
                .setKl(0.0005)
                .setKq(0.00005));

        camera.setImageWriter(new ImageWriter("snooker_table1", 1000, 1000))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


    @Test
    public void snookerTable2() {
        Scene scene = new Scene("Snooker Table");
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(150, 150).setVPDistance(1000);

// Create a grid of spheres
        int gridSize = 5;
        double sphereRadius = 30;
        double spacing = 80;
        Color[] colors = {new Color(RED), new Color(GREEN), new Color(BLUE), new Color(YELLOW), new Color(MAGENTA)};
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Point center = new Point((i - gridSize / 2) * spacing, (j - gridSize / 2) * spacing, -50);
                Color color = colors[(i + j) % colors.length];
                Material material = new Material().setkD(0.4).setkS(0.3).setnShininess(100);
                scene.geometries.add(new Sphere(sphereRadius, center).setEmission(color).setMaterial(material));
            }
        }

        scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("multipleSpheres45", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


    @Test
    public void spaceScene() {
        Scene scene = new Scene("Space Scene");
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0))
                .setVPSize(800, 800).setVPDistance(1000);

        // Set background color to black
        scene.setBackground(new Color(0, 0, 0));

        // Add a sphere representing the planet
        Geometry planet = new Sphere(200,new Point(0, 0, 0) )
                .setEmission(new Color(0, 0, 255)) // Blue color
                .setMaterial(new Material().setnShininess(100).setkT(0.2).setkT(0.2));
        scene.geometries.add(planet);

        // Add some stars as small spheres
        int starCount = 100;
        double starRadius = 2;
        double starDistance = 1000;

        for (int i = 0; i < starCount; i++) {
            double x = Math.random() * 2000 - 1000;
            double y = Math.random() * 2000 - 1000;
            double z = Math.random() * starDistance;

            Point starCenter = new Point(x, y, z);
            Geometry star = new Sphere(starRadius,starCenter)
                    .setEmission(new Color(255, 255, 255)) // White color
                    .setMaterial(new Material().setnShininess(50));
            scene.geometries.add(star);
        }

        // Set up lighting and rendering
        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, 0, 0), new Vector(0, 0, 1))
                .setKl(0.1).setKq(0.5));

        camera.setImageWriter(new ImageWriter("space_scene", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }


    @Test
    public void nebulaScene() {
        Scene scene = new Scene("Nebula Scene");
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0))
                .setVPSize(800, 800).setVPDistance(1000);

        // Set background color to black
        scene.setBackground(new Color(0, 0, 0));

        // Add a sphere representing a celestial object (e.g., a nebula)
        Geometry celestialObject = new Sphere(300,new Point(0, 0, 0))
                .setEmission(new Color(255, 165, 0)) // Orange color
                .setMaterial(new Material().setkD(0.7).setkS(0.3).setnShininess(100));
        scene.geometries.add(celestialObject);

        // Add particles as small spheres representing stars or cosmic dust
        int particleCount = 1000;
        double particleRadius = 2;
        double particleDistance = 1000;

        for (int i = 0; i < particleCount; i++) {
            double x = Math.random() * 2000 - 1000;
            double y = Math.random() * 2000 - 1000;
            double z = Math.random() * particleDistance;

            Point particleCenter = new Point(x, y, z);
            Geometry particle = new Sphere(particleRadius, particleCenter)
                    .setEmission(new Color(255, 255, 255)) // White color
                    .setMaterial(new Material().setkD(0.2).setkS(0.8).setnShininess(50));
            scene.geometries.add(particle);
        }

        // Set up lighting and rendering
        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(200, -200, -1000), new Vector(0, 0, 1))
                .setKl(0.0001).setKq(0.000005));

        camera.setImageWriter(new ImageWriter("nebula_scene", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void spectacularScene() {
        Scene scene = new Scene("Spectacular Scene");
        Camera camera = new Camera(new Point(0, 0, -2000), new Vector(0, 0, 1), new Vector(0, 1, 0))
                .setVPSize(800, 800).setVPDistance(1000);

        // Set background color to black
        scene.setBackground(new Color(0, 0, 0));

        // Create a large sphere representing a galaxy
        Geometry galaxy = new Sphere(2000,new Point(0, 0, 0))
                .setEmission(new Color(255, 255, 255)) // White color
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        scene.geometries.add(galaxy);

        // Add randomly positioned and colored stars
        int starCount = 500;
        double starRadius = 5;

        for (int i = 0; i < starCount; i++) {
            double x = Math.random() * 4000 - 2000;
            double y = Math.random() * 4000 - 2000;
            double z = Math.random() * 4000 - 2000;

            Point starCenter = new Point(x, y, z);
            Geometry star = new Sphere(starRadius,starCenter)
                    .setEmission(randomColor()) // Random color
                    .setMaterial(new Material().setkD(0.2).setkS(0.8).setnShininess(50));
            scene.geometries.add(star);
        }

        // Set up lighting and rendering
        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, 0, -5000), new Vector(0, 0, 1))
                .setKl(0.0001).setKq(0.000005));

        camera.setImageWriter(new ImageWriter("spectacular_scene", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    // Helper method to generate a random color
    private Color randomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }


    @Test
    public void reflectiveShapesInSpace() {
        Scene scene = new Scene("Reflective Shapes in Space");
        Camera camera = new Camera(new Point(0, 0, -2000), new Vector(0, 0, 1), new Vector(0, 1, 0))
                .setVPSize(800, 800).setVPDistance(1000);

        // Set background color to black
        scene.setBackground(new Color(0, 0, 0));

        // Create a large reflective sphere representing the main object
        Geometry reflectiveSphere = new Sphere(500,new Point(0, 0, 0))
                .setEmission(new Color(0, 0, 0)) // No self-emission
                .setMaterial(new Material().setkD(0.2).setkS(0.8).setnShininess(100).setkR(0.9));

        scene.geometries.add(reflectiveSphere);

        // Create additional objects with different shapes and materials
        Geometry sphere = new Sphere(100,new Point(500, 0, 0))
                .setEmission(new Color(50, 50, 50))
                .setMaterial(new Material().setkD(0.2).setkS(0.8).setnShininess(50));

        Geometry plane = new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                .setEmission(new Color(0, 0, 0))
                .setMaterial(new Material().setkD(0.2).setkS(0.8).setnShininess(100).setkR(0.5));

        Geometry triangle = new Triangle(new Point(-300, 0, 300), new Point(300, 0, 300), new Point(0, 300, 300))
                .setEmission(new Color(100, 100, 100))
                .setMaterial(new Material().setkD(0.2).setkS(0.8).setnShininess(50));

        scene.geometries.add(sphere, plane, triangle);

        // Set up lighting and rendering
        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(-500, 500, -500), new Vector(1, -1, 1))
                .setKl(0.0001).setKq(0.000005));

        camera.setImageWriter(new ImageWriter("reflective_shapes_in_space", 800, 800))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }




}

