package me.melonboy10.midi2building;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;
//import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
