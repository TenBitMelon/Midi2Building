package me.melonboy10.midi2building.screenElements;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import me.melonboy10.midi2building.conversion.Midi2BlockConversion;
import me.melonboy10.midi2building.util.ResourceManager;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static me.melonboy10.midi2building.util.ResourceManager.*;

public class GeneratorApplication extends Application {

    public static final int scale = 4;
    public static final Midi2BlockConversion conversion = new Midi2BlockConversion();

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

        // Sets the gridPane so every tile on the grid matches to one Minecraft Block
        // (Makes the gridPane not a gridPain)
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: null;"); // Makes it transparent
//        gridPane.setGridLinesVisible(true);  // [DEBUG] makes the gridPane visible
        for (int i = 0; i < 14; i++) { //S  ets the columns to the size of the MC blocks in the image.
                                       // THIS ONLY WORKS WITH A 14 BLOCK WIDE IMAGE
            gridPane.getColumnConstraints().add(new ColumnConstraints(backgroundImage.getWidth() / imageScale * scale / 14));
        }
        for (int i = 0; i < 10; i++) { //Sets the rows to the size of the MC blocks in the image.
                                       // THIS ONLY WORKS WITH A 10 BLOCK TALL IMAGE
            gridPane.getRowConstraints().add(new RowConstraints(backgroundImage.getHeight() / imageScale * scale / 10));
        }
        // Makes window dragable
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

        // Initialises the scene
        Scene scene = new Scene(gridPane);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        // Background Code
        ImageView background = new ImageView();

        background.setImage(backgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(backgroundImage.getWidth()/ imageScale * scale);

        gridPane.add(background,0,0);
        GridPane.setValignment(background, VPos.TOP);


        /* Toggle Elements */
        // Initialises toggleable elements
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

        // Makes the lever grow when moused over
        lever.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            getImageFromAtlas(lever,mousedLever,BlockAtlas.ZERO,lever.getIsOn());
        });
        lever.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            getImageFromAtlas(lever,atlas,lever.getBlockAtlas(),lever.getIsOn());
        });

        // Adds animations when the lever is clicked
        lever.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            lever.toggle();
            int delay = lever.getIsOn() ? 100 : 0; // Yes, we made the redstone turn off faster than it turns on to match the game.
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(0),   actionEvent -> lamp        .toggle()),
                new KeyFrame(Duration.millis(100), actionEvent -> sideTorch   .toggle()),
                new KeyFrame(Duration.millis(200), actionEvent -> dot         .toggle()),
                new KeyFrame(Duration.millis(200), actionEvent -> pistonArm   .toggle()),
                new KeyFrame(Duration.millis(200 + delay), actionEvent -> repeater1   .toggle()),
                new KeyFrame(Duration.millis(300 + delay), actionEvent -> repeater2   .toggle()),
                new KeyFrame(Duration.millis(400 + delay), actionEvent -> repeater_two.toggle()),
                new KeyFrame(Duration.millis(500 + delay), actionEvent -> redstoneLine.toggle()),
                new KeyFrame(Duration.millis(600 + delay), actionEvent -> bottomTorch .toggle()),
                new KeyFrame(Duration.millis(600 + delay), actionEvent -> repeater_two.toggle()),
                new KeyFrame(Duration.millis(700 + delay), actionEvent -> redstoneLine.toggle()),
                new KeyFrame(Duration.millis(800 + delay),actionEvent -> bottomTorch .toggle()));
            timeline.play();
        });

        // Adds the toggleables to the gridPain and therefore the scene which makes each of them visible
        gridPane.add(pistonArm,   7, 8);
        gridPane.add(lever,       11,6);
        gridPane.add(lamp,        12,6);
        gridPane.add(sideTorch,   10,6);
        gridPane.add(dot,         10,7);
        gridPane.add(repeater1,   6,8);
        gridPane.add(repeater2,   5,8);
        gridPane.add(repeater_two,3,8);
        gridPane.add(bottomTorch, 2,7);
        gridPane.add(redstoneLine,2,8);

        // Aligns toggleables to the top left of each square in the gridPane
        for (ToggleableCanvas toggleable : ToggleableCanvas.toggleables) {
            GridPane.setHalignment(toggleable, HPos.LEFT);
            GridPane.setValignment(toggleable, VPos.TOP);
        }
        GridPane.setHalignment(pistonArm, HPos.LEFT);
        GridPane.setValignment(pistonArm, VPos.TOP);

        // Adds static images to hide the piston arm when retracted
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


        /* End redstone */


        /* Loading Files*/
        DropShadow shadow = new DropShadow();
        shadow.setRadius(2.0);
        shadow.setOffsetX(4.0);
        shadow.setOffsetY(4.0);

        // Midi Stuff
        Text midiText = new Text("Please Select a Midi File");
        midiText.setFont(minecraftia);
        midiText.setFill(Color.rgb(255, 170, 0)); // Minecraft's "Gold"

        gridPane.add(midiText,4,2);
        GridPane.setMargin(midiText, new Insets(0,0,-12*scale, 3*scale)); // Moves the text to the right place TODO: center this
        midiText.setEffect(shadow);

        Canvas noteBlock = new Canvas(16*scale,16*scale);
        noteBlock.setOpacity(0.15);
        noteBlock.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        noteBlock.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            noteBlock.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        noteBlock.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            noteBlock.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        noteBlock.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Midi File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Midi Files", "*.mid", "*.midi"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                try {
                    conversion.setMidiFile(selectedFile);
                    midiText.setText("Midi: " + selectedFile.getName());
                } catch (InvalidMidiDataException | IOException e) {
                    midiText.setText("Invalid Midi File");
                    e.printStackTrace();
                }
            }
        });
        gridPane.add(noteBlock,3,3);


        // Structure Block Stuff
        Text structureText = new Text("Please Select a Structure File");
        structureText.setFont(minecraftia);
        structureText.setFill(Color.rgb(85, 255, 85)); // Minecraft's "Light Green"

        gridPane.add(structureText,5,3);
        GridPane.setMargin(structureText, new Insets(0,0,-12*scale, 3*scale)); // Moves the text to the right place TODO: center this
        structureText.setEffect(shadow);

        Canvas structureBlock = new Canvas(16*scale,16*scale);
        structureBlock.setOpacity(0.15);
        structureBlock.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        structureBlock.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            structureBlock.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        structureBlock.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            structureBlock.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        structureBlock.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Structure File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("NBT Files", "*.nbt"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                structureText.setText("Structure: " + selectedFile.getName());
            }
        });
        gridPane.add(structureBlock,11,3);



        /* Crafting table & Selecting Blocks */
        Canvas craftingTable = new Canvas(16*scale,16*scale);
        craftingTable.setOpacity(0.15);
        craftingTable.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        craftingTable.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            craftingTable.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        craftingTable.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            craftingTable.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        craftingTable.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            SelectBlocks selectBlocksWindow = new SelectBlocks();
            selectBlocksWindow.initOwner(stage);
            selectBlocksWindow.show();
        });

        gridPane.add(craftingTable,12,3);

        // Makes the stage visible
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("sotp");
    }
}
