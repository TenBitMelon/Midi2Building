package me.melonboy10.midi2building;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
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

import static me.melonboy10.midi2building.GeneratorApplication.conversion;
import static me.melonboy10.midi2building.GeneratorApplication.scale;
import static me.melonboy10.midi2building.ResourceManager.*;

public class SelectBlocks extends Stage {

    public SelectBlocks() {
        super();

        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);  // [DEBUG] makes the gridPane visible
        for (int i = 0; i < 14; i++) { //Sets the columns to the size of the MC blocks in the image.
            // THIS ONLY WORKS WITH A 14 BLOCK WIDE IMAGE
            gridPane.getColumnConstraints().add(new ColumnConstraints(backgroundImage.getWidth() / imageScale * scale / 14));
        }
        for (int i = 0; i < 10; i++) { //Sets the rows to the size of the MC blocks in the image.
            // THIS ONLY WORKS WITH A 10 BLOCK TALL IMAGE
            gridPane.getRowConstraints().add(new RowConstraints(backgroundImage.getHeight() / imageScale * scale / 10));
        }

        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.UNDECORATED);

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


        Scene scene = new Scene(gridPane);
        scene.setFill(Color.TRANSPARENT);
        scene.setFill(Color.GREY);
        this.setScene(scene);

        int column = 2;
        int row = 1;
        int numBlocks = 0;

        Canvas close = new Canvas(16*scale,16*scale);
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
        gridPane.add(close,13,0);

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
                noteName.setFill(Color.rgb(255, 170, 0)); // Minecraft's "Gold"
                gridPane.add(noteName, column, row);

                Text noteNumber = new Text(String.valueOf(blocks.get(note)));
                noteNumber.setFont(minecraftia);
                noteNumber.setFill(Color.rgb(255, 170, 0)); // Minecraft's "Gold"
                gridPane.add(noteNumber, column + 2, row);

                numBlocks++;
                if (numBlocks >= 16) {
                    break;
                } else if (column == 2) {
                    column = 8;
                } else {
                    row++;
                    column = 2;
                }

            }
        }

    }
}
