package me.melonboy10.midi2building;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;

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
        stage.setWidth(backgroundImage.getWidth() / imageScale * scale);
        stage.setHeight(backgroundImage.getHeight() / imageScale * scale);
        stage.initStyle(StageStyle.TRANSPARENT);

        GridPane gridPane = new GridPane();
        //gridPane.getRowConstraints().add(new RowConstraints(backgroundImage.getHeight() / imageScale * scale / 28));
        for (int i = 0; i < 14; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(backgroundImage.getWidth() / imageScale * scale / 14));
        }
        for (int i = 0; i < 10; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(backgroundImage.getHeight() / imageScale * scale / 10));
        }
        //Makes window dragable
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        gridPane.setOnMousePressed(event -> {
            xOffset.set(stage.getX() - event.getScreenX());
            yOffset.set(stage.getY() - event.getScreenY());
        });
        gridPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + xOffset.get());
            stage.setY(mouseEvent.getScreenY() + yOffset.get());
        });

        Scene scene = new Scene(gridPane);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        //Background Code
        ImageView background = new ImageView();

        background.setImage(backgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(backgroundImage.getWidth()/ imageScale * scale);

        gridPane.add(background,0,0);
//        gridPane.setGridLinesVisible(true);
        GridPane.setValignment(background, VPos.TOP);

        // Begin Toggle Elements
        ToggleableCanvas lever        = new ToggleableCanvas(BlockAtlas.LEVER         ,false);
        ToggleableCanvas lamp         = new ToggleableCanvas(BlockAtlas.LAMP          ,false);
        ToggleableCanvas sideTorch    = new ToggleableCanvas(BlockAtlas.SIDE_TORCH    ,true);
        ToggleableCanvas dot          = new ToggleableCanvas(BlockAtlas.REDSTONE_DOT  ,true);
        ToggleableCanvas repeater1    = new ToggleableCanvas(BlockAtlas.REPEATER_ONE  ,true);
        ToggleableCanvas repeater2    = new ToggleableCanvas(BlockAtlas.REPEATER_ONE  ,true);
        ToggleableCanvas repeater_two = new ToggleableCanvas(BlockAtlas.REPEATER_TWO  ,false);
        ToggleableCanvas redstoneLine = new ToggleableCanvas(BlockAtlas.REDSTONE_LINE ,false);
        ToggleableCanvas bottomTorch  = new ToggleableCanvas(BlockAtlas.BOTTOM_TORCH  ,true);

        TranslatableBlock pistonArm = new TranslatableBlock(readImage("src/main/resources/gui/PistonHead.png"),true, 48, 16);

        lever.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            lever.toggle();
            int delay = lever.getIsOn() ? 100 : 0;
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0),   actionEvent -> {lamp        .toggle();}),
                new KeyFrame(Duration.millis(100), actionEvent -> {sideTorch   .toggle();}),
                new KeyFrame(Duration.millis(200), actionEvent -> {dot         .toggle();}),
                new KeyFrame(Duration.millis(200), actionEvent -> {pistonArm   .toggle();}),
                new KeyFrame(Duration.millis(200 + delay), actionEvent -> {repeater1   .toggle();}),
                new KeyFrame(Duration.millis(300 + delay), actionEvent -> {repeater2   .toggle();}),
                new KeyFrame(Duration.millis(400 + delay), actionEvent -> {repeater_two.toggle();}),
                new KeyFrame(Duration.millis(500 + delay), actionEvent -> {redstoneLine.toggle();}),
                new KeyFrame(Duration.millis(600 + delay), actionEvent -> {bottomTorch .toggle();}),
                new KeyFrame(Duration.millis(600 + delay), actionEvent -> {repeater_two.toggle();}),
                new KeyFrame(Duration.millis(700 + delay), actionEvent -> {redstoneLine.toggle();}),
                new KeyFrame(Duration.millis(800 + delay),actionEvent -> {bottomTorch .toggle();})
                );
            timeline.play();

//            for (ToggleableCanvas toggleable : ToggleableCanvas.toggleables) {
//                toggleable.toggle();
//            }
        });
        gridPane.add(pistonArm, 7, 8);
        gridPane.add(lever,11,6);
        gridPane.add(lamp,12,6);
        gridPane.add(sideTorch,10,6);
        gridPane.add(dot,10,7);
        gridPane.add(repeater1,6,8);
        gridPane.add(repeater2,5,8);
        gridPane.add(repeater_two,3,8);
        gridPane.add(bottomTorch,2,7);
        gridPane.add(redstoneLine,2,8);

        for (ToggleableCanvas toggleable : ToggleableCanvas.toggleables) {
            GridPane.setHalignment(toggleable, HPos.LEFT);
            GridPane.setValignment(toggleable, VPos.TOP);
        }
        GridPane.setHalignment(pistonArm, HPos.LEFT);
        GridPane.setValignment(pistonArm, VPos.TOP);

        // static images
        ImageView pistonBody = ResourceManager.readImageView("src/main/resources/gui/PistonBody.png");
        pistonBody.setPreserveRatio(true);
        pistonBody.setFitHeight(16 * scale);
        ImageView pistonWool = ResourceManager.readImageView("src/main/resources/gui/YellowWool.png");
        pistonWool.setPreserveRatio(true);
        pistonWool.setFitHeight(16 * scale);

        gridPane.add(pistonBody, 9, 8);
        gridPane.add(pistonWool, 10, 8);

        GridPane.setHalignment(pistonBody, HPos.LEFT);
        GridPane.setValignment(pistonBody, VPos.TOP);
        GridPane.setHalignment(pistonWool, HPos.LEFT);
        GridPane.setValignment(pistonWool, VPos.TOP);

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("sotp");
    }
}
