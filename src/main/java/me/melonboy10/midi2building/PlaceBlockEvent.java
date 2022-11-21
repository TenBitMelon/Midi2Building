package me.melonboy10.midi2building;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static me.melonboy10.midi2building.Main.nbt;

public class PlaceBlockEvent extends NoteEvent {

    Schematic.Block block;

    public PlaceBlockEvent(long tick, Schematic.Block block, SoundAtlas sound) {
        super(tick, block.location, sound);
        this.block = block;
    }

    @Override
    public String getFileContents() {
        StringBuilder contents = new StringBuilder();

        contents.append(String.format("setblock %s %s %s %s", block.location.getX(), block.location.getY(), block.location.getZ(), block.getFormatted()));
        contents.append("\n");
        contents.append("title @a actionbar [\"\",{\"text\":\"|-=\"},{\"text\":\"" + "\u2b1b".repeat((int) (tick / (double) tick * 10)) + "\",\"color\":\"aqua\"},{\"text\":\"" + "\u2b1b".repeat((int) (10 - tick / (double) tick * 10)) + "\",\"color\":\"gray\"},{\"text\":\"=-| \"}]");

        return contents.toString();
//            writer.write(String.format("setblock %s %s %s %s", SchemBlock.location.getX(), SchemBlock.location.getY(), SchemBlock.location.getZ(), SchemBlock.getFormated()) + "\n");
//            //writer.write("title @a actionbar [\"\",{\"text\":\"|-=\"},{\"text\":\"" + "\u2b1b".repeat((int) (time / (double) largestTime * 10)) + "\",\"color\":\"aqua\"},{\"text\":\"" + "\u2b1b".repeat((int) (10 - time / (double) largestTime * 10)) + "\",\"color\":\"gray\"},{\"text\":\"=-| \"}]");
    }
}
