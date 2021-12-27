package me.melonboy10.midi2building.screenElements;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
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
import me.melonboy10.midi2building.util.ResourceManager;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static me.melonboy10.midi2building.screenElements.GeneratorApplication.conversion;
import static me.melonboy10.midi2building.screenElements.GeneratorApplication.scale;
import static me.melonboy10.midi2building.util.ResourceManager.*;

public class SelectSound extends Stage {
    private static final int IMAGES_PER_ROW = 8;

    private final GridPane gridPane;
    private final String note;
    Canvas iconToChange;

    public SelectSound(String note, Canvas iconToChange) {
        super();

        this.note = note;
        this.iconToChange = iconToChange;

        // Background Code
        ImageView background = new ImageView();

        background.setImage(soundSelectionbackgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(soundSelectionbackgroundImage.getWidth()/ imageScale * scale);

        gridPane = new GridPane();
        gridPane.add(background,0,0);
        GridPane.setValignment(background, VPos.TOP);

        gridPane.getColumnConstraints().add(new ColumnConstraints(scale * 8));
        for (int i = 0; i < 9; i++) { //Sets the columns to the size of the slots in the chest image.
            // THIS ONLY WORKS WITH CHEST SLOTS
            gridPane.getColumnConstraints().add(new ColumnConstraints( scale *  18));
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(scale * 4));
        gridPane.getColumnConstraints().add(new ColumnConstraints(scale * 14));
        gridPane.getColumnConstraints().add(new ColumnConstraints(scale * 7));

        gridPane.getRowConstraints().add(new RowConstraints( scale * 7));
        gridPane.getRowConstraints().add(new RowConstraints(scale * 10));
        for (int i = 0; i < 5; i++) { //Sets the rows to the size of the slots in the chest image.
            // THIS ONLY WORKS WITH CHEST SLOTS
            gridPane.getRowConstraints().add(new RowConstraints( scale * 18));
        }
        gridPane.getRowConstraints().add(new RowConstraints( scale * 7));

        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);

//        gridPane.setGridLinesVisible(true);  // [DEBUG] makes the gridPane visible


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

        // Adds the window text
        String noteUserFriendly = ""; // The letter-octave form of the note rather than the octave-letter form it's stored in
        for (int i = 0; i < note.length(); i++) {
            char currentChar = note.charAt(i);
            if (NOTE_NAMES.contains(String.valueOf(currentChar))) {
                noteUserFriendly = note.substring(i);
                noteUserFriendly += note.substring(0,i);
            }
        }

        Text title = new Text("Select Sound for " + noteUserFriendly);
        title.setFont(minecraftiaChest);
        title.setFill(Color.rgb(63, 63, 63)); // Minecraft's "Gray"
        gridPane.add(title,0,0);
        GridPane.setMargin(title, new Insets(0,0,-13*scale, 7*scale)); // Moves the text to the right place TODO: center this

        // Add close button
        Canvas close = new Canvas(7*scale,7*scale);
        close.setOpacity(1);
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
        gridPane.add(close,12,0);


        // Code to add the blocks
        int row = 2;
        int column = 1;
        for (BlockSounds sound : BlockSounds.values()) {
            Canvas soundIcon = new Canvas(18*scale,18*scale);
            getImageFromAtlas(soundIcon, soundAtlas, sound);
            gridPane.add(soundIcon,column,row);

            Canvas soundSelector = new Canvas(18*scale,18*scale);
            soundSelector.setOpacity(0.15);
            soundSelector.getGraphicsContext2D().setFill(Color.rgb(255,255,255));

            soundSelector.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                soundSelector.getGraphicsContext2D().fillRect(0, 0, 100, 100);
            });
            soundSelector.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
                soundSelector.getGraphicsContext2D().clearRect(0, 0, 100, 100);
            });
            soundSelector.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                conversion.getMidiFile().getBlockSound().put(note,sound);
                getImageFromAtlas(iconToChange, soundAtlas, sound);
                this.close();
            });
            gridPane.add(soundSelector,column,row);

            column++;
            if (column > 9){
                column = 1;
                row++;
                if (row > 5)
                    break;
            }

        }

    }

    private static void getImageFromAtlas(Canvas canvas, Image image, BlockSounds sound) {
        double width = 18.0;
        double height = 18.0;

        int row = sound.soundID / 9;
        int column = sound.soundID % 9;

        canvas.getGraphicsContext2D().clearRect(0,0,width * scale,height * scale);
        canvas.getGraphicsContext2D().drawImage(
                image, width * imageScale * column, height * imageScale * row, width * imageScale, height * imageScale, 0, 0, width * scale, height * scale
        );
    }

    public GridPane getGridPane() {
        return gridPane;
    }
    public String getNote() {
        return note;
    }
}
