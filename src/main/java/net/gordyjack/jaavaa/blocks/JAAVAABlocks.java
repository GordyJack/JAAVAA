package net.gordyjack.jaavaa.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.*;
import net.gordyjack.jaavaa.JAAVAA;

import net.fabricmc.fabric.api.item.v1.*;
import net.gordyjack.jaavaa.blocks.custom.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class JAAVAABlocks {
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
            Blocks.GLASS,
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
            Blocks.WHITE_STAINED_GLASS,
            Blocks.ORANGE_STAINED_GLASS,
            Blocks.MAGENTA_STAINED_GLASS,
            Blocks.LIGHT_BLUE_STAINED_GLASS,
            Blocks.YELLOW_STAINED_GLASS,
            Blocks.LIME_STAINED_GLASS,
            Blocks.PINK_STAINED_GLASS,
            Blocks.GRAY_STAINED_GLASS,
            Blocks.LIGHT_GRAY_STAINED_GLASS,
            Blocks.CYAN_STAINED_GLASS,
            Blocks.PURPLE_STAINED_GLASS,
            Blocks.BLUE_STAINED_GLASS,
            Blocks.BROWN_STAINED_GLASS,
            Blocks.GREEN_STAINED_GLASS,
            Blocks.RED_STAINED_GLASS,
            Blocks.BLACK_STAINED_GLASS,
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
            Blocks.REDSTONE_BLOCK,
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
//            Blocks.DRIED_KELP_BLOCK,
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
    private static boolean hasWall(Block block) {
        final Block[] BLOCKS_WITH_WALLS = {
                Blocks.COBBLESTONE,
                Blocks.MOSSY_COBBLESTONE,
                Blocks.BRICKS,
                Blocks.PRISMARINE,
                Blocks.RED_SANDSTONE,
                Blocks.MOSSY_STONE_BRICKS,
                Blocks.GRANITE,
                Blocks.STONE_BRICKS,
                Blocks.MUD_BRICKS,
                Blocks.NETHER_BRICKS,
                Blocks.ANDESITE,
                Blocks.RED_NETHER_BRICKS,
                Blocks.SANDSTONE,
                Blocks.END_STONE_BRICKS,
                Blocks.DIORITE,
                Blocks.BLACKSTONE,
                Blocks.POLISHED_BLACKSTONE_BRICKS,
                Blocks.POLISHED_BLACKSTONE,
                Blocks.TUFF,
                Blocks.POLISHED_TUFF,
                Blocks.TUFF_BRICKS,
                Blocks.COBBLED_DEEPSLATE,
                Blocks.POLISHED_DEEPSLATE,
                Blocks.DEEPSLATE_TILES,
                Blocks.DEEPSLATE_BRICKS
        };
        return Arrays.stream(BLOCKS_WITH_WALLS).toList().contains(block);
    }
    private static boolean hasStairs(Block block) {
        final Block[] BLOCKS_WITH_STAIRS = {
                Blocks.OAK_PLANKS,
                Blocks.COBBLESTONE,
                Blocks.BRICKS,
                Blocks.STONE_BRICKS,
                Blocks.MUD_BRICKS,
                Blocks.NETHER_BRICKS,
                Blocks.SANDSTONE,
                Blocks.SPRUCE_PLANKS,
                Blocks.BIRCH_PLANKS,
                Blocks.JUNGLE_PLANKS,
                Blocks.QUARTZ_BLOCK,
                Blocks.ACACIA_PLANKS,
                Blocks.CHERRY_PLANKS,
                Blocks.DARK_OAK_PLANKS,
                Blocks.MANGROVE_PLANKS,
                Blocks.BAMBOO_PLANKS,
                Blocks.BAMBOO_MOSAIC,
                Blocks.PRISMARINE,
                Blocks.PRISMARINE_BRICKS,
                Blocks.DARK_PRISMARINE,
                Blocks.RED_SANDSTONE,
                Blocks.PURPUR_BLOCK,
                Blocks.POLISHED_GRANITE,
                Blocks.SMOOTH_RED_SANDSTONE,
                Blocks.MOSSY_STONE_BRICKS,
                Blocks.POLISHED_DIORITE,
                Blocks.MOSSY_COBBLESTONE,
                Blocks.END_STONE_BRICKS,
                Blocks.STONE,
                Blocks.SMOOTH_SANDSTONE,
                Blocks.SMOOTH_QUARTZ,
                Blocks.GRANITE,
                Blocks.ANDESITE,
                Blocks.RED_NETHER_BRICKS,
                Blocks.POLISHED_ANDESITE,
                Blocks.DIORITE,
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
        return Arrays.stream(BLOCKS_WITH_STAIRS).toList().contains(block);
    }
    
    public static Block getParent(Block block) {
    if(block instanceof SlabBlock && SLABS.contains((SlabBlock) block)) {
        return SLAB_PARENTS.get(block);
    } else if (block instanceof WallBlock && WALLS.contains((WallBlock) block)) {
        return WALL_PARENTS.get(block);
    } else if (block instanceof StairsBlock && STAIRS.contains((StairsBlock) block)) {
        return STAIR_PARENTS.get(block);
    }
    return Blocks.AIR;
}
    public static void registerBlocks() {
        JAAVAA.logInfo("Registering ModBlocks");
        
        for (Block parentBlock : VANILLA_BLOCKS) {
            String translationKey = parentBlock.getTranslationKey();
            String name = translationKey.substring(translationKey.lastIndexOf(".") + 1);
            Map<String, Block> blockSet;
            
            boolean isGlass = name.contains("glass");
            boolean isStainedGlass = name.contains("stained") && isGlass;
            boolean isTinted = name.contains("tinted") && isGlass;
            if (isGlass) {
                if (isStainedGlass) {
                    blockSet = getBlockSet(parentBlock, "stained");
                } else if (isTinted) {
                    blockSet = getBlockSet(parentBlock, "tinted");
                }else {
                    blockSet = getBlockSet(parentBlock, "glass");
                }
            } else if (parentBlock == Blocks.REDSTONE_BLOCK) {
                blockSet = getBlockSet(parentBlock, "redstone");
            } else {
                blockSet = getBlockSet(parentBlock);
            }
            registerBlockSet(parentBlock, blockSet, name);
        }
    }
    private static Map<String, Block> getBlockSet(Block parentBlock) {
        return getBlockSet(parentBlock, "none");
    }
    private static Map<String, Block> getBlockSet(Block parentBlock, String type) {
        Map<String, Block> map = new HashMap<>();
        FabricBlockSettings parentSettings = FabricBlockSettings.copyOf(parentBlock);
        switch (type) {
            case "glass" -> {
                if (!hasSlab(parentBlock)) map.put("slab", new JAAVAASlab.Transparent(parentSettings));
                if (!hasWall(parentBlock)) map.put("wall", new JAAVAAWall.Transparent(parentSettings));
                if (!hasStairs(parentBlock)) map.put("stairs", new StairsBlock(parentBlock.getDefaultState(), parentSettings));
            }
            case "stained" -> {
                DyeColor color = getDyeColor(parentBlock);
                if (!hasSlab(parentBlock)) map.put("slab", new JAAVAASlab.StainedGlass(color, parentSettings));
                if (!hasWall(parentBlock)) map.put("wall", new JAAVAAWall.StainedGlass(color, parentSettings));
                if (!hasStairs(parentBlock)) map.put("stairs", new StairsBlock(parentBlock.getDefaultState(), parentSettings));
            }
            case "tinted" -> {
                if (!hasSlab(parentBlock)) map.put("slab", new JAAVAASlab.TintedGlass(parentSettings));
                if (!hasWall(parentBlock)) map.put("wall", new JAAVAAWall.TintedGlass(parentSettings));
                if (!hasStairs(parentBlock)) map.put("stairs", new StairsBlock(parentBlock.getDefaultState(), parentSettings));
            }
            case "redstone" -> {
                if (!hasSlab(parentBlock)) map.put("slab", new JAAVAASlab.Redstone(parentSettings));
                if (!hasWall(parentBlock)) map.put("wall", new JAAVAAWall.Redstone(parentSettings));
                if (!hasStairs(parentBlock)) map.put("stairs", new StairsBlock(parentBlock.getDefaultState(), parentSettings));
            }
            default -> {
                if (!hasSlab(parentBlock)) map.put("slab", new SlabBlock(parentSettings));
                if (!hasWall(parentBlock)) map.put("wall", new WallBlock(parentSettings));
                if (!hasStairs(parentBlock)) map.put("stairs", new StairsBlock(parentBlock.getDefaultState(), parentSettings));
            }
        }
        return map;
    }
    @NotNull
    private static DyeColor getDyeColor(Block parentBlock) {
        String parentKey = parentBlock.getTranslationKey();
        String colorName = parentKey.substring(parentKey.lastIndexOf('.') + 1, parentKey.indexOf("_stained"));
        return switch (colorName) {
            case "white" -> DyeColor.WHITE;
            case "orange" -> DyeColor.ORANGE;
            case "magenta" -> DyeColor.MAGENTA;
            case "light_blue" -> DyeColor.LIGHT_BLUE;
            case "yellow" -> DyeColor.YELLOW;
            case "lime" -> DyeColor.LIME;
            case "pink" -> DyeColor.PINK;
            case "gray" -> DyeColor.GRAY;
            case "light_gray" -> DyeColor.LIGHT_GRAY;
            case "cyan" -> DyeColor.CYAN;
            case "purple" -> DyeColor.PURPLE;
            case "blue" -> DyeColor.BLUE;
            case "brown" -> DyeColor.BROWN;
            case "green" -> DyeColor.GREEN;
            case "red" -> DyeColor.RED;
            case "black" -> DyeColor.BLACK;
            default -> DyeColor.WHITE;
        };
    }
    private static void registerBlockSet(Block parentBlock, Map<String, Block> blockMap, String name) {
        if (blockMap.get("slab") != null) {
            SlabBlock registeredSlab = (SlabBlock) registerBlock(name + "_slab", blockMap.get("slab"));
            SLABS.add(registeredSlab);
            SLAB_PARENTS.put(registeredSlab, parentBlock);
        }
        if (blockMap.get("wall") != null) {
            WallBlock registeredWall = (WallBlock) registerBlock(name + "_wall", blockMap.get("wall"));
            WALLS.add(registeredWall);
            WALL_PARENTS.put(registeredWall, parentBlock);
        }
        if (blockMap.get("stairs") != null) {
            StairsBlock registeredStair = (StairsBlock) registerBlock(name + "_stairs", blockMap.get("stairs"));
            STAIRS.add(registeredStair);
            STAIR_PARENTS.put(registeredStair, parentBlock);
        }
    }
}
