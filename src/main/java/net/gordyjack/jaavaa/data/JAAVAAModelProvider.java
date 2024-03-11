package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.gordyjack.jaavaa.block.*;
import net.gordyjack.jaavaa.item.*;
import net.gordyjack.jaavaa.item.custom.*;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class JAAVAAModelProvider extends FabricModelProvider {
    public JAAVAAModelProvider(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator bsmGen) {
        bsmGen.registerSimpleCubeAll(JAAVAABlocks.TEST_BLOCK);
        registerVanillaBlockSets(bsmGen);
        generateMiniBlockModels();
    }
    @Override
    public void generateItemModels(ItemModelGenerator imGen) {
        for (Item item : JAAVAAItems.ITEMS) {
            if (item instanceof CollectorItem) {
                continue;
            }
            imGen.register(item, Models.GENERATED);
        }
    }

    private void registerVanillaBlockSets(BlockStateModelGenerator bsmGen) {
        for (SlabBlock slabBlock : JAAVAABlocks.SLABS) {
            Block parentBlock = JAAVAABlocks.getParent(slabBlock);
            registerSlabModel(bsmGen, createTextureMap(parentBlock), parentBlock, slabBlock);
        }
        for (WallBlock wallBlock : JAAVAABlocks.WALLS) {
            Block parentBlock = JAAVAABlocks.getParent(wallBlock);
            registerWallModel(bsmGen, createTextureMap(parentBlock), wallBlock);
        }
        for (StairsBlock stairsBlock : JAAVAABlocks.STAIRS) {
            Block parentBlock = JAAVAABlocks.getParent(stairsBlock);
            registerStairsModel(bsmGen, createTextureMap(parentBlock), stairsBlock);
        }
    }
    private TextureMap createTextureMap(Block parentBlock) {
        TextureMap textureMap = TextureMap.all(parentBlock);
//        if (parentBlock == Blocks.DRIED_KELP_BLOCK) {
//            textureMap.put(TextureKey.ALL, new Identifier("block/dried_kelp_side"));
//            textureMap.put(TextureKey.UP, new Identifier("block/dried_kelp_top"));
//            textureMap.put(TextureKey.TOP, new Identifier("block/dried_kelp_top"));
//            textureMap.put(TextureKey.DOWN, new Identifier("block/dried_kelp_bottom"));
//            textureMap.put(TextureKey.BOTTOM, new Identifier("block/dried_kelp_bottom"));
//        }
        //TODO: Implement all special cases
        if (parentBlock == Blocks.SMOOTH_QUARTZ) {
            textureMap.put(TextureKey.ALL, new Identifier("block/quartz_block_bottom"));
        }
        if (parentBlock == Blocks.CHISELED_QUARTZ_BLOCK) {
            textureMap.put(TextureKey.END, new Identifier("block/chiseled_quartz_block_top"));
            textureMap.put(TextureKey.SIDE, new Identifier("block/chiseled_quartz_block"));
        }
        if (parentBlock == Blocks.CUT_SANDSTONE) {
            textureMap.put(TextureKey.END, new Identifier("block/sandstone_top"));
            textureMap.put(TextureKey.SIDE, new Identifier("block/cut_sandstone"));
        }
        if (parentBlock == Blocks.CHISELED_SANDSTONE) {
            textureMap.put(TextureKey.END, new Identifier("block/sandstone_top"));
            textureMap.put(TextureKey.SIDE, new Identifier("block/chiseled_sandstone"));
        }
        if (parentBlock == Blocks.CUT_RED_SANDSTONE) {
            textureMap.put(TextureKey.END, new Identifier("block/red_sandstone_top"));
            textureMap.put(TextureKey.SIDE, new Identifier("block/cut_red_sandstone"));
        }
        if (parentBlock == Blocks.CHISELED_RED_SANDSTONE) {
            textureMap.put(TextureKey.END, new Identifier("block/red_sandstone_top"));
            textureMap.put(TextureKey.SIDE, new Identifier("block/chiseled_red_sandstone"));
        }
        if (parentBlock == Blocks.REINFORCED_DEEPSLATE) {
            textureMap.put(TextureKey.ALL, new Identifier("block/reinforced_deepslate_side"));
            textureMap.put(TextureKey.TOP, new Identifier("block/reinforced_deepslate_top"));
            textureMap.put(TextureKey.BOTTOM, new Identifier("block/reinforced_deepslate_bottom"));
        }
        return textureMap;
    }
    private void registerSlabModel(BlockStateModelGenerator bsmGen, TextureMap textureMap, Block parentBlock, SlabBlock slabBlock) {
        Identifier bottomID = Models.SLAB.upload(slabBlock, textureMap, bsmGen.modelCollector);
        Identifier topID = Models.SLAB_TOP.upload(slabBlock, textureMap, bsmGen.modelCollector);
        String parentKey = parentBlock.getTranslationKey();
        String parentName = parentKey.substring(parentKey.lastIndexOf('.') + 1);
        Identifier doubleID = new Identifier("block/" + parentName);
        bsmGen.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slabBlock, bottomID, topID, doubleID));
    }
    //TODO: Vanilla walls only use one texture/TextureKey for all sides, will need to implement custom template models to fix.
    //Reference minecraft:block/template_wall_post.json, minecraft:block/template_wall_side.json, and minecraft:block/template_wall_side_tall.json
    private void registerWallModel(BlockStateModelGenerator bsmGen, TextureMap textureMap, WallBlock wallBlock) {
        Identifier postId = Models.TEMPLATE_WALL_POST.upload(wallBlock, textureMap, bsmGen.modelCollector);
        Identifier sideId = Models.TEMPLATE_WALL_SIDE.upload(wallBlock, textureMap, bsmGen.modelCollector);
        Identifier tallId = Models.TEMPLATE_WALL_SIDE_TALL.upload(wallBlock, textureMap, bsmGen.modelCollector);
        bsmGen.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(wallBlock, postId, sideId, tallId));
        Identifier invId = Models.WALL_INVENTORY.upload(wallBlock, textureMap, bsmGen.modelCollector);
        bsmGen.registerParentedItemModel(wallBlock, invId);
    }
    private void registerStairsModel(BlockStateModelGenerator bsmGen, TextureMap textureMap, StairsBlock stairsBlock) {
        Identifier innerStairId = Models.INNER_STAIRS.upload(stairsBlock, textureMap, bsmGen.modelCollector);
        Identifier stairId = Models.STAIRS.upload(stairsBlock, textureMap, bsmGen.modelCollector);
        Identifier outerStairId = Models.OUTER_STAIRS.upload(stairsBlock, textureMap, bsmGen.modelCollector);
        bsmGen.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairsBlock, innerStairId, stairId, outerStairId));
        bsmGen.registerParentedItemModel(stairsBlock, stairId);
    }
    private void generateMiniBlockModels() {
        String bottomSingle = "mini_block_00000001_single.json";
        String bottomDualStraight = "mini_block_0000011_dual_straight.json";
        String bottomDualDiagonal = "mini_block_00001001_dual_diagonal.json";
        String bottomTriple = "mini_block_00001011_triple.json";
        String bottomQuad = "mini_block_00001111_quad_slab.json";
        String topSingle = "mini_block_00010000_single.json";
        String topDualStraight = "mini_block_00110000_dual_straight.json";
        String topDualDiagonal = "mini_block_10010000_dual_diagonal.json";
        String topTriple = "mini_block_10110000_triple.json";
        String topQuad = "mini_block_11110000_quad_slab.json";
    }
}
