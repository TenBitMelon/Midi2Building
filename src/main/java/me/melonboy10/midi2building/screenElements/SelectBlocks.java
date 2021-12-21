package me.melonboy10.midi2building.screenElements;

import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
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

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static me.melonboy10.midi2building.screenElements.GeneratorApplication.conversion;
import static me.melonboy10.midi2building.screenElements.GeneratorApplication.scale;
import static me.melonboy10.midi2building.util.ResourceManager.*;

public class SelectBlocks extends Stage {

    public SelectBlocks() {
        super();

        // Background Code
        ImageView background = new ImageView();

        background.setImage(blockSelectionbackgroundImage);
        background.setPreserveRatio(true);
        background.setFitWidth(blockSelectionbackgroundImage.getWidth()/ imageScale * scale);

        GridPane gridPane = new GridPane();
        gridPane.add(background,0,0);
        GridPane.setValignment(background, VPos.TOP);

        double constant = blockSelectionbackgroundImage.getWidth()/ imageScale * scale / (imageScale * scale);
        constant = scale;
        System.out.println(constant);
        gridPane.getColumnConstraints().add(new ColumnConstraints(constant * 7));
        for (int i = 0; i < 9; i++) { //Sets the columns to the size of the slots in the chest image.
            // THIS ONLY WORKS WITH CHEST SLOTS
            gridPane.getColumnConstraints().add(new ColumnConstraints( constant *  18));
        }
        gridPane.getColumnConstraints().add(new ColumnConstraints(constant * 7));

        gridPane.getRowConstraints().add(new RowConstraints( constant * 7));
        gridPane.getRowConstraints().add(new RowConstraints(constant * 10));
        for (int i = 0; i < 7; i++) { //Sets the rows to the size of the slots in the chest image.
            // THIS ONLY WORKS WITH CHEST SLOTS
            gridPane.getRowConstraints().add(new RowConstraints( constant * 18));
        }
        gridPane.getRowConstraints().add(new RowConstraints( constant * 7));

        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.TRANSPARENT);

        gridPane.setGridLinesVisible(true);  // [DEBUG] makes the gridPane visible


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

        Canvas close = new Canvas(7*scale,7*scale);
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
        gridPane.add(close,10,0);


        int column = 1;
        int row = 2;
        int numBlocks = 0;

        if (conversion.getIsInstantiated()) {
            HashMap<String,Integer> blocks = conversion.getMidiFile().getBlocks();
            List<String> notes = new ArrayList<>(blocks.keySet());
            Collections.sort(notes);
            System.out.println(notes);

            for (int i = notes.size() - 1; i >= 0; i--) {
                String note = notes.get(i);
                String octave;
                String noteLetter;
                if (note.charAt(0) == '-') {
                    octave = note.substring(0,2);
                    noteLetter = note.substring(2);
                } else {
                    octave = note.substring(0,1);
                    noteLetter = note.substring(1);
                }

                Text noteName = new Text(noteLetter + octave);
                noteName.setFont(minecraftia);
                noteName.setFill(Color.rgb(255, 255, 255)); // Minecraft's "Gold"
                gridPane.add(noteName, column, row);

                Text noteNumber = new Text(String.valueOf(blocks.get(note)));
                noteNumber.setFont(minecraftia);
                noteNumber.setFill(Color.rgb(255, 255, 255)); // Minecraft's "Gold"
                gridPane.add(noteNumber, column, row+2);

                numBlocks++;
                if (numBlocks >= 12) {
                    break;
                } else if (column >= 6) {
                    column = 1;
                    row = 6;
                } else {
                    column++;
                }

            }
        }

    }
}
