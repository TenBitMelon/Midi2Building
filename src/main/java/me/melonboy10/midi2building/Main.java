package me.melonboy10.midi2building;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static File datapackOutput;
    static File song = new File("src/main/resources/defaultSongs/11.mid");
    static Scanner reader;
    {
        reader = new Scanner(String.valueOf(this.getClass().getResource("src/main/resources/resourceLocations.txt")));
        datapackOutput = new File(reader.nextLine());
    }

    public static void main(String[] args) {
        MidiParser parser = new MidiParser(song);
        final HashMap<String, SoundAtlas> noteToSoundType = parser.getNoteToSoundType();

    }

}
