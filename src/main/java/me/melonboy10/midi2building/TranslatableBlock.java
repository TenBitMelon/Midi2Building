package me.melonboy10.midi2building;

import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static me.melonboy10.midi2building.GeneratorApplication.scale;
import static me.melonboy10.midi2building.ResourceManager.*;

/**
 *
 * A class for animated, moving blocks. Translates an asset back and forth horizontally
 * by one block. Currently used for the piston.
 * Default block is 16 x 16 Minecraft pixels.
 *
 * @implNote the "On" state currently moves the asset to the right. The "Off" state moves it to the left.
 *
 **/
public class TranslatableBlock extends Canvas {
    private boolean isOn;
    private final int pixelWidth;
    private final int pixelHeight;

    public TranslatableBlock(Image asset, boolean isOn){
        this(asset,isOn,16,16);
    }

    public TranslatableBlock(Image asset, boolean isOn, int pixelWidth, int pixelHeight){
        super(pixelWidth * scale , pixelHeight * scale);
        this.isOn = isOn;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        this.getGraphicsContext2D().clearRect(0,0,pixelWidth * scale,pixelHeight * scale);
        this.getGraphicsContext2D().drawImage(
                asset, 0, 0, pixelWidth * imageScale, pixelHeight * imageScale, 0, 0, pixelWidth * scale, pixelHeight * scale
        );
    }

    public void toggle(){
        if (isOn) {
            turnOff();
        } else {
            turnOn();
        }
    }

    public void turnOn(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), this);
        translateTransition.setFromX(16 * scale);
        translateTransition.setToX(0);
        translateTransition.play();
        isOn = true;
    }

    public void turnOff (){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), this);
        translateTransition.setFromX(0);
        translateTransition.setToX(16 * scale);
        translateTransition.play();
        isOn = false;
    }

    // Getters
    public boolean getIsOn() {
        return isOn;
    }

    public int getPixelWidth() {
        return pixelWidth;
    }

    public int getPixelHeight() {
        return pixelHeight;
    }

}
