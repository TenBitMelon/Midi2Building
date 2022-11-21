package me.melonboy10.midi2building;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@EqualsAndHashCode
public abstract class NoteEvent {

    @Getter protected long tick;
    @Getter protected Schematic.Location location;
    @Getter protected File file;
    @Getter protected SoundAtlas sound;

    public NoteEvent(long tick, Schematic.Location location, SoundAtlas sound) {
        this.tick = tick;
        this.location = location;
        this.sound = sound;
    }

    public File makeFunctionFile(String path) {
        try {
            file = new File(path + this.tick + "-" + this.hashCode() + ".mcfunction");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(getFileContents());
            writer.close();
        } catch (IOException ignored) {}
        return file;
    }

    @NonNull
    abstract public String getFileContents();
}
