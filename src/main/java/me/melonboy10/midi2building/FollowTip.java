package me.melonboy10.midi2building;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

public class FollowTip {
    private JWindow tip;
    private RichJLabel tipLabel;

    private final AWTEventListener mouseHandler = e -> {
        Window window = tip.getOwner();
        MouseEvent event = null;

        int id = e.getID();
        switch (id) {
            case MouseEvent.MOUSE_ENTERED, MouseEvent.MOUSE_MOVED, MouseEvent.MOUSE_DRAGGED -> {
                event = (MouseEvent) e;
                if (window.isAncestorOf(event.getComponent())) {
                    Point loc = event.getLocationOnScreen();
                    tip.setLocation(loc.x + 10, loc.y + 10);
                    tip.setVisible(true);
                }
            }
            case MouseEvent.MOUSE_EXITED -> {
                event = (MouseEvent) e;
                Point p = SwingUtilities.convertPoint(
                        event.getComponent(), event.getPoint(), window);
                if (!window.contains(p)) {
                    tip.setVisible(false);
                }
            }
        }
    };

    public FollowTip(String text, Window window) {
        tipLabel = new RichJLabel(text, 2);
        tipLabel.setOpaque(true);
        tipLabel.setSize(tipLabel.getPreferredSize());
        tipLabel.setForeground(Color.CYAN);
        tipLabel.setPadding(5, 5,0, 0);
        tipLabel.setRightShadow(2, 2, Color.WHITE);
        //tipLabel.setFont(ResourceManager.font.deriveFont(18f));
        tipLabel.setBackgroundColor(new Color(23,8,23));
        tipLabel.setBorder(new LineBorder(new Color(32,1,76), 2));
        tipLabel.setVerticalAlignment(SwingConstants.TOP);

        tip = new JWindow(window);
        tip.setType(Window.Type.POPUP);
        tip.setFocusableWindowState(false);
        tip.getContentPane().add(tipLabel);
        tip.setSize(tipLabel.getPreferredSize());
        tip.pack();
    }

    public void setText(String text) {
        tipLabel.setText(text);
    }

    public void activate() {
        Window window = tip.getOwner();
        window.getToolkit().addAWTEventListener(mouseHandler,
                AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);

        Point p = window.getMousePosition();
        if (p != null) {
            SwingUtilities.convertPointToScreen(p, window);
            tip.setLocation(p.x + 10, p.y + 10);
            tip.setVisible(true);
        }
    }

    public void deactivate() {
        Window window = tip.getOwner();
        window.getToolkit().removeAWTEventListener(mouseHandler);

        tip.setVisible(false);
    }

    public JWindow getWindow() {
        return tip;
    }

    public RichJLabel getLabel() {
        return tipLabel;
    }
}