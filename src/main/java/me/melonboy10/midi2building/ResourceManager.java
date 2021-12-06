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

    enum BlockAtlas {SIDE_TORCH(0), BOTTOM_TORCH(1), REPEATER_ONE(2), REPEATER_TWO(3), LEVER (4), REDSTONE_DOT(5), REDSTONE_LINE(6), LAMP(7);
        final int column;
        BlockAtlas(int column) {
            this.column = column;
        }
    }

    public static final int imageScale = 10;
    //public static Font font = null;
    public static Image backgroundImage, widgetsScaled, atlas;

    static {
        try {
            //Font tempFont = Font.createFont(Font.TRUETYPE_FONT, getResource("gui/Minecraftia-Regular.ttf"));
            //GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //genv.registerFont(tempFont);
            //font = tempFont.deriveFont((float) 8 * GeneratorGUI.scale);

            atlas = readImage("src/main/resources/gui/BlockAtlas.png");
            //backgroundImage = new javafx.scene.image.Image(new FileInputStream("src/main/resources/gui/Background.png"));
            backgroundImage = new javafx.scene.image.Image(new FileInputStream("src/main/resources/gui/Background-NoRedstone.png"));
            widgetsScaled = new javafx.scene.image.Image(new FileInputStream("src/main/resources/gui/Widgets.png"));
        } catch (IOException ex) {
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
    static void getImageFromAtlas(ToggleableCanvas canvas, Image image, BlockAtlas atlas) {
        getImageFromAtlas(canvas, image, atlas.column, 0);
    }

    static void getImageFromAtlas(ToggleableCanvas canvas,Image image, BlockAtlas atlas, int row) {
        getImageFromAtlas(canvas, image, atlas.column, row);
    }

    static void getImageFromAtlas(ToggleableCanvas canvas,Image image, BlockAtlas atlas, boolean isOn) {
        if (isOn) {
            getImageFromAtlas(canvas, image, atlas.column, 1);
        } else {
            getImageFromAtlas(canvas, image, atlas.column, 0);
        }
    }

    static void getImageFromAtlas(ToggleableCanvas canvas, Image image, int column, int row) {
        double width = canvas.getPixelWidth();
        double height = canvas.getPixelHeight();

        canvas.getGraphicsContext2D().clearRect(0,0,width * scale,height * scale);
        canvas.getGraphicsContext2D().drawImage(
                image, width * imageScale * column, height * imageScale * row, width * imageScale, height * imageScale, 0, 0, width * scale, height * scale
        );
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
