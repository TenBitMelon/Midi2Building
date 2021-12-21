package me.melonboy10.midi2building.screenElements;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class RichJLabel extends JLabel {

    private int tracking;

    public RichJLabel(String text, int tracking) {
        super(text);
        this.tracking = tracking;
    }

    private int left_x, left_y, right_x, right_y, padding_left, padding_top, padding_right, padding_bottom;
    private Color left_color, right_color, background_color;

    public void setLeftShadow(int x, int y, Color color) {
        left_x = x;
        left_y = y;
        left_color = color;
    }

    public void setRightShadow(int x, int y, Color color) {
        right_x = x;
        right_y = y;
        right_color = color;
    }

    public void setBackgroundColor(Color color) {
        background_color = color;
    }

    public void setPadding(int left, int top, int right, int bottom) {
        padding_left = left;
        padding_top = top;
        padding_right = right;
        padding_bottom = bottom;
    }

    public Dimension getPreferredSize() {
        String text = getText();
        FontMetrics fm = this.getFontMetrics(getFont());

        String longestString = Arrays.stream(text.split("\\\\n")).sorted(Comparator.comparingInt(String::length)).reduce((first, second) -> second).get();
        int w = fm.stringWidth(longestString);
        w += longestString.length() * tracking;
        w += left_x + right_x + padding_left + padding_right;
        int h = (int) ((fm.getAscent() * 0.9) * text.split("\\\\n").length + padding_top + padding_bottom);
        h += left_y + right_y;

        return new Dimension(w,h);
    }

    public void paintComponent(Graphics g) {
        if (background_color != null) {
            g.setColor(background_color);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        ((Graphics2D)g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        char[] chars = getText().toCharArray();

        FontMetrics fm = this.getFontMetrics(getFont());

        int h = switch (getVerticalAlignment()) {
            case TOP -> fm.getAscent();
            case CENTER -> (getHeight() - fm.getAscent()) / 2 + fm.getAscent();
            case BOTTOM -> getHeight();
            default -> getHeight();
        };
        int x = switch (getHorizontalAlignment()) {
            case LEFT -> 0;
            case CENTER -> getWidth() / 2 - (fm.stringWidth(getText()) + (getText().length() * tracking)) / 2;
            case RIGHT -> getWidth() - fm.stringWidth(getText());
            default -> 0;
        };

        boolean italics = false;
        boolean bold = false;

        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == '\\') {
                switch (chars[i + 1]) {
                    case 'n' -> {
                        h += fm.getAscent() * 0.9;
                        x = 0;
                    }
                    case '9' -> {
                        setForeground(new Color(91, 104, 128));
                        setRightShadow(right_x, right_y, new Color(21, 21, 63));
                    }
                    case 'f' -> {
                        setForeground(new Color(255, 255, 255));
                        setRightShadow(right_x, right_y, new Color(63, 63, 63));
                    }
                    case '7' -> {
                        setForeground(new Color(170, 170, 170));
                        setRightShadow(right_x, right_y, new Color(42, 42, 42));
                    }
                    case '8' -> {
                        setForeground(new Color(85, 85, 85));
                        setRightShadow(right_x, right_y, new Color(21, 21, 21));
                    }
                    case 'c' -> {
                        setForeground(new Color(217,72,73));
                        setRightShadow(right_x, right_y, new Color(53,18,18));
                    }
                    case 'b' -> {
                        setForeground(new Color(86,255,255));
                        setRightShadow(right_x, right_y, new Color(22,56,51));
                    }
                    case 'o' -> italics = true;
                    case 'l' -> bold = true;
                    case 'r' -> {
                        setForeground(new Color(255, 255, 255));
                        setRightShadow(right_x, right_y, new Color(63, 63, 63));
                        italics = false;
                        bold = false;
                    }
                    default -> System.out.println("Unadded color: " + chars[i + 1]);
                }
                i++;
            } else {
                int w = fm.charWidth(ch) + tracking;
                if (italics) {
                    g.setFont(getFont().deriveFont(Font.ITALIC));
                }

                g.setColor(left_color);
                g.drawString("" + ch, x - left_x + padding_left - padding_right, h - left_y + padding_top - padding_bottom);
                if (bold) {
                    g.drawString("" + ch, x - left_x + padding_left - padding_right + tracking, h - left_y + padding_top - padding_bottom);
                }

                g.setColor(right_color);
                g.drawString("" + ch, x + right_x + padding_left - padding_right, h + right_y + padding_top - padding_bottom);
                if (bold) {
                    g.drawString("" + ch, x + right_x + padding_left - padding_right + tracking, h + right_y + padding_top - padding_bottom);
                }

                g.setColor(getForeground());
                g.drawString("" + ch, x + padding_left - padding_right, h + padding_top - padding_bottom);
                if (bold) {
                    g.drawString("" + ch, x + padding_left - padding_right + tracking, h + padding_top - padding_bottom);
                }

                x += w;
            }
        }

        ((Graphics2D)g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);

    } // end paintComponent()
}