package me.melonboy10.midi2building;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

import static me.melonboy10.midi2building.GeneratorApplication.scale;
import static me.melonboy10.midi2building.ResourceManager.backgroundImage;
import static me.melonboy10.midi2building.ResourceManager.imageScale;

public class SelectBlocks extends Stage {

    public SelectBlocks() {
        super();

        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);  // [DEBUG] makes the gridPane visible
        for (int i = 0; i < 14; i++) { //S  ets the columns to the size of the MC blocks in the image.
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
        scene.setFill(Color.CYAN);
        this.setScene(scene);

    }
}
