package me.melonboy10.midi2building;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static String datapackOutput;
    static File song = new File("src/main/resources/defaultSongs/Thumbnail.mid");
    static File nbt = new File("src/main/resources/palette_test.nbt"); // new File("src/main/resources/thumbnail.nbt");
    static HashMap<String, SoundAtlas> noteToSound = new HashMap<>(){{
        put("1C",   SoundAtlas.DEEPSLATE); //
        put("1C#",  SoundAtlas.STONE); //
        put("1D",   SoundAtlas.MOSS_BLOCK); //
        put("1D#",  SoundAtlas.GRASS); //
        put("1E",   SoundAtlas.WOOD); //
        put("1F#",  SoundAtlas.WOOL); //
        put("1G#",  null); //
        put("1A",   SoundAtlas.COPPER); //
        put("1B",   SoundAtlas.CHAIN); //
        put("2C",   SoundAtlas.DEEPSLATE); //
        put("2C#",  SoundAtlas.STONE); //
        put("2D",   SoundAtlas.MOSS_BLOCK); //
        put("2D#",  SoundAtlas.GRASS); //
        put("2E",   SoundAtlas.WOOD); //
        put("2F#",  SoundAtlas.WOOL); //
        put("2G#",  null); //
        put("2A",   SoundAtlas.COPPER); //
        put("2B",   SoundAtlas.CHAIN); //
        put("3C",   null); //
        put("3C#",  null); //
        put("3D",   null); //
        put("3D#",  null); //
        put("3E",   null); //
        put("3F#",  null); //
        put("3G#",  null); //
        put("3A",   null); //
        put("3B",   null); //
    }};

    static {
        try {
            Scanner reader = new Scanner(new File("src/main/resources/resourceLocations.txt"));
            datapackOutput = reader.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        MidiParser parser = new MidiParser(song);
        Schematic schematic = new Schematic(nbt);
        schematic.copyBlocks();
        DataPack dataPack = new DataPack(datapackOutput);

        HashMap<SoundAtlas, Integer> soundCount = new HashMap<>();

        long largestTime = 0;
        for (Note note : parser.getNotes()) {
            SoundAtlas key = noteToSound.get(note.getNote());
            if (key != null) {
                if (soundCount.containsKey(key)) {
                    soundCount.put(key, soundCount.get(key) + 1);
                } else {
                    soundCount.put(key, 1);
                }
                Schematic.Block block = schematic.getAndRemoveBlock(key);
                if (block != null) {
                    dataPack.addBlock(note.getTime(), block);
                    largestTime = Math.max(largestTime, note.getTime());
                } else {
//                    System.out.println("!!! Block not found in schematic with sound " + key + " !!!");
                }
            } else {
//                System.out.println("!!! Found note without block matching !!! " + note.getNote());
            }
        }

        dataPack.largestTime = largestTime;
        dataPack.generate();
        System.out.println(soundCount);
    }
}
