package me.melonboy10.midi2building;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataPack {

    private final String output;
    private final String folderName = "Midi2Building";
    private final String namespace = "midi2building";
    private final ArrayList<NoteEvent> events = new ArrayList<>();
    public long largestTime = 0;
    public Schematic.Location size;

    public DataPack(String datapackOutput) {
        this.output = datapackOutput;
    }

    public void addEvent(NoteEvent noteEvent){
        events.add(noteEvent);
    }

    public String getPath(){
        return output + "/" + folderName + "/data/" + namespace;
    }

    public void generate() throws IOException {
        if (events.isEmpty()) try {
            throw new Exception("No blocks found in schematic");
        } catch (Exception e) {
            e.printStackTrace();
        }


        File functionFolder = new File(output + "/" + folderName + "/data/" + namespace + "/functions");
        for (File file : functionFolder.listFiles()) {
            file.delete();
        }
        File folderGen = new File(output + "/" + folderName + "/data/" + namespace + "/functions/placements");
        folderGen.mkdirs();

        // ----- MC META -----

        File packMeta = new File(output + "/" + folderName + "/pack.mcmeta");
        packMeta.createNewFile();
        FileWriter fileWriter = new FileWriter(packMeta);
        fileWriter.write(
                """
                    {
                        "pack": {
                            "pack_format": 9,
                            "description": "Midi2Building Generator - Made by melonboy10 & Minecraft_Atom"
                        }
                    }"""
        );
        fileWriter.close();

        File clearFunction = new File(output + "/" + folderName + "/data/" + namespace + "/functions/clear.mcfunction");
        clearFunction.createNewFile();
        FileWriter clearWriter = new FileWriter(clearFunction);
        clearWriter.write(String.format("fill %s %s %s %s %s %s air", (Schematic.Location.xOffset), (Schematic.Location.yOffset), (Schematic.Location.zOffset), (Schematic.Location.xOffset + size.getX()), (Schematic.Location.yOffset + size.getY()), (Schematic.Location.zOffset + size.getZ())));
        clearWriter.close();


        File startFunction = new File(output + "/" + folderName + "/data/" + namespace + "/functions/start.mcfunction");
        startFunction.createNewFile();
        FileWriter startWriter = new FileWriter(startFunction);

        File endFunction = new File(output + "/" + folderName + "/data/" + namespace + "/functions/stop.mcfunction");
        endFunction.createNewFile();
        FileWriter endWriter = new FileWriter(endFunction);



        events.forEach((event) -> {
            String name = event.getFileName();
            long time = event.getTick();
            try {
                startWriter.write("schedule function " + namespace + ":placements/" + name + " " + (time+1) + "t\n");
                endWriter.write("schedule clear " + namespace + ":placements/" + name + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        startWriter.close();
        endWriter.close();
    }
}