package me.melonboy10.midi2building.conversion;

import me.melonboy10.midi2building.util.ResourceManager;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class MidiParser {
    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private File midiFile;
    private Sequence sequence;
    private final HashMap<String, Integer> blockNumber = new HashMap<>();
    private final HashMap<String, ResourceManager.BlockSounds> blockSound = new HashMap<>();

    public MidiParser(File midiFile) throws InvalidMidiDataException, IOException {
        this.midiFile = midiFile;
        sequence = MidiSystem.getSequence(this.midiFile);

        for (Track track :  sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
//                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage sm) {
//                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = octave + NOTE_NAMES[note];

                        if (blockNumber.containsKey(noteName)) {
                            blockNumber.put(noteName,blockNumber.get(noteName) + 1);
                        } else {
                            blockNumber.put(noteName,1);
                            blockSound.put(noteName, ResourceManager.BlockSounds.NULL);
                        }
                    }
                }
            }
        }
    }

    public HashMap<String, Integer> getBlockNumber() {
        return blockNumber;
    }
    public HashMap<String, ResourceManager.BlockSounds> getBlockSound() {
        return blockSound;
    }
}
