package me.melonboy10.midi2building.screenElements;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.melonboy10.midi2building.util.SoundAtlas;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static me.melonboy10.midi2building.screenElements.GeneratorApplication.conversion;
import static me.melonboy10.midi2building.screenElements.GeneratorApplication.scale;
import static me.melonboy10.midi2building.util.ResourceManager.*;

public class SelectBlocks extends Stage {
    private int currentOctave;
    private final List<Node> textNodes = new ArrayList<>();
    private final GridPane gridPane;

    private final Canvas upTwoOctaves;
    private final Canvas upOneOctave;
    private final Canvas goToC4;
    private final Canvas downOneOctave;
    private final Canvas downTwoOctaves;

    public SelectBlocks() {
        super();

        currentOctave = 4;

        // Background Code
        ImageView background = new ImageView();

        background.setImage(blockSelectionbackgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(blockSelectionbackgroundImage.getWidth()/ imageScale * scale);

        gridPane = new GridPane();
        gridPane.add(background,0,0);
        GridPane.setValignment(background, VPos.TOP);

        gridPane.getColumnConstraints().add(new ColumnConstraints(scale * 7));
        for (int i = 0; i < 9; i++) { //Sets the columns to the size of the slots in the chest image.
            // THIS ONLY WORKS WITH CHEST SLOTS
            gridPane.getColumnConstraints().add(new ColumnConstraints( scale *  18));
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(scale * 7));

        gridPane.getRowConstraints().add(new RowConstraints( scale * 7));
        gridPane.getRowConstraints().add(new RowConstraints(scale * 10));
        for (int i = 0; i < 7; i++) { //Sets the rows to the size of the slots in the chest image.
            // THIS ONLY WORKS WITH CHEST SLOTS
            gridPane.getRowConstraints().add(new RowConstraints( scale * 18));
        }
        gridPane.getRowConstraints().add(new RowConstraints( scale * 7));

        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);

        //gridPane.setGridLinesVisible(true);  // [DEBUG] makes the gridPane visible


        // Makes window dragable
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        gridPane.setOnMousePressed(event -> {
            xOffset.set(this.getX() - event.getScreenX());
            yOffset.set(this.getY() - event.getScreenY());
        });
        gridPane.setOnMouseDragged(mouseEvent -> {
            this.setX(mouseEvent.getScreenX() + xOffset.get());
            this.setY(mouseEvent.getScreenY() + yOffset.get());
        });


        // Initialises the scene
        Scene scene = new Scene(gridPane);
        scene.setFill(Color.TRANSPARENT);
        this.setScene(scene);

        // Add close button
        Canvas close = new Canvas(18*scale,18*scale);
        close.setOpacity(0.15);
        close.getGraphicsContext2D().setFill(Color.rgb(255,0,0));

        close.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            close.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        close.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            close.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        close.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            this.close();
        });
        gridPane.add(close,8,8);


        // Add change octave buttons
        upTwoOctaves = new Canvas(18*scale,18*scale);
        upTwoOctaves.setOpacity(0.15);
        upTwoOctaves.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        upTwoOctaves.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            upTwoOctaves.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        upTwoOctaves.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            upTwoOctaves.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        upTwoOctaves.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            currentOctave += 2;
            if(conversion.getIsInstantiated())
                changeOctave(currentOctave);
        });
        gridPane.add(upTwoOctaves,8,3);


        upOneOctave = new Canvas(18*scale,18*scale);
        upOneOctave.setOpacity(0.15);
        upOneOctave.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        upOneOctave.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            upOneOctave.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        upOneOctave.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            upOneOctave.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        upOneOctave.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            currentOctave++;
            if(conversion.getIsInstantiated())
                changeOctave(currentOctave);
        });
        gridPane.add(upOneOctave,8,4);


        goToC4 = new Canvas(18*scale,18*scale);
        goToC4.setOpacity(0.15);
        goToC4.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        goToC4.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            goToC4.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        goToC4.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            goToC4.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        goToC4.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            currentOctave = 4;
            if(conversion.getIsInstantiated())
                changeOctave(currentOctave);
        });
        gridPane.add(goToC4,8,5);


        downOneOctave = new Canvas(18*scale,18*scale);
        downOneOctave.setOpacity(0.15);
        downOneOctave.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        downOneOctave.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            downOneOctave.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        downOneOctave.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            downOneOctave.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        downOneOctave.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            currentOctave--;
            if(conversion.getIsInstantiated())
                changeOctave(currentOctave);
        });
        gridPane.add(downOneOctave,8,6);


        downTwoOctaves = new Canvas(18*scale,18*scale);
        downTwoOctaves.setOpacity(0.15);
        downTwoOctaves.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

        downTwoOctaves.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            downTwoOctaves.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        });
        downTwoOctaves.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            downTwoOctaves.getGraphicsContext2D().clearRect(0, 0, 100, 100);
        });
        downTwoOctaves.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            currentOctave -= 2;
            if(conversion.getIsInstantiated())
                changeOctave(currentOctave);
        });
        gridPane.add(downTwoOctaves,8,7);

        if(conversion.getIsInstantiated())
            changeOctave(currentOctave);
    }

    private void changeOctave(int octave) {
        int column = 1;
        int row = 6;

        for (Node textNode : textNodes) {
            gridPane.getChildren().remove(textNode);
        }

        for (int i = 0; i < 12; i++) {
            String noteLetter = NOTE_NAMES.get(i % 12);

            // The octave of the current note rather than the user-selected octave.
            int realOctave = i / 12 + octave;
            String note = octave + noteLetter;

            // The pitch of the note Ex: C4
            Text noteName = new Text(noteLetter + realOctave);
            noteName.setFont(minecraftiaChest);
            noteName.setFill(Color.rgb(255, 255, 255)); // Pure White
            gridPane.add(noteName, column, row);
            textNodes.add(noteName);

            GridPane.setValignment(noteName,VPos.BOTTOM);
            GridPane.setHalignment(noteName,HPos.RIGHT);
            GridPane.setMargin(noteName, new Insets(0,0,-3*scale,0)); // Moves the text to the right place
            noteName.setEffect(textShadow);

            // The Block sound associated with the pitch
            Canvas soundIcon = new Canvas(18*scale,18*scale);
            getImageFromAtlas(soundIcon, soundAtlas, conversion.getMidiFile().getBlockSound().getOrDefault(note, SoundAtlas.NULL));
            gridPane.add(soundIcon,column,row + 1);

            // The button to change the block sound - opens SelectSound.java
            Canvas changeSound = new Canvas(18*scale,18*scale);
            changeSound.setOpacity(0.15);
            changeSound.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

            changeSound.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                changeSound.getGraphicsContext2D().fillRect(0, 0, 100, 100);
            });
            changeSound.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
                changeSound.getGraphicsContext2D().clearRect(0, 0, 100, 100);
            });
            changeSound.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                SelectSound selectSoundWindow = new SelectSound(note, soundIcon);
                selectSoundWindow.initOwner(this);
                selectSoundWindow.show();
            });
            gridPane.add(changeSound,column,row+1);

            // The number of times the note is played
            Text noteNumber = conversion.getMidiFile().getBlockNumber().containsKey(note) ? new Text(String.valueOf(conversion.getMidiFile().getBlockNumber().get(note))) : new Text("0");
            noteNumber.setFont(minecraftiaSign);
            noteNumber.setFill(Color.rgb(0, 0, 0)); // Pure Black
            gridPane.add(noteNumber, column, row+2);
            textNodes.add(noteNumber);

            GridPane.setValignment(noteNumber,VPos.TOP);
            GridPane.setHalignment(noteNumber,HPos.CENTER);
            GridPane.setMargin(noteNumber, new Insets(3*scale,0,0,0)); // Moves the text to the right place


            if (column >= 6) {
                column = 1;
                row = 2;
            } else {
                column++;
            }

        }

        // Adding the change octave text
        int realOctave = octave + 2;
        for (int i = 1; i <= 5; i++) {
            Text noteName = i == 3 ? new Text("C4") : new Text("C" + String.valueOf(realOctave));

            noteName.setFont(minecraftiaChest);
            noteName.setFill(Color.rgb(255, 255, 255)); // Pure White
            gridPane.add(noteName, 8, i + 2);
            textNodes.add(noteName);

            GridPane.setValignment(noteName,VPos.BOTTOM);
            GridPane.setHalignment(noteName,HPos.RIGHT);
            GridPane.setMargin(noteName, new Insets(0,0,-3*scale,0)); // Moves the text to the right place
            noteName.setEffect(textShadow);

            realOctave--;
        }

        // Bringing buttons to the front so they can be clicked
        upTwoOctaves.toFront();
        upOneOctave.toFront();
        goToC4.toFront();
        downOneOctave.toFront();
        downTwoOctaves.toFront();

    }

    private static void getImageFromAtlas(Canvas canvas, Image image, SoundAtlas sound) {
        double width = 18.0;
        double height = 18.0;

        int row = sound.textureID / 9;
        int column = sound.textureID % 9;

        canvas.getGraphicsContext2D().clearRect(0,0,width * scale,height * scale);
        canvas.getGraphicsContext2D().drawImage(
                image, width * imageScale * column, height * imageScale * row, width * imageScale, height * imageScale, 0, 0, width * scale, height * scale
        );
    }
}
