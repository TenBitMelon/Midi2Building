package me.melonboy10.midi2building;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static me.melonboy10.midi2building.GeneratorApplication.scale;

public class ResourceManager {

    public static final int imageScale = 10;
    //public static Font font = null;
    public static Image backgroundImage, widgetsScaled;

    static {
        try {
            //Font tempFont = Font.createFont(Font.TRUETYPE_FONT, getResource("gui/Minecraftia-Regular.ttf"));
            //GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //genv.registerFont(tempFont);
            //font = tempFont.deriveFont((float) 8 * GeneratorGUI.scale);

            backgroundImage = new javafx.scene.image.Image(new FileInputStream("src/main/resources/gui/Background.png"));
            widgetsScaled = new javafx.scene.image.Image(new FileInputStream("src/main/resources/gui/Widgets.png"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    static Canvas getSubImage(Image image, int x, int y, double width, double height) {
        final Canvas canvas = new Canvas(width, height);
        canvas.getGraphicsContext2D().drawImage(
                image, x, y, width, height, 0, 0, width/imageScale * scale, height/imageScale * scale
        );

        return canvas;
    }

    // Gets an image from the Atlas where all the images are stored. By default images are 16 x 16 Minecraft pixels where 10 pixels is one MC pixel
    // Returns a Canvas sized with the app's scale factors
    static Canvas getImageFromAtlas(Image image, BlockAtlas atlas) {
        return getImageFromAtlas(image, atlas.column, atlas.row, 16, 16, 10);
    }
    static Canvas getImageFromAtlas(Image image, int column, int row) {
        return getImageFromAtlas(image, column, row, 16, 16, 10);
    }

    static Canvas getImageFromAtlas(Image image, int column, int row, int pixelWidth, int pixelHeight) {
        return getImageFromAtlas(image, column, row, pixelWidth, pixelHeight, 10);
    }

    static Canvas getImageFromAtlas(Image image, int column, int row, int pixelWidth, int pixelHeight, int scaleFactor) {
        final Canvas canvas = new Canvas(pixelWidth * scaleFactor, pixelHeight * scaleFactor);
        canvas.getGraphicsContext2D().drawImage(
                image, pixelWidth * scaleFactor * column, pixelHeight * scaleFactor * row, pixelWidth * scaleFactor, pixelHeight * scaleFactor, 0, 0, pixelWidth * scaleFactor/imageScale * scale, pixelHeight * scaleFactor/imageScale * scale
        );

        return canvas;
    }

    // Loading Assets

    enum BlockAtlas {LEVER_ON (3,0), LEVER_OFF(3,1);
        int column;
        int row;
        BlockAtlas(int column, int row) {
            this.column = column;
            this.row = row;
        }
    }

    static ImageView readImageView(String path) throws IOException {
        return new ImageView(new javafx.scene.image.Image(new FileInputStream(path)));
    }

    static Image readImage(String path) throws IOException {
        return new javafx.scene.image.Image(new FileInputStream(path));
    }

    static FileInputStream getResource(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }
}
