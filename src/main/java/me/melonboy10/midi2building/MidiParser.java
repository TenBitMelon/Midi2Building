package me.melonboy10.midi2building;

import javax.sound.midi.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MidiParser {
    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private final File midiFile;
    private Sequence sequence;
    private final HashMap<String, Integer> noteCount = new HashMap<>();
    private final ArrayList<Note> notes = new ArrayList<Note>();

    public MidiParser(File midiFile) {
        this.midiFile = midiFile;
        try {
            sequence = MidiSystem.getSequence(this.midiFile);
        } catch (Exception e) {
            System.out.println("Failed to read midi file!");
        }

        for (Track track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage sm) {
//                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = octave + NOTE_NAMES[note];
                        notes.add(new Note(event.getTick(), noteName));

                        if (noteCount.containsKey(noteName)) {
                            noteCount.put(noteName, noteCount.get(noteName) + 1);
                        } else {
                            noteCount.put(noteName, 1);
                        }
                    }
                }
            }
        }
    }

    public HashMap<String, Integer> getNoteCount() {
        return noteCount;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }
}
