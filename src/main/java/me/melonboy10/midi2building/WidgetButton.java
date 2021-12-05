package me.melonboy10.midi2building;

import javax.swing.*;
/**
public class WidgetButton extends JButton{

    ImageIcon plain, hover, click;
    public boolean isEnabled;

    public WidgetButton(int x, int y, int buttonTexture, boolean enabled){
        int buttonSize = GeneratorGUI.scale * ResourceManager.imageScale;
        plain = new ImageIcon(ResourceManager.widgetsScaled.getSubimage(0, buttonTexture * buttonSize, buttonSize, buttonSize));
        hover = new ImageIcon(ResourceManager.widgetsScaled.getSubimage(buttonSize, buttonTexture * buttonSize, buttonSize, buttonSize));
        click = new ImageIcon(ResourceManager.widgetsScaled.getSubimage(buttonSize * 2, buttonTexture * buttonSize, buttonSize, buttonSize));

        isEnabled = enabled;
        if (enabled) {
            setIcon(plain);
        } else {
            setIcon(click);
        }
        setOpaque(true);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setBounds(x * GeneratorGUI.scale, y * GeneratorGUI.scale, buttonSize, buttonSize);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (isEnabled) {
                    setIcon(hover);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (isEnabled) {
                    setIcon(plain);
                }
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (isEnabled) {
                    setIcon(click);
                }
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (isEnabled) {
                    setIcon(plain);
                }
            }
        });
    }

    void changeEnabled(boolean b) {
        isEnabled = b;
        if (isEnabled) {
            setIcon(plain);
        } else {
            setIcon(click);
        }
    }
}**/
