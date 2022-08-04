package me.melonboy10.midi2building;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@EqualsAndHashCode
public abstract class NoteEvent {

    @Getter private long tick;
    @Getter private Schematic.Location location;
    @Getter private File file;
    @Getter private SoundAtlas sound;

    public NoteEvent(long tick, Schematic.Location location, SoundAtlas sound) {
        this.tick = tick;
        this.location = location;
        this.sound = sound;
    }

    public File makeFunctionFile(String path, long tick, long time) throws IOException {
        file = new File(path + tick + "-" + this.hashCode() + ".mcfunction");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(getFileContents());
        writer.close();
        return file;
    }

    abstract public String getFileContents();
}
