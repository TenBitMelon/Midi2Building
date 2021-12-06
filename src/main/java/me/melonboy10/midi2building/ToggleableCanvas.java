package me.melonboy10.midi2building;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;

import static me.melonboy10.midi2building.GeneratorApplication.scale;
import static me.melonboy10.midi2building.ResourceManager.*;

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
        System.out.println("isOn = " + isOn);
        if (isOn) {
            turnOff();
        } else {
            turnOn();
        }
    }

    public void turnOn(){
        System.out.println("WOAH, IT'S ON NOW!");
        getImageFromAtlas(this,atlas, blockAtlas,isOn);
        isOn = true;
    }

    public void turnOff(){
        System.out.println("OH NO, IT'S OFF NOW!");
        getImageFromAtlas(this,atlas, blockAtlas,isOn);
        isOn = false;
    }

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
