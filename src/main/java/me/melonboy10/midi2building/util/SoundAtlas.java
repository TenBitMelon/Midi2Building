package me.melonboy10.midi2building.util;

import java.util.ArrayList;
import java.util.List;

public enum SoundAtlas {

    NULL(0),
    AMETHYST(9,
        "amethyst_block",
        "budding_amethyst",
        "small_amethyst_bud",
        "medium_amethyst_bud",
        "large_amethyst_bud"),
    AMETHYST_CLUSTER(1

    ),
    ANCIENT_DEBRIS(56,
        "ancient_debris"),
    ANVIL(2

    ),
    AZALEA(3

    ),
    AZALEA_LEAVES(4,
        "azalea_leaves",
        "flowering_azalea_leaves"),
    BAMBOO(5

    ),
    BAMBOO_SAPLING(6

    ),
    BASALT(7

    ),
    BONE_BLOCK(11),
    CALCITE(12),
    CANDLE(13),
    GLOW_BERRIES(24), //CAVE_VINES,
    CHAIN(14,
        "chain"),
    COPPER(15),
    CORAL(16),
    DEEPSLATE(19,
        "deepslate",
        "cobbled_deepslate",
        "polished_deepslate",
        "polished_deepslate"),
    DEEPSLATE_BRICKS(20

    ),
    DEEPSLATE_TILES(21

    ),
    DRIPLEAF(8),
    DRIPSTONE(22,
        "",
        ""),
    FUNGUS(23),
    NETHER_ORE(33),
    HANGING_ROOTS(26),
    HONEY(27),
    LANTERN(28),
    LILY_PAD(29),
    LODESTONE(30),
    MOSS(31),
    NETHER_BRICKS(32),
    NETHER_SPROUTS(35),
    NETHERITE(10),
    NETHERRACK(37),
    NYLIUM(17),
    POWDER_SNOW(40),
    ROOTED_DIRT(41),
    NETHER_ROOTS(34),
    SCAFFOLDING(43),
    SHROOMLIGHT(45),
    SLIME(46),
    SNOW(47),
    SOUL_SAND(48),
    SOUL_SOIL(49),
    SPORE_BLOSSOM(50),
    STEM(18),
    SWEET_BERRY(52),
    TUFF(53),
    VINE(54),
    NETHER_WART(36),
    WET_GRASS(44),
    CLOTH(55),
    GRASS(39),
    GRAVEL(25),
    SAND(42),
    STONE(51),
    WOOD(38);


    public final ArrayList<String> blocks;
    public int textureID;

    SoundAtlas(int textureID, String... block) {
        this.textureID = textureID;
        this.blocks = new ArrayList<>(List.of(block));
    }
}
