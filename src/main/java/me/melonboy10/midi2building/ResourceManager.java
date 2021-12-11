package me.melonboy10.midi2building;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static me.melonboy10.midi2building.GeneratorApplication.scale;

public class ResourceManager {

    enum BlockAtlas {
        NULL         (-1),
        ZERO         (0),
        SIDE_TORCH   (0),
        BOTTOM_TORCH (1),
        REPEATER_ONE (2),
        REPEATER_TWO (3),
        LEVER        (4),
        REDSTONE_DOT (5),
        REDSTONE_LINE(6),
        LAMP         (7);

        final int column;
        BlockAtlas(int column) {
            this.column = column;
        }
    }
    public static final int imageScale = 10;
    public static Image backgroundImage, widgetsScaled, atlas, mousedLever;

    static {
        try {
            //ToDo: Figure out how fonts work
            //Font tempFont = Font.createFont(Font.TRUETYPE_FONT, getResource("gui/Minecraftia-Regular.ttf"));
            //GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //genv.registerFont(tempFont);
            //font = tempFont.deriveFont((float) 8 * GeneratorGUI.scale);

            // Initialising and loading assets
            atlas = readImage("src/main/resources/gui/BlockAtlas.png");
            mousedLever = readImage("src/main/resources/gui/MousedLever.png");
            backgroundImage = new javafx.scene.image.Image(new FileInputStream("src/main/resources/gui/Background-NoRedstone.png"));
            widgetsScaled = new javafx.scene.image.Image(new FileInputStream("src/main/resources/gui/Widgets.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static void setCanvas(Canvas canvas, Image image) {
        canvas.getGraphicsContext2D().clearRect(0,0,image.getWidth() * scale,image.getHeight() * scale);
        canvas.getGraphicsContext2D().drawImage(
                image, 0, 0, image.getWidth() * imageScale, image.getHeight() * imageScale, 0, 0, image.getWidth() * scale, image.getHeight() * scale
        );
    }

    /**
     * Gets an image from the Atlas where all the images are stored.
     * Returns a Canvas sized with the app's scale factors
     * @implNote By default, images are 16 x 16 Minecraft pixels where 10 pixels is one MC pixel
     **/
    static void getImageFromAtlas(ToggleableCanvas canvas, Image image, BlockAtlas atlas) {
        getImageFromAtlas(canvas, image, atlas.column, 0);
    }

    static void getImageFromAtlas(ToggleableCanvas canvas,Image image, BlockAtlas atlas, int row) {
        getImageFromAtlas(canvas, image, atlas.column, row);
    }

    static void getImageFromAtlas(ToggleableCanvas canvas,Image image, BlockAtlas atlas, boolean isOn) {
        if (isOn) {
            getImageFromAtlas(canvas, image, atlas.column, 0);
        } else {
            getImageFromAtlas(canvas, image, atlas.column, 1);
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


    // Helper functions to load assets
    // ToDo: Add descriptors and what each function accepts and returns
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
