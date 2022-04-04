package me.melonboy10.midi2building;

import java.io.IOException;

public interface NoteEvent {

    public void makeFunctionFile(String path, long tick, long time) throws IOException;

    public String getFileName();
}
