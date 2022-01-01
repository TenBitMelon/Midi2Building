package me.melonboy10.midi2building.screenElements;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.melonboy10.midi2building.util.SoundAtlas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static me.melonboy10.midi2building.screenElements.GeneratorApplication.conversion;
import static me.melonboy10.midi2building.screenElements.GeneratorApplication.scale;
import static me.melonboy10.midi2building.util.ResourceManager.*;

public class SelectSound extends Stage {
    private static final int IMAGES_PER_ROW = 8;

    private final GridPane gridPane;
    private final String note;
    private final List<Node> soundNodes = new ArrayList<>();
    private ScrollBar sc;
    private int rowsScrolled = 0;
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
        gridPane.setStyle("-fx-background-color: null;"); // Makes it transparent
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

        int isOn = 0; // When this is 0, it gets the on version of the slider
        Canvas slider = new Canvas(12*scale, 15*scale);
        slider.getGraphicsContext2D().clearRect(0,0,12 * scale,15 * scale);
        slider.getGraphicsContext2D().drawImage(
                scrollBar, 12 * isOn * imageScale, 0, 12 * imageScale, 15 * imageScale, 0, 0, 12 * scale, 15 * scale
        );
        GridPane.setMargin(slider, new Insets(-0.5 * scale,0, 0, scale));

        gridPane.add(slider,11,2);

        int numRows = (int)(SoundAtlas.values().length / 9) - 4;
        sc = new ScrollBar();
        sc.setMin(0);
        sc.setMax(numRows);
        sc.setValue(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setMinHeight(90 * scale);
        sc.setMinWidth(180 * scale);
        sc.setVisibleAmount(0.5);
        sc.setStyle(
            """       
                .decrement-arrow {
                    -fx-padding:0;
                 }
                .increment-arrow {
                    -fx-padding:0;
                }
            """ // No idea why this works but this shrinks the increment arrows on the scroll bar
        );
        //sc.setStyle("-fx-font-size: 20px;"); // changes the size of the thumb on the scroll bar (No idea why it doesn't work)
        sc.applyCss(); // I don't think this is necessary but idk

        sc.setOpacity(0.0);// Makes it invisible

        sc.setUnitIncrement(1.0);

        gridPane.add(sc,1,2);
        GridPane.setHalignment(sc, HPos.LEFT);
        GridPane.setValignment(sc, VPos.TOP);

        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                setScroll(new_val.intValue());
                GridPane.setMargin(slider, new Insets((73 * new_val.doubleValue() / (numRows - 1) - 0.5) * scale,0, 0, scale));

            }
        });

        setScroll(0);
    }

    private void setScroll(int rowsScrolled) {
        // Code to add the blocks

        int row = 2;
        int column = 1;

        for (Node node : soundNodes) {
            gridPane.getChildren().remove(node);
        }

        for (SoundAtlas sound : SoundAtlas.values()) {

            if (sound.textureID >= rowsScrolled * 9) {

                Canvas soundIcon = new Canvas(18 * scale, 18 * scale);
                getImageFromAtlas(soundIcon, soundAtlas, sound);
                gridPane.add(soundIcon, column, row);
                soundNodes.add(soundIcon);

                Canvas soundSelector = new Canvas(18 * scale, 18 * scale);
                soundSelector.setOpacity(0.15);
                soundSelector.getGraphicsContext2D().setFill(Color.rgb(255, 255, 255));

                soundSelector.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                    soundSelector.getGraphicsContext2D().fillRect(0, 0, 100, 100);
                });
                soundSelector.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
                    soundSelector.getGraphicsContext2D().clearRect(0, 0, 100, 100);
                });
                soundSelector.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    conversion.getMidiFile().getNoteToSoundType().put(note, sound);
                    getImageFromAtlas(iconToChange, soundAtlas, sound);
                    this.close();
                });

                soundSelector.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
                    System.out.println(scrollEvent.getDeltaY());
                });

                gridPane.add(soundSelector, column, row);
                soundNodes.add(soundSelector);

                column++;
                if (column > 9) {
                    column = 1;
                    row++;
                    if (row > 6)
                        break;
                }
            }
        }
    }

    private static void getImageFromAtlas(Canvas canvas, Image image, SoundAtlas sound) {
        double width = 15.0;
        double height = 15.0;

        double leftOffset = 1.5;
        double topOffset = 1.5;

//        int row = sound.textureID / 9;
        int row = 0;
        int column = sound.textureID;//% 9;

        canvas.getGraphicsContext2D().clearRect(0,0,width * imageScale * scale,height * imageScale * scale);
        canvas.getGraphicsContext2D().drawImage(
                image, width * imageScale * column, height * imageScale * row, width * imageScale, height * imageScale, leftOffset * scale, topOffset * scale, width * scale, height * scale
        );
    }

    public GridPane getGridPane() {
        return gridPane;
    }
    public String getNote() {
        return note;
    }
}
