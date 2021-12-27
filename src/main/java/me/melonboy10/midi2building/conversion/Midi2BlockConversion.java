package me.melonboy10.midi2building.conversion;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static me.melonboy10.midi2building.screenElements.GeneratorApplication.conversion;

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
