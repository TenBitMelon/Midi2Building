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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;

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

        Image atlas = readImage("src/main/resources/gui/BlockAtlas.png");

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

        //Lever Code
        Canvas lever = getImageFromAtlas(atlas, BlockAtlas.LEVER_ON);

        parent.add(lever,11,6);
        GridPane.setHalignment(lever, HPos.LEFT);
        GridPane.setValignment(lever, VPos.TOP);
        final boolean[] isOn = {false};

//        GridPane.setValignment(lever, VPos.BOTTOM);
//        lever.setRotate(180);

        /**
        Label label = new Label("UWU");

        Button start = new Button("hello world");
        //start.setDefaultButton(true);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lever.setRotate(180);
                if (isOn[0]) {
                    GridPane.setValignment(lever, VPos.BOTTOM);
                    isOn[0] = true;
                } else {
                    GridPane.setValignment(lever, VPos.TOP);
                    isOn[0] = false;
                }
            }
        });
        **/

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("sotp");
    }
}
