package me.melonboy10.midi2building;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Schematic {

    File file;
    ArrayList<Block> blocks = new ArrayList<>();
    ArrayList<Block> blocksCopy = new ArrayList<>();
    HashMap<Integer, String> paletteIds = new HashMap<>();
    Location size;

    public Schematic(File file) {
        this.file = file;


        try {
            NamedTag namedTag = NBTUtil.read(file);

            ListTag<?> sizList = (ListTag<?>) ((CompoundTag) namedTag.getTag()).get("size");
            size = new Location(((IntTag) sizList.get(0)).asInt(), ((IntTag) sizList.get(1)).asInt(), ((IntTag) sizList.get(2)).asInt());

            final int[] index = {0};
            ((ListTag<?>) ((CompoundTag) namedTag.getTag()).get("palette")).forEach(tag -> {
                final String blockName = ((StringTag) ((CompoundTag) tag).get("Name")).getValue();
                paletteIds.put(index[0], blockName.replace("minecraft:", ""));
                index[0]++;
            });
            ((ListTag<?>) ((CompoundTag) namedTag.getTag()).get("blocks")).forEach(tag -> {
                int paletteBlockId = Integer.parseInt(((CompoundTag) tag).get("state").valueToString());
                final ListTag<?> pos = (ListTag<?>) ((CompoundTag) tag).get("pos");
                String block = paletteIds.get(paletteBlockId);
                blocks.add(new Block(block, "", new Location(((IntTag) pos.get(0)).asInt(), ((IntTag) pos.get(1)).asInt(), ((IntTag) pos.get(2)).asInt())));
            });
        } catch (IOException e) {
            System.out.println("Failed to read schematic file.");
            e.printStackTrace();
        }
    }

    public Block getBlock(SoundAtlas sound) {
        for (Block block : blocks) {
            if (sound == null || sound.blocks.contains(block.name.toUpperCase())) return block;
        }
        return null;
    }

    public Block getAndRemoveBlock(SoundAtlas sound) {
        for (Block block : blocksCopy) {
            if ( sound.blocks.contains(block.name.toUpperCase())) {
                blocksCopy.remove(block);
                return block;
            }
        }
        return null;
    }

    public void copyBlocks() {
        blocksCopy = (ArrayList<Block>) blocks.clone();
        for (Block block : blocksCopy) {
            if(block.name.equals("oak_planks")) {
                //System.out.println(block);
            }
        }
    }

    class Block {

        String name;
        Location location;
        String properties;

        public Block(String name, String properties, Location location) {
            this.name = name;
            this.properties = properties;
            this.location = location;
        }

        @Override
        public String toString() {
            return "Block{" +
                    "name='" + name + '\'' +
                    ", location=" + location +
                    ", properties='" + properties + '\'' +
                    '}';
        }

        public String getFormat() {
            return "minecraft:" + name + (properties.isEmpty() ? "" : "[" + properties + "]");
        }
    }

    class Location {

        public static int xOffset = 0;
        public static int yOffset = 1;
        public static int zOffset = 0;

        private int x;
        private int y;
        private int z;

        public Location(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void setOffsets(int xOffset, int yOffset, int zOffset){
            Location.xOffset = xOffset;
            Location.yOffset = yOffset;
            Location.zOffset = zOffset;
        }

        public int getX() {
            return x + xOffset;
        }

        public int getY() {
            return y + yOffset;
        }

        public int getZ() {
            return z + zOffset;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }
}
