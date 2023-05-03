package renderer;
import org.junit.jupiter.api.Test;
import primitives.Color;

/** Testing ImageWriter*/
class ImageWriterTests {
    @Test
    void testImageWithGrid() {
        int width = 800;
        int height = 500;
        ImageWriter imageWriter = new ImageWriter("imageWithGrid", width, height);

        // Set the background color and grid color
        Color bgColor = new Color(0, 0, 255); // light blue
        Color gridColor = new Color(255, 0, 0); // glowing green

        // Calculate the dimensions of each grid square
        int squareWidth = width / 16;
        int squareHeight = height / 10;

        // Draw the grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Check if this pixel should be a grid line
                boolean isGridLine = (i % squareWidth == 0) || (j % squareHeight == 0);

                // Set the pixel color accordingly
                Color pixelColor = isGridLine ? gridColor : bgColor;

                // Write the pixel to the image
                imageWriter.writePixel(i, j, pixelColor);
            }
        }

        // Write the image to file
        imageWriter.writeToImage();
    }

}