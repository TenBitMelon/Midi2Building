package me.melonboy10.midi2building;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;

public class Midi2BlockConversion {

    private boolean isInstantiated = false;
    private MidiParser midiFile;

    public Midi2BlockConversion() {

    }

    public void setMidiFile(File selectedFile) throws InvalidMidiDataException, IOException {
        midiFile = new MidiParser(selectedFile);
        isInstantiated = true;

    }

    public boolean getIsInstantiated() {
        return isInstantiated;
    }

    public MidiParser getMidiFile(){
        return midiFile;
    }

}
