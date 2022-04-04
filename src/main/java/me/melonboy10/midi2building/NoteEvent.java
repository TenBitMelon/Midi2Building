package me.melonboy10.midi2building;

import java.io.IOException;

public interface NoteEvent {

    boolean makeFunctionFile(String path, long tick, long time) throws IOException;

    NoteEvent copy();

    String getFileName();

    long getTick();
}
