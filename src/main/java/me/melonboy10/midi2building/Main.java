package me.melonboy10.midi2building;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static String datapackOutput;
    static File song = new File("src/main/resources/defaultSongs/Thumbnail.mid");
    public static File nbt = new File("src/main/resources/tiny_test.nbt"); // new File("src/main/resources/thumbnail.nbt");
    static HashMap<String, NoteEvent> noteToSound = new HashMap<>(){{
        put("1C",   new PlaceBlock(SoundAtlas.DEEPSLATE)); //
        put("1C#",  new PlaceBlock(SoundAtlas.STONE)); //
        put("1D",   new PlaceBlock(SoundAtlas.MOSS_BLOCK)); //
        put("1D#",  new PlaceBlock(SoundAtlas.GRASS)); //
        put("1E",   new PlaceBlock(SoundAtlas.WOOD)); //
        put("1F#",  new PlaceBlock(SoundAtlas.WOOL)); //
        put("1G#",  null); //
        put("1A",   new PlaceBlock(SoundAtlas.COPPER)); //
        put("1B",   new PlaceBlock(SoundAtlas.CHAIN)); //
        put("2C",   new PlaceBlock(SoundAtlas.DEEPSLATE)); //
        put("2C#",  new PlaceBlock(SoundAtlas.STONE)); //
        put("2D",   new PlaceBlock(SoundAtlas.MOSS_BLOCK)); //
        put("2D#",  new PlaceBlock(SoundAtlas.GRASS)); //
        put("2E",   new PlaceBlock(SoundAtlas.WOOD)); //
        put("2F#",  new PlaceBlock(SoundAtlas.WOOL)); //
        put("2G#",  null); //
        put("2A",   new PlaceBlock(SoundAtlas.COPPER)); //
        put("2B",   new PlaceBlock(SoundAtlas.CHAIN)); //
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
        System.out.println(parser.getNoteCount());
        Schematic schematic = new Schematic(nbt);
        schematic.copyBlocks();
        DataPack dataPack = new DataPack(datapackOutput);
        double timeScale = 0.1;

        long largestTime = 0;
        for (Note note : parser.getNotes()) {
            NoteEvent key = noteToSound.get(note.getNote());
            if (key != null) {
                key.makeFunctionFile(dataPack.getPath() + "/functions/placements/", (long) (note.getTime() * timeScale), note.getTime());
                largestTime = Math.max(largestTime, note.getTime());
                dataPack.addEvent((long) (note.getTime() * timeScale), key.getFileName());
            } else {
                //System.out.println("!!! Found note without block matching !!! " + note.getNote());
            }
        }

        dataPack.size = schematic.size;
        dataPack.largestTime = largestTime;
        dataPack.generate();

        for (Schematic.Block block : PlaceBlock.schematic.blocksCopy){
            if(!block.name.equals("air"))
                System.out.println(block);
        }
    }
}
