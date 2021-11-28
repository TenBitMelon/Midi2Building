package me.melonboy10.midi2building;

import javax.swing.*;
import java.awt.*;

public class ImageComponent extends JComponent {

    private final Image image;
    public ImageComponent(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}