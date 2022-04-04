package me.melonboy10.midi2building;

import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.*;

import java.io.File;
import java.io.IOException;
import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Schematic {

    File file;
    ArrayList<Block> blocks = new ArrayList<>();
    ArrayList<Block> blocksCopy = new ArrayList<>();
    HashMap<Integer, String> paletteIds = new HashMap<>();
    HashMap<Integer, String> propertyIds = new HashMap<>();
    Block[][][] blockGrid;

    public Schematic(File file) {
        this.file = file;


        try {
            NamedTag namedTag = NBTUtil.read(file);
            final ListTag<?> size = (ListTag<?>) ((CompoundTag) namedTag.getTag()).get("size");
            blockGrid = new Block[((IntTag) size.get(0)).asInt()][((IntTag) size.get(1)).asInt()][((IntTag) size.get(2)).asInt()];

            final int[] index = {0};
            ((ListTag<?>) ((CompoundTag) namedTag.getTag()).get("palette")).forEach(tag -> {
                final String blockName = ((StringTag) ((CompoundTag) tag).get("Name")).getValue();
                paletteIds.put(index[0], blockName.replace("minecraft:", ""));
                if (((CompoundTag) tag).containsKey("Properties")) {
                    try {
                        String prop = SNBTUtil.toSNBT(((CompoundTag) tag).get("Properties"));
                        propertyIds.put(index[0], prop.substring(1, prop.length() - 1).replaceAll(":", "="));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                index[0]++;
            });
            ((ListTag<?>) ((CompoundTag) namedTag.getTag()).get("blocks")).forEach(tag -> {
                int paletteBlockId = Integer.parseInt(((CompoundTag) tag).get("state").valueToString());
                final ListTag<?> pos = (ListTag<?>) ((CompoundTag) tag).get("pos");
                String blockName = paletteIds.get(paletteBlockId);
                final Location location = new Location(((IntTag) pos.get(0)).asInt(), ((IntTag) pos.get(1)).asInt(), ((IntTag) pos.get(2)).asInt());
                final Block block = new Block(blockName, propertyIds.getOrDefault(paletteBlockId, ""), location);
                blocks.add(block);
                blockGrid[location.x][location.y][location.z] = block;
            });
        } catch (IOException e) {
            System.out.println("Filed to read schematic file.");
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
        for (int i = 0; i < blocksCopy.size(); i++) {
            Block block = blocksCopy.get(i);
            if (sound == null || sound.blocks.contains(block.name.toUpperCase())) {
                return blocksCopy.remove(i);
            }
        }
        return null;
    }

    public void copyBlocks() {
        blocksCopy = (ArrayList<Block>) blocks.clone();
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

        public static final int xOffset = 0;
        public static final int yOffset = 1;
        public static final int zOffset = 0;

        private final int x;
        private final int y;
        private final int z;

        public Location(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
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
