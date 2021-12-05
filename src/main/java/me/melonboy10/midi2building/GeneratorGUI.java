package me.melonboy10.midi2building;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
/**
public class GeneratorGUI extends JWindow {

    public static final int scale = 4;

    public GeneratorGUI() throws IOException {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0 ,0));

        setSize(new Dimension(ResourceManager.backgroundImage.getWidth() / ResourceManager.imageScale * scale, ResourceManager.backgroundImage.getHeight() / ResourceManager.imageScale * scale));
        setPreferredSize(new Dimension(ResourceManager.backgroundImage.getWidth() / ResourceManager.imageScale * scale, ResourceManager.backgroundImage.getHeight() / ResourceManager.imageScale * scale));
        setContentPane(new ImageComponent(ResourceManager.backgroundImage.getScaledInstance(ResourceManager.backgroundImage.getWidth() / ResourceManager.imageScale * scale, ResourceManager.backgroundImage.getHeight() / ResourceManager.imageScale * scale, Image.SCALE_REPLICATE)));

        FrameDragListener frameDragListener = new FrameDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);

        WidgetButton closeButton = new WidgetButton(180, 117, 0, true);
        closeButton.addActionListener (e -> System.exit(0));

        JLabel warning = new JLabel("");
        warning.setBounds(165 * scale, 120 * scale, 3 * scale, 12 * scale);
        warning.setIcon(new ImageIcon(ResourceManager.widgetsScaled.getSubimage(54 * scale, 30 * scale, 3 * scale, 12 * scale)));
        FollowTip warningLabel = new FollowTip("\\cThis is currently experimental.\\nMake backups of your resourcepack before using.", this);
        warning.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                warningLabel.activate();
            }

            public void mouseExited(MouseEvent evt) {
                warningLabel.deactivate();
            }
        });

        WidgetButton openFileButton = new WidgetButton(21, 18, 4, true);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Resourcepack Folder");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        openFileButton.addActionListener(e -> {
            int value = fileChooser.showOpenDialog(rootPane);

            if (value == JFileChooser.APPROVE_OPTION) {

            }
        });



        add(warning);
        add(closeButton);
        add(openFileButton);
        pack();
        setVisible(true);
    }
}
**/