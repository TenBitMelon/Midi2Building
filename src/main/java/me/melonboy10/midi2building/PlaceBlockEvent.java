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
        return new StringBuilder()
            // Add check for special blocks like farmland or filled cauldrons to add special animations
            .append(String.format("setblock %s %s %s %s", block.location.getX(), block.location.getY(), block.location.getZ(), block.getFormatted()))
            .append("\n")
            .append("title @a actionbar [\"\",{\"text\":\"|-=\"},{\"text\":\"")
            .append("\u2b1b".repeat((int) (tick / (double) tick * 10)))
            .append("\",\"color\":\"aqua\"},{\"text\":\"")
            .append("\u2b1b".repeat((int) (10 - tick / (double) tick * 10)))
            .append("\",\"color\":\"gray\"},{\"text\":\"=-| \"}]")
            .toString();
//            writer.write(String.format("setblock %s %s %s %s", SchemBlock.location.getX(), SchemBlock.location.getY(), SchemBlock.location.getZ(), SchemBlock.getFormated()) + "\n");
//            //writer.write("title @a actionbar [\"\",{\"text\":\"|-=\"},{\"text\":\"" + "\u2b1b".repeat((int) (time / (double) largestTime * 10)) + "\",\"color\":\"aqua\"},{\"text\":\"" + "\u2b1b".repeat((int) (10 - time / (double) largestTime * 10)) + "\",\"color\":\"gray\"},{\"text\":\"=-| \"}]");
    }
}
