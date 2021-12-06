package me.melonboy10.midi2building;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import static me.melonboy10.midi2building.ResourceManager.*;

public class GeneratorApplication extends Application {

    public static final int scale = 4;


    @Override
    public void init() throws Exception {
        System.out.println("before");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Midi2Building Generator");
        stage.setWidth(backgroundImage.getWidth() / ResourceManager.imageScale * scale);
        stage.setHeight(backgroundImage.getHeight() / ResourceManager.imageScale * scale);
        stage.initStyle(StageStyle.TRANSPARENT);



        GridPane parent = new GridPane();
        //parent.getRowConstraints().add(new RowConstraints(backgroundImage.getHeight() / ResourceManager.imageScale * scale / 28));
        for (int i = 0; i < 14; i++) {
            parent.getColumnConstraints().add(new ColumnConstraints(backgroundImage.getWidth() / ResourceManager.imageScale * scale / 14));
        }
        for (int i = 0; i < 10; i++) {
            parent.getRowConstraints().add(new RowConstraints(backgroundImage.getHeight() / ResourceManager.imageScale * scale / 10));
        }
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);


        //Background Code
        ImageView background = new ImageView();

        background.setImage(backgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(backgroundImage.getWidth()/ ResourceManager.imageScale * scale);

        parent.add(background,0,0);
        parent.setGridLinesVisible(true);
        GridPane.setValignment(background, VPos.TOP);


        ToggleableCanvas lever = new ToggleableCanvas(BlockAtlas.LEVER,false);
        lever.addEventFilter(MouseEvent.MOUSE_CLICKED,leverClicked);

        ToggleableCanvas lamp = new ToggleableCanvas(BlockAtlas.LAMP,false);
        ToggleableCanvas sideTorch = new ToggleableCanvas(BlockAtlas.SIDE_TORCH,true);
        ToggleableCanvas dot = new ToggleableCanvas(BlockAtlas.REDSTONE_DOT,true);
        ToggleableCanvas repeater1 = new ToggleableCanvas(BlockAtlas.REPEATER_ONE,true);
        ToggleableCanvas repeater2 = new ToggleableCanvas(BlockAtlas.REPEATER_ONE,true);
        ToggleableCanvas repeaterTwoTick = new ToggleableCanvas(BlockAtlas.REPEATER_TWO,true);
        ToggleableCanvas redstoneLine = new ToggleableCanvas(BlockAtlas.REDSTONE_LINE,false);
        ToggleableCanvas bottomTorch = new ToggleableCanvas(BlockAtlas.BOTTOM_TORCH,false);

        parent.add(lever,11,6);

        parent.add(lamp,12,6);
        parent.add(sideTorch,10,6);
        parent.add(dot,10,7);
        parent.add(repeater1,9,8);
        parent.add(repeater2,5,8);
        parent.add(repeaterTwoTick,3,8);
        parent.add(redstoneLine,2,8);
        parent.add(bottomTorch,2,7);

        for (ToggleableCanvas toggleable : ToggleableCanvas.toggleables) {
            GridPane.setHalignment(toggleable, HPos.LEFT);
            GridPane.setValignment(toggleable, VPos.TOP);
        }

        stage.show();
    }

    EventHandler<MouseEvent> leverClicked = mouseEvent -> {
        for (ToggleableCanvas toggleable : ToggleableCanvas.toggleables) {
            toggleable.toggle();
            if (toggleable.getBlockAtlas() == BlockAtlas.REDSTONE_LINE){
                toggleable.toggle();
            }
        }
    };

    @Override
    public void stop() throws Exception {
        System.out.println("sotp");
    }
}
