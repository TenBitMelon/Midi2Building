package me.melonboy10.midi2building;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.StringTag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    static String datapackOutput;
    static File song = new File("src/main/resources/defaultSongs/Thumbnail.mid");
    static File nbt = new File("src/main/resources/sound_lattice.nbt"); // new File("src/main/resources/thumbnail.nbt");
    static HashMap<String, SoundAtlas> noteToSound = new HashMap<>(){{
        put("1C",   null); //
        put("1C#",  null); //
        put("1D",   null); //
        put("1D#",  null); //
        put("1E",   null); //
        put("1F#",  null); //
        put("1G#",  null); //
        put("1A",   null); //
        put("1B",   null); //
        put("2C",   SoundAtlas.HONEY); // 38
        put("2C#",  SoundAtlas.CANDLE); // 33
        put("2D",   SoundAtlas.AMETHYST_BLOCK); //
        put("2D#",  SoundAtlas.GRASS); // 20
        put("2E",   SoundAtlas.ANVIL); // 240
        put("2F#",  SoundAtlas.WART_BLOCK); // 50
        put("2G#",  SoundAtlas.BONE); // 34
        put("2A",   SoundAtlas.NETHERITE); // 44
        put("2B",   SoundAtlas.METAL); // 12
        put("3C",   SoundAtlas.WOOD); // 28
        put("3C#",  SoundAtlas.WOOL); // 140
        put("3D",   SoundAtlas.SAND); // 9
        put("3D#",  SoundAtlas.TUFF); // 10
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

        for (Note note : parser.getNotes()) {
            SoundAtlas key = noteToSound.get(note.getNote());
            if (key != null) {
                Schematic.Block block = schematic.getAndRemoveBlock(null);
                if (block != null) dataPack.addBlock(note.getTime(), block);
            } else {
                System.out.println("!!! Found note without block matching !!! " + note.getNote());
            }
        }

        dataPack.generate();
    }
}
