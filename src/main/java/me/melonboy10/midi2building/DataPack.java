package me.melonboy10.midi2building;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DataPack {

    private final String output;
    private final String folderName = "Midi2Building";
    private final String namespace = "midi2building";
    private final HashMap<Long, Schematic.Block> blocks = new HashMap<>();
    public long largestTime = 0;
    private double timeScale = 0.1;

    public DataPack(String datapackOutput) {
        this.output = datapackOutput;
    }

    public void addBlock(long time, Schematic.Block block) {
        blocks.put(time, block);
    }

    public void setTimeScale(double timeScale) {
        this.timeScale = timeScale;
    }

    public void generate() throws IOException {
        if (blocks.isEmpty()) try {
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

        File startFunction = new File(output + "/" + folderName + "/data/" + namespace + "/functions/start.mcfunction");
        startFunction.createNewFile();
        FileWriter startWriter = new FileWriter(startFunction);

        File endFunction = new File(output + "/" + folderName + "/data/" + namespace + "/functions/stop.mcfunction");
        endFunction.createNewFile();
        FileWriter endWriter = new FileWriter(endFunction);

        blocks.forEach((time, block) -> {
            try {
                String name = makeFunctionFile((long) (time * timeScale), block, time);
                startWriter.write("schedule function " + namespace + ":placements/" + name + " " + (long) (time * timeScale) + "t\n");
                endWriter.write("schedule clear " + namespace + ":placements/" + name + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        startWriter.close();
        endWriter.close();
    }

    public String makeFunctionFile(long tick, Schematic.Block block, long time) throws IOException {
        File file = new File(output + "/" + folderName + "/data/" + namespace + "/functions/placements/" + tick + "-" + block.hashCode() + ".mcfunction");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(String.format("setblock %s %s %s %s", block.location.getX(), block.location.getY(), block.location.getZ(), block.getFormat()) + "\n");
        writer.write("title @a actionbar [\"\",{\"text\":\"|-=\"},{\"text\":\"" + "\u2b1b".repeat((int) (time / (double) largestTime * 10)) + "\",\"color\":\"aqua\"},{\"text\":\"" + "\u2b1b".repeat((int) (10 - time / (double) largestTime * 10)) + "\",\"color\":\"gray\"},{\"text\":\"=-| \"}]");
        writer.close();
        return tick + "-" + block.hashCode();
    }
}
