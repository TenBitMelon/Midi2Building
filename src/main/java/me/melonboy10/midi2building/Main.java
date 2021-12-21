package me.melonboy10.midi2building;

import javafx.application.Application;
import me.melonboy10.midi2building.screenElements.GeneratorApplication;

import java.util.HashMap;

public class Main {

    public static final int TEMPO = 105;
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final HashMap<String, String> NOTE_BLOCKS = new HashMap<>(){{
        put("C1", "minecraft:deepslate");
        put("C#1", "minecraft:stone");
        put("D1", "minecraft:moss_block");
        put("D#1", "minecraft:grass_block");
        put("E1", "minecraft:oak_planks");
        put("F#1", "minecraft:white_wool");
        put("G#1", "minecraft:crafting_table");
        put("A1", "minecraft:oxidized_copper");
        put("B1", "minecraft:chain");
        put("C2", "minecraft:chest");
        put("C#2", "minecraft:cake");
        put("D2", "minecraft:tnt");
        put("D#2", "minecraft:tnt");
    }};

    public static void main(String[] args) throws Exception {
        Application.launch(GeneratorApplication.class, args);
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/10.mid"));
//        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/11.mid"));
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/11short.mid"));
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/12long.mid"));
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/13long.mid"));
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/test1.mid"));
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/test2.mid"));
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/test3.mid"));
////        Sequence sequence = MidiSystem.getSequence(new File("src/main/resources/defaultSongs/Thumbnail.mid"));
//
//        File packMeta = new File("output/MusicBuilder/pack.mcmeta");
//        FileWriter fileWriter = new FileWriter(packMeta);
//        fileWriter.write(
//            "{\n" +
//            "    \"pack\": {\n" +
//            "        \"pack_format\": 7,\n" +
//            "        \"description\": \"The default data for Minecraft\"\n" +
//            "    }\n" +
//            "}"
//        );
//        fileWriter.close();
//
//        File functionFolder = new File("output/MusicBuilder/data/music_builder/functions");
//        for (File file : functionFolder.listFiles()) {
//            file.delete();
//        }
//
//        File startFile = new File("output/MusicBuilder/data/music_builder/functions/start.mcfunction");
//        File clearFile = new File("output/MusicBuilder/data/music_builder/functions/clear.mcfunction");
//        FileWriter startWriter = new FileWriter(startFile);
//        FileWriter clearWriter = new FileWriter(clearFile);
//
//        int blockNumber = 0;
//        for (Track track :  sequence.getTracks()) {
//            for (int i=0; i < track.size(); i++) {
//                MidiEvent event = track.get(i);
////                System.out.print("@" + event.getTick() + " ");
//                MidiMessage message = event.getMessage();
//                if (message instanceof ShortMessage sm) {
////                    System.out.print("Channel: " + sm.getChannel() + " ");
////                    if (sm.getCommand() == NOTE_ON) {
////                        int key = sm.getData1();
////                        int octave = (key / 12)-1;
////                        int note = key % 12;
////                        String noteName = NOTE_NAMES[note];
////                        int velocity = sm.getData2();
////                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
////                    } else if (sm.getCommand() == NOTE_OFF) {
////                        int key = sm.getData1();
////                        int octave = (key / 12)-1;
////                        int note = key % 12;
////                        String noteName = NOTE_NAMES[note];
////                        int velocity = sm.getData2();
////                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
////                    } else {
////                        System.out.println("Command:" + sm.getCommand());
////                    }
//                    if (sm.getCommand() == NOTE_ON) {
//                        blockNumber++;
//                        System.out.println(NOTE_NAMES[sm.getData1() % 12] + ((sm.getData1() / 12) - 2));
//                        File functionFile = new File("output/MusicBuilder/data/music_builder/functions/block_number_" + blockNumber + ".mcfunction");
//                        functionFile.createNewFile();
//                        FileWriter functionWriter = new FileWriter(functionFile);
//                        functionWriter.write(writeBlock(blockNumber, NOTE_BLOCKS.getOrDefault(NOTE_NAMES[sm.getData1() % 12] + ((sm.getData1() / 12) - 2), "minecraft:air")));
//                        functionWriter.close();
//                        startWriter.write("schedule function " + "music_builder:block_number_" + blockNumber + " " + (int) (event.getTick() * (1200.0 / sequence.getResolution() / TEMPO)) + "t\n");
//                        clearWriter.write("schedule clear " + "music_builder:block_number_" + blockNumber + "\n");
//                    }
//                }
//
//            }
//        }
//        startWriter.close();
//        clearWriter.close();

    }

    private static String writeBlock(int blockNumber, String block) {
        return "setblock " + (int) blockNumber / 100 + " 60 " + blockNumber % 100 + " " + block;
    }

}
