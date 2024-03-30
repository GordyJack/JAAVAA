package net.gordyjack.jaavaa.data.tags;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.gordyjack.jaavaa.block.*;
import net.minecraft.block.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

import java.util.Arrays;
import java.util.concurrent.*;

public class JAAVAABlockTagGenerator
extends FabricTagProvider.BlockTagProvider
implements JAAVAATagGeneratorInterface<Block> {
    public JAAVAABlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    public final TagKey<Block> JAAVAA_COLLECTORS = registerModTagKey("collectors");
    public final TagKey<Block> NEEDS_LEVEL_4_TOOL = MiningLevelManager.getBlockTag(4);
    public final TagKey<Block> NEEDS_LEVEL_5_TOOL = MiningLevelManager.getBlockTag(5);
    private boolean needsDiamond(Block parentBlock) {
        final Block[] NEEDS_DIAMOND_TOOL = {
                Blocks.OBSIDIAN,
                Blocks.CRYING_OBSIDIAN,
                Blocks.NETHERITE_BLOCK,
                Blocks.ANCIENT_DEBRIS
        };
        return Arrays.stream(NEEDS_DIAMOND_TOOL).toList().contains(parentBlock);
    }
    private boolean needsIron(Block parentBlock) {
        final Block[] NEEDS_IRON_TOOL = {
                Blocks.DIAMOND_BLOCK,
                Blocks.DIAMOND_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.EMERALD_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.EMERALD_BLOCK,
                Blocks.GOLD_BLOCK,
                Blocks.RAW_GOLD_BLOCK,
                Blocks.GOLD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.REDSTONE_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE
        };
        return Arrays.stream(NEEDS_IRON_TOOL).toList().contains(parentBlock);
    }
    private boolean needsStone(Block parentBlock) {
        final Block[] NEEDS_STONE_TOOL = {
                Blocks.IRON_BLOCK,
                Blocks.IRON_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.LAPIS_BLOCK,
                Blocks.LAPIS_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.COPPER_BLOCK,
                Blocks.RAW_COPPER_BLOCK,
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.CUT_COPPER,
                Blocks.WEATHERED_COPPER,
                Blocks.WEATHERED_CUT_COPPER,
                Blocks.OXIDIZED_COPPER,
                Blocks.OXIDIZED_CUT_COPPER,
                Blocks.EXPOSED_COPPER,
                Blocks.EXPOSED_CUT_COPPER,
                Blocks.WAXED_COPPER_BLOCK,
                Blocks.WAXED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_OXIDIZED_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.CHISELED_COPPER,
                Blocks.EXPOSED_CHISELED_COPPER,
                Blocks.WEATHERED_CHISELED_COPPER,
                Blocks.OXIDIZED_CHISELED_COPPER,
                Blocks.WAXED_CHISELED_COPPER,
                Blocks.WAXED_EXPOSED_CHISELED_COPPER,
                Blocks.WAXED_WEATHERED_CHISELED_COPPER,
                Blocks.WAXED_OXIDIZED_CHISELED_COPPER,
                Blocks.COPPER_GRATE,
                Blocks.EXPOSED_COPPER_GRATE,
                Blocks.WEATHERED_COPPER_GRATE,
                Blocks.OXIDIZED_COPPER_GRATE,
                Blocks.WAXED_COPPER_GRATE,
                Blocks.WAXED_EXPOSED_COPPER_GRATE,
                Blocks.WAXED_WEATHERED_COPPER_GRATE,
                Blocks.WAXED_OXIDIZED_COPPER_GRATE
        };
        return Arrays.stream(NEEDS_STONE_TOOL).toList().contains(parentBlock);
    }
    private boolean mineableAxe(Block parentBlock) {
        final Block[] MINEABLE_AXE = {
                Blocks.OAK_PLANKS,
                Blocks.SPRUCE_PLANKS,
                Blocks.BIRCH_PLANKS,
                Blocks.JUNGLE_PLANKS,
                Blocks.ACACIA_PLANKS,
                Blocks.DARK_OAK_PLANKS,
                Blocks.CRIMSON_PLANKS,
                Blocks.WARPED_PLANKS,
                Blocks.MANGROVE_PLANKS,
                Blocks.BAMBOO_PLANKS,
                Blocks.CHERRY_PLANKS,
                Blocks.BAMBOO_MOSAIC
        };
        return Arrays.stream(MINEABLE_AXE).toList().contains(parentBlock);
    }
    private boolean mineableHoe(Block parentBlock) {
        final Block[] MINEABLE_HOE = {
                Blocks.NETHER_WART_BLOCK,
                Blocks.WARPED_WART_BLOCK,
                Blocks.SHROOMLIGHT,
                Blocks.MOSS_BLOCK,
                Blocks.SCULK
        };
        return Arrays.stream(MINEABLE_HOE).toList().contains(parentBlock);
    }
    private boolean mineablePickaxe(Block parentBlock) {
        final Block[] MINEABLE_PICKAXE = {
                Blocks.STONE,
                Blocks.GRANITE,
                Blocks.POLISHED_GRANITE,
                Blocks.DIORITE,
                Blocks.POLISHED_DIORITE,
                Blocks.ANDESITE,
                Blocks.POLISHED_ANDESITE,
                Blocks.COBBLESTONE,
                Blocks.GOLD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.IRON_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.COAL_ORE,
                Blocks.DEEPSLATE_COAL_ORE,
                Blocks.NETHER_GOLD_ORE,
                Blocks.LAPIS_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.LAPIS_BLOCK,
                Blocks.SANDSTONE,
                Blocks.CHISELED_SANDSTONE,
                Blocks.CUT_SANDSTONE,
                Blocks.GOLD_BLOCK,
                Blocks.IRON_BLOCK,
                Blocks.BRICKS,
                Blocks.MOSSY_COBBLESTONE,
                Blocks.OBSIDIAN,
                Blocks.DIAMOND_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.DIAMOND_BLOCK,
                Blocks.REDSTONE_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.NETHERRACK,
                Blocks.BASALT,
                Blocks.POLISHED_BASALT,
                Blocks.STONE_BRICKS,
                Blocks.MOSSY_STONE_BRICKS,
                Blocks.CRACKED_STONE_BRICKS,
                Blocks.CHISELED_STONE_BRICKS,
                Blocks.NETHER_BRICKS,
                Blocks.END_STONE,
                Blocks.EMERALD_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.EMERALD_BLOCK,
                Blocks.REDSTONE_BLOCK,
                Blocks.NETHER_QUARTZ_ORE,
                Blocks.QUARTZ_BLOCK,
                Blocks.CHISELED_QUARTZ_BLOCK,
                Blocks.QUARTZ_PILLAR,
                Blocks.WHITE_TERRACOTTA,
                Blocks.ORANGE_TERRACOTTA,
                Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA,
                Blocks.YELLOW_TERRACOTTA,
                Blocks.LIME_TERRACOTTA,
                Blocks.PINK_TERRACOTTA,
                Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA,
                Blocks.CYAN_TERRACOTTA,
                Blocks.PURPLE_TERRACOTTA,
                Blocks.BLUE_TERRACOTTA,
                Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA,
                Blocks.RED_TERRACOTTA,
                Blocks.BLACK_TERRACOTTA,
                Blocks.PRISMARINE,
                Blocks.PRISMARINE_BRICKS,
                Blocks.DARK_PRISMARINE,
                Blocks.TERRACOTTA,
                Blocks.COAL_BLOCK,
                Blocks.RED_SANDSTONE,
                Blocks.CHISELED_RED_SANDSTONE,
                Blocks.CUT_RED_SANDSTONE,
                Blocks.SMOOTH_STONE,
                Blocks.SMOOTH_SANDSTONE,
                Blocks.SMOOTH_QUARTZ,
                Blocks.SMOOTH_RED_SANDSTONE,
                Blocks.PURPUR_BLOCK,
                Blocks.PURPUR_PILLAR,
                Blocks.END_STONE_BRICKS,
                Blocks.MAGMA_BLOCK,
                Blocks.RED_NETHER_BRICKS,
                Blocks.WHITE_CONCRETE,
                Blocks.ORANGE_CONCRETE,
                Blocks.MAGENTA_CONCRETE,
                Blocks.LIGHT_BLUE_CONCRETE,
                Blocks.YELLOW_CONCRETE,
                Blocks.LIME_CONCRETE,
                Blocks.PINK_CONCRETE,
                Blocks.GRAY_CONCRETE,
                Blocks.LIGHT_GRAY_CONCRETE,
                Blocks.CYAN_CONCRETE,
                Blocks.PURPLE_CONCRETE,
                Blocks.BLUE_CONCRETE,
                Blocks.BROWN_CONCRETE,
                Blocks.GREEN_CONCRETE,
                Blocks.RED_CONCRETE,
                Blocks.BLACK_CONCRETE,
                Blocks.DEAD_TUBE_CORAL_BLOCK,
                Blocks.DEAD_BRAIN_CORAL_BLOCK,
                Blocks.DEAD_BUBBLE_CORAL_BLOCK,
                Blocks.DEAD_FIRE_CORAL_BLOCK,
                Blocks.DEAD_HORN_CORAL_BLOCK,
                Blocks.TUBE_CORAL_BLOCK,
                Blocks.BRAIN_CORAL_BLOCK,
                Blocks.BUBBLE_CORAL_BLOCK,
                Blocks.FIRE_CORAL_BLOCK,
                Blocks.HORN_CORAL_BLOCK,
                Blocks.WARPED_NYLIUM,
                Blocks.CRIMSON_NYLIUM,
                Blocks.NETHERITE_BLOCK,
                Blocks.ANCIENT_DEBRIS,
                Blocks.CRYING_OBSIDIAN,
                Blocks.BLACKSTONE,
                Blocks.POLISHED_BLACKSTONE,
                Blocks.POLISHED_BLACKSTONE_BRICKS,
                Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
                Blocks.CHISELED_POLISHED_BLACKSTONE,
                Blocks.GILDED_BLACKSTONE,
                Blocks.CHISELED_NETHER_BRICKS,
                Blocks.CRACKED_NETHER_BRICKS,
                Blocks.QUARTZ_BRICKS,
                Blocks.TUFF,
                Blocks.CALCITE,
                Blocks.OXIDIZED_COPPER,
                Blocks.WEATHERED_COPPER,
                Blocks.EXPOSED_COPPER,
                Blocks.COPPER_BLOCK,
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.OXIDIZED_CUT_COPPER,
                Blocks.WEATHERED_CUT_COPPER,
                Blocks.EXPOSED_CUT_COPPER,
                Blocks.CUT_COPPER,
                Blocks.WAXED_COPPER_BLOCK,
                Blocks.WAXED_WEATHERED_COPPER,
                Blocks.WAXED_EXPOSED_COPPER,
                Blocks.WAXED_OXIDIZED_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WAXED_CUT_COPPER,
                Blocks.DRIPSTONE_BLOCK,
                Blocks.COBBLED_DEEPSLATE,
                Blocks.POLISHED_DEEPSLATE,
                Blocks.DEEPSLATE_TILES,
                Blocks.DEEPSLATE_BRICKS,
                Blocks.CHISELED_DEEPSLATE,
                Blocks.CRACKED_DEEPSLATE_BRICKS,
                Blocks.CRACKED_DEEPSLATE_TILES,
                Blocks.SMOOTH_BASALT,
                Blocks.RAW_IRON_BLOCK,
                Blocks.RAW_COPPER_BLOCK,
                Blocks.RAW_GOLD_BLOCK,
                Blocks.ICE,
                Blocks.PACKED_ICE,
                Blocks.BLUE_ICE,
                Blocks.AMETHYST_BLOCK,
                Blocks.MUD_BRICKS,
                Blocks.PACKED_MUD,
                Blocks.CHISELED_TUFF,
                Blocks.POLISHED_TUFF,
                Blocks.TUFF_BRICKS,
                Blocks.CHISELED_TUFF_BRICKS,
                Blocks.CHISELED_COPPER,
                Blocks.EXPOSED_CHISELED_COPPER,
                Blocks.WEATHERED_CHISELED_COPPER,
                Blocks.OXIDIZED_CHISELED_COPPER,
                Blocks.WAXED_CHISELED_COPPER,
                Blocks.WAXED_EXPOSED_CHISELED_COPPER,
                Blocks.WAXED_WEATHERED_CHISELED_COPPER,
                Blocks.WAXED_OXIDIZED_CHISELED_COPPER,
                Blocks.COPPER_GRATE,
                Blocks.EXPOSED_COPPER_GRATE,
                Blocks.WEATHERED_COPPER_GRATE,
                Blocks.OXIDIZED_COPPER_GRATE,
                Blocks.WAXED_COPPER_GRATE,
                Blocks.WAXED_EXPOSED_COPPER_GRATE,
                Blocks.WAXED_WEATHERED_COPPER_GRATE,
                Blocks.WAXED_OXIDIZED_COPPER_GRATE,

                Blocks.GLOWSTONE,
                Blocks.REINFORCED_DEEPSLATE
        };
        return Arrays.stream(MINEABLE_PICKAXE).toList().contains(parentBlock);
    }
    private boolean mineableShovel(Block parentBlock) {
        final Block[] MINEABLE_SHOVEL = {
                Blocks.CLAY,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.PODZOL,
                Blocks.GRASS_BLOCK,
                Blocks.MYCELIUM,
                Blocks.SNOW_BLOCK,
                Blocks.SOUL_SAND,
                Blocks.SOUL_SOIL,
                Blocks.ROOTED_DIRT,
                Blocks.MUD
        };
        return Arrays.stream(MINEABLE_SHOVEL).toList().contains(parentBlock);
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(JAAVAA_COLLECTORS)
                .add(
                        JAAVAABlocks.ALLAY_COLLECTOR,
                        JAAVAABlocks.ENDER_COLLECTOR);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(
                        Blocks.REINFORCED_DEEPSLATE,
                        Blocks.GLOWSTONE,
                        JAAVAABlocks.STARSTEEL_BLOCK,
                        JAAVAABlocks.ADJUSTABLE_REDSTONE_LAMP)
                .addOptionalTag(ConventionalBlockTags.GLASS_BLOCKS)
                .addOptionalTag(JAAVAA_COLLECTORS);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .addOptionalTag(JAAVAA_COLLECTORS);
        getOrCreateTagBuilder(NEEDS_LEVEL_4_TOOL)
                .add(
                        JAAVAABlocks.STARSTEEL_BLOCK);

        for (Block block : JAAVAABlocks.BLOCKS) {
            String key = block.getTranslationKey();
            if (block instanceof SlabBlock) {
                getOrCreateTagBuilder(BlockTags.SLABS).add(block);
            }
            if (block instanceof WallBlock) {
                getOrCreateTagBuilder(BlockTags.WALLS).add(block);
            }
            if (block instanceof StairsBlock) {
                getOrCreateTagBuilder(BlockTags.STAIRS).add(block);
            }

            Block parent = JAAVAABlocks.getParent(block);
            if (needsDiamond(parent)) {
                getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
            }
            if (needsIron(parent)) {
                getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block);
            }
            if (needsStone(parent)) {
                getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block);
            }
            if (mineableAxe(parent)) {
                getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
            }
            if (mineableHoe(parent)) {
                getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(block);
            }
            if (mineablePickaxe(parent)) {
                getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
            }
            if (mineableShovel(parent)) {
                getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(block);
            }

            if (key.contains("glass")) {
                getOrCreateTagBuilder(ConventionalBlockTags.GLASS_BLOCKS).add(block);
                getOrCreateTagBuilder(BlockTags.IMPERMEABLE).add(block);
            }
            if (key.contains("ice")) {
                getOrCreateTagBuilder(BlockTags.ICE).add(block);
            }
            if (key.contains("obsidian")) {
                getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(block);
            }
            if (key.contains("ore")) {
                if (key.contains("coal")) {
                    getOrCreateTagBuilder(BlockTags.COAL_ORES).add(block);
                }
                if (key.contains("copper")) {
                    getOrCreateTagBuilder(BlockTags.COPPER_ORES).add(block);
                }
                if (key.contains("diamond")) {
                    getOrCreateTagBuilder(BlockTags.DIAMOND_ORES).add(block);
                }
                if (key.contains("emerald")) {
                    getOrCreateTagBuilder(BlockTags.EMERALD_ORES).add(block);
                }
                if (key.contains("gold")) {
                    getOrCreateTagBuilder(BlockTags.GOLD_ORES).add(block);
                }
                if (key.contains("iron")) {
                    getOrCreateTagBuilder(BlockTags.IRON_ORES).add(block);
                }
                if (key.contains("lapis")) {
                    getOrCreateTagBuilder(BlockTags.LAPIS_ORES).add(block);
                }
                if (key.contains("redstone")) {
                    getOrCreateTagBuilder(BlockTags.REDSTONE_ORES).add(block);
                }
            }
            if (key.contains("planks")) {
                getOrCreateTagBuilder(BlockTags.PLANKS).add(block);
            }
            if (key.contains("reinforced_deepslate")) {
                getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE).add(block);
                getOrCreateTagBuilder(BlockTags.WITHER_IMMUNE).add(block);
            }
            if (key.contains("snow")) {
                getOrCreateTagBuilder(BlockTags.SNOW).add(block);
            }
            if (key.contains("soul")) {
                getOrCreateTagBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(block);
                getOrCreateTagBuilder(BlockTags.SOUL_SPEED_BLOCKS).add(block);
            }
            if (key.contains("stone_brick")) {
                getOrCreateTagBuilder(BlockTags.STONE_BRICKS).add(block);
            }
            if (key.contains("terracotta")) {
                getOrCreateTagBuilder(BlockTags.TERRACOTTA).add(block);
            }
            if (key.contains("wart")) {
                getOrCreateTagBuilder(BlockTags.WART_BLOCKS).add(block);
            }
            if (key.contains("wool")) {
                getOrCreateTagBuilder(BlockTags.WOOL).add(block);
            }
        }
    }
    @Override
    public TagKey<Block> registerTagKey(String namespace, String name) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(namespace, name));
    }
}
