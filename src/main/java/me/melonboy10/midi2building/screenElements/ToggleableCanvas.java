package me.melonboy10.midi2building.screenElements;

import javafx.scene.canvas.Canvas;
import me.melonboy10.midi2building.ResourceManager;

import java.util.ArrayList;

import static me.melonboy10.midi2building.screenElements.GeneratorApplication.scale;
import static me.melonboy10.midi2building.ResourceManager.*;

/**
 *
 * Toggles a canvas between two images, most commonly on or off for redstone components.
 *
 * @implNote The 2nd state should be the same image file as the 1st state,
 * should be the same size, and should be immediately below the 1st state.
 *
 **/

public class ToggleableCanvas extends Canvas {
    private int pixelWidth;
    private int pixelHeight;

    private final ResourceManager.BlockAtlas blockAtlas;
    private boolean isOn;

    public static ArrayList<ToggleableCanvas> toggleables = new ArrayList<>();

    public ToggleableCanvas(ResourceManager.BlockAtlas blockAtlas, boolean isOn){
        this(blockAtlas,isOn,16,16);
    }

    public ToggleableCanvas(ResourceManager.BlockAtlas blockAtlas, boolean isOn, int pixelWidth, int pixelHeight){
        super(pixelWidth * scale , pixelHeight * scale);
        this.blockAtlas = blockAtlas;
        this.isOn = isOn;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
//        this.getGraphicsContext2D().fillRect(0,0,getWidth(),getHeight());
        getImageFromAtlas(this,atlas, blockAtlas,isOn);
        toggleables.add(this);
        toggle();
    }

    public void toggle(){
        if (isOn) {
            turnOff();
        } else {
            turnOn();
        }
    }

    public void turnOn(){
        isOn = true;
        getImageFromAtlas(this,atlas, blockAtlas,isOn);
    }

    public void turnOff(){
        isOn = false;
        getImageFromAtlas(this,atlas, blockAtlas,isOn);
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

    public BlockAtlas getBlockAtlas() {
        return blockAtlas;
    }

}
