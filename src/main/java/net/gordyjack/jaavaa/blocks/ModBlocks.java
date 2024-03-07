package net.gordyjack.jaavaa.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.*;
import net.gordyjack.jaavaa.JAAVAA;

import net.fabricmc.fabric.api.item.v1.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

import java.util.*;

public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<SlabBlock> SLABS = new ArrayList<>();
    private static final Map<SlabBlock, Block> SLAB_PARENTS = new HashMap<>();
    public static final List<WallBlock> WALLS = new ArrayList<>();
    private static final Map<WallBlock, Block> WALL_PARENTS = new HashMap<>();
    public static final List<StairsBlock> STAIRS = new ArrayList<>();
    private static final Map<StairsBlock, Block> STAIR_PARENTS = new HashMap<>();
    public static final Block[] VANILLA_BLOCKS = {
            Blocks.STONE,
            Blocks.GRANITE,
            Blocks.POLISHED_GRANITE,
            Blocks.DIORITE,
            Blocks.POLISHED_DIORITE,
            Blocks.ANDESITE,
            Blocks.POLISHED_ANDESITE,
            Blocks.GRASS_BLOCK,
            Blocks.DIRT,
            Blocks.COARSE_DIRT,
            Blocks.PODZOL,
            Blocks.COBBLESTONE,
            Blocks.OAK_PLANKS,
            Blocks.SPRUCE_PLANKS,
            Blocks.BIRCH_PLANKS,
            Blocks.JUNGLE_PLANKS,
            Blocks.ACACIA_PLANKS,
            Blocks.CHERRY_PLANKS,
            Blocks.DARK_OAK_PLANKS,
            Blocks.MANGROVE_PLANKS,
            Blocks.BAMBOO_PLANKS,
            Blocks.BAMBOO_MOSAIC,
            Blocks.BEDROCK,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.OAK_LEAVES,
            Blocks.SPRUCE_LEAVES,
            Blocks.BIRCH_LEAVES,
            Blocks.JUNGLE_LEAVES,
            Blocks.ACACIA_LEAVES,
            Blocks.CHERRY_LEAVES,
            Blocks.DARK_OAK_LEAVES,
            Blocks.MANGROVE_LEAVES,
            Blocks.AZALEA_LEAVES,
            Blocks.FLOWERING_AZALEA_LEAVES,
            Blocks.GLASS, //TODO CUSTOM FUNCTION
            Blocks.LAPIS_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.LAPIS_BLOCK,
            Blocks.SANDSTONE,
            Blocks.CHISELED_SANDSTONE,
            Blocks.CUT_SANDSTONE,
            Blocks.WHITE_WOOL,
            Blocks.ORANGE_WOOL,
            Blocks.MAGENTA_WOOL,
            Blocks.LIGHT_BLUE_WOOL,
            Blocks.YELLOW_WOOL,
            Blocks.LIME_WOOL,
            Blocks.PINK_WOOL,
            Blocks.GRAY_WOOL,
            Blocks.LIGHT_GRAY_WOOL,
            Blocks.CYAN_WOOL,
            Blocks.PURPLE_WOOL,
            Blocks.BLUE_WOOL,
            Blocks.BROWN_WOOL,
            Blocks.GREEN_WOOL,
            Blocks.RED_WOOL,
            Blocks.BLACK_WOOL,
            Blocks.GOLD_BLOCK,
            Blocks.IRON_BLOCK,
            Blocks.BRICKS,
            Blocks.MOSSY_COBBLESTONE,
            Blocks.OBSIDIAN,
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.DIAMOND_BLOCK,
            //Blocks.REDSTONE_ORE, //TODO CUSTOM FUNCTION
            //Blocks.DEEPSLATE_REDSTONE_ORE, //TODO CUSTOM FUNCTION
            Blocks.SNOW,
            Blocks.ICE, //TODO CUSTOM FUNCTION
            Blocks.CLAY,
            Blocks.NETHERRACK,
            Blocks.SOUL_SAND, //TODO CUSTOM FUNCTION
            Blocks.SOUL_SOIL,
            Blocks.GLOWSTONE, //TODO CUSTOM FUNCTION
            Blocks.WHITE_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.ORANGE_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.MAGENTA_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.LIGHT_BLUE_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.YELLOW_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.LIME_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.PINK_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.GRAY_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.LIGHT_GRAY_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.CYAN_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.PURPLE_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.BLUE_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.BROWN_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.GREEN_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.RED_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.BLACK_STAINED_GLASS, //TODO CUSTOM FUNCTION
            Blocks.STONE_BRICKS,
            Blocks.MOSSY_STONE_BRICKS,
            Blocks.CRACKED_STONE_BRICKS,
            Blocks.CHISELED_STONE_BRICKS,
            Blocks.PACKED_MUD,
            Blocks.MUD_BRICKS,
            Blocks.PUMPKIN,
            Blocks.MELON,
            Blocks.MYCELIUM,
            Blocks.NETHER_BRICKS,
            //Blocks.REDSTONE_LAMP, //TODO CUSTOM FUNCTION
            Blocks.EMERALD_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.REDSTONE_BLOCK, //TODO CUSTOM FUNCTION
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.QUARTZ_BLOCK,
            Blocks.CHISELED_QUARTZ_BLOCK,
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
            Blocks.SLIME_BLOCK, //TODO CUSTOM FUNCTION
            Blocks.PRISMARINE,
            Blocks.PRISMARINE_BRICKS,
            Blocks.DARK_PRISMARINE,
            Blocks.SEA_LANTERN, //TODO CUSTOM FUNCTION
            Blocks.TERRACOTTA,
            Blocks.COAL_BLOCK,
            Blocks.PACKED_ICE, //TODO CUSTOM FUNCTION
            Blocks.RED_SANDSTONE,
            Blocks.CHISELED_RED_SANDSTONE,
            Blocks.CUT_RED_SANDSTONE,
            Blocks.SMOOTH_STONE,
            Blocks.SMOOTH_SANDSTONE,
            Blocks.SMOOTH_QUARTZ,
            Blocks.SMOOTH_RED_SANDSTONE,
            Blocks.PURPUR_BLOCK,
            Blocks.END_STONE_BRICKS,
            Blocks.MAGMA_BLOCK, //TODO CUSTOM FUNCTION
            Blocks.NETHER_WART_BLOCK,
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
            Blocks.DRIED_KELP_BLOCK,
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
            Blocks.BLUE_ICE, //TODO CUSTOM FUNCTION
            Blocks.WARPED_NYLIUM,
            Blocks.WARPED_WART_BLOCK,
            Blocks.CRIMSON_NYLIUM,
            Blocks.SHROOMLIGHT, //TODO CUSTOM FUNCTION
            Blocks.CRIMSON_PLANKS,
            Blocks.WARPED_PLANKS,
            Blocks.HONEY_BLOCK, //TODO CUSTOM FUNCTION
            Blocks.HONEYCOMB_BLOCK,
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
            Blocks.AMETHYST_BLOCK, //TODO CUSTOM FUNCTION
            Blocks.TUFF,
            Blocks.POLISHED_TUFF,
            Blocks.CHISELED_TUFF,
            Blocks.TUFF_BRICKS,
            Blocks.CHISELED_TUFF_BRICKS,
            Blocks.CALCITE,
            Blocks.TINTED_GLASS,
            Blocks.SCULK,
            Blocks.COPPER_BLOCK, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.EXPOSED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WEATHERED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.OXIDIZED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.OXIDIZED_CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WEATHERED_CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.EXPOSED_CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.OXIDIZED_CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WEATHERED_CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.EXPOSED_CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_OXIDIZED_CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_WEATHERED_CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_EXPOSED_CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_CHISELED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_COPPER_BLOCK, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_WEATHERED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_EXPOSED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_OXIDIZED_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_OXIDIZED_CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_WEATHERED_CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_EXPOSED_CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_CUT_COPPER, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.EXPOSED_COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WEATHERED_COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.OXIDIZED_COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_EXPOSED_COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_WEATHERED_COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.WAXED_OXIDIZED_COPPER_GRATE, //TODO CUSTOM FUNCTION / RECIPES
            Blocks.DRIPSTONE_BLOCK,
            Blocks.MOSS_BLOCK,
            Blocks.ROOTED_DIRT,
            Blocks.MUD, //TODO CUSTOM FUNCTION / RECIPES
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
            Blocks.REINFORCED_DEEPSLATE
    };
    
    public static final Block TEST_BLOCK = registerBlock("jamba_test_block", new Block(
            FabricBlockSettings.copyOf(Blocks.STONE)
    ));
    public static final Block TEST_MINI_BLOCK = registerBlock("jamba_test_mini_block", new MiniBlock(
            TEST_BLOCK,
            FabricBlockSettings.copyOf(TEST_BLOCK)
    ));
    
    private static Block registerBlock(String name, Block block) {
        BLOCKS.add(block);
        
        registerBlockItem(name, block);
        return registerBlockWithoutItem(name, block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, JAAVAA.getID(name), new BlockItem(block, new FabricItemSettings()));
    }
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, JAAVAA.getID(name), block);
    }
    private static boolean hasSlab(Block block) {
        final Block[] BLOCKS_WITH_SLABS = {
                Blocks.STONE,
                Blocks.GRANITE,
                Blocks.POLISHED_GRANITE,
                Blocks.DIORITE,
                Blocks.POLISHED_DIORITE,
                Blocks.ANDESITE,
                Blocks.POLISHED_ANDESITE,
                Blocks.COBBLESTONE,
                Blocks.OAK_PLANKS,
                Blocks.SPRUCE_PLANKS,
                Blocks.BIRCH_PLANKS,
                Blocks.JUNGLE_PLANKS,
                Blocks.ACACIA_PLANKS,
                Blocks.CHERRY_PLANKS,
                Blocks.DARK_OAK_PLANKS,
                Blocks.MANGROVE_PLANKS,
                Blocks.SANDSTONE,
                Blocks.CUT_SANDSTONE,
                Blocks.PRISMARINE,
                Blocks.PRISMARINE_BRICKS,
                Blocks.DARK_PRISMARINE,
                Blocks.BAMBOO_PLANKS,
                Blocks.BAMBOO_MOSAIC,
                Blocks.SMOOTH_STONE,
                Blocks.BRICKS,
                Blocks.STONE_BRICKS,
                Blocks.MUD_BRICKS,
                Blocks.NETHER_BRICKS,
                Blocks.QUARTZ_BLOCK,
                Blocks.RED_SANDSTONE,
                Blocks.CUT_RED_SANDSTONE,
                Blocks.PURPUR_BLOCK,
                Blocks.SMOOTH_RED_SANDSTONE,
                Blocks.SMOOTH_QUARTZ,
                Blocks.SMOOTH_SANDSTONE,
                Blocks.MOSSY_COBBLESTONE,
                Blocks.MOSSY_STONE_BRICKS,
                Blocks.RED_NETHER_BRICKS,
                Blocks.END_STONE_BRICKS,
                Blocks.CRIMSON_PLANKS,
                Blocks.WARPED_PLANKS,
                Blocks.BLACKSTONE,
                Blocks.POLISHED_BLACKSTONE_BRICKS,
                Blocks.POLISHED_BLACKSTONE,
                Blocks.TUFF,
                Blocks.POLISHED_TUFF,
                Blocks.TUFF_BRICKS,
                Blocks.OXIDIZED_CUT_COPPER,
                Blocks.WEATHERED_CUT_COPPER,
                Blocks.EXPOSED_CUT_COPPER,
                Blocks.CUT_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WAXED_CUT_COPPER,
                Blocks.COBBLED_DEEPSLATE,
                Blocks.POLISHED_DEEPSLATE,
                Blocks.DEEPSLATE_TILES,
                Blocks.DEEPSLATE_BRICKS
        };
        return Arrays.stream(BLOCKS_WITH_SLABS).toList().contains(block);
    }
    
    public static Block getParent(Block block) {
        if(SLABS.contains((SlabBlock) block)) {
            return SLAB_PARENTS.get(block);
        } else if(WALLS.contains((WallBlock) block)) {
        
        } else if(STAIRS.contains((StairsBlock) block)) {
        
        }
        return Blocks.AIR;
    }
    public static void registerBlocks() {
        JAAVAA.logInfo("Registering ModBlocks");
        
        for (Block block : VANILLA_BLOCKS) {
            String translationKey = block.getTranslationKey();
            String name = translationKey.substring(translationKey.lastIndexOf(".") + 1);
            //JAAVAA.logError("REGISTERING: " + name);
            if (!hasSlab(block)) {
                SlabBlock slabBlock = (SlabBlock) registerBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block)));
                SLABS.add(slabBlock);
                SLAB_PARENTS.put(slabBlock, block);
            }
            WallBlock wallBlock = (WallBlock) registerBlock(name + "_wall", new WallBlock(
                    FabricBlockSettings.copyOf(block)
            ));
            StairsBlock stairsBlock = (StairsBlock) registerBlock(name + "_stair", new StairsBlock(
                    block.getDefaultState(),
                    FabricBlockSettings.copyOf(block)
            ));
            
            WALLS.add(wallBlock);
            WALL_PARENTS.put(wallBlock, block);
            STAIRS.add(stairsBlock);
            STAIR_PARENTS.put(stairsBlock, block);
        }
    }
}
