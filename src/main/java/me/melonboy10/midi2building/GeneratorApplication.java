package me.melonboy10.midi2building;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static me.melonboy10.midi2building.ResourceManager.backgroundImage;

public class GeneratorApplication extends Application {

    public static final int scale = 4;

    @Override
    public void init() throws Exception {
        System.out.println("before");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Midi2Building Generator");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setWidth(backgroundImage.getWidth() / ResourceManager.imageScale * scale);
        stage.setHeight(backgroundImage.getHeight() / ResourceManager.imageScale * scale);

        GridPane parent = new GridPane();
        Scene scene = new Scene(parent);
        stage.setScene(scene);







        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("sotp");
    }
}
