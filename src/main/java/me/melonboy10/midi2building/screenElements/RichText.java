package me.melonboy10.midi2building.screenElements;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import me.melonboy10.midi2building.util.ResourceManager;

public class RichText extends Text {

    DropShadow dropShadow;
    enum MinecraftColors {}

    public RichText(String text) {
        super(text);
        this.setFont(ResourceManager.minecraftia);
        dropShadow = new DropShadow(0, ResourceManager.imageScale, ResourceManager.imageScale, Color.CORAL);


    }

    public RichText(double x, double y, String text) {
        super(x, y, text);
    }



}
