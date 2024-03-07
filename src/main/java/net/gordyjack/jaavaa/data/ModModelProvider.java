package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.gordyjack.jaavaa.blocks.*;
import net.gordyjack.jaavaa.items.*;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator bsmGen) {
        bsmGen.registerSimpleCubeAll(ModBlocks.TEST_BLOCK);
        generateMiniBlockModels();
        
        for (SlabBlock block: ModBlocks.SLABS) {
            registerSlabModel(bsmGen, ModBlocks.getParent(block), block);
//            bsmGen.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(block, JAAVAA.getID(name + "_bottom"),
//                            JAAVAA.getID(name + "_top"), JAAVAA.getID(name + "_full")));
        }
    }
    @Override
    public void generateItemModels(ItemModelGenerator imGen) {
        for (Item item : ModItems.ITEMS) {
            imGen.register(item, Models.GENERATED);
        }
    }
    private void registerSlabModel(BlockStateModelGenerator bsmGen, Block parentBlock, SlabBlock slabBlock) {
        TextureMap textureMap = TextureMap.all(parentBlock);
        registerSlabModel(bsmGen, textureMap, parentBlock, slabBlock);
    }
    private void registerSlabModel(BlockStateModelGenerator bsmGen, TextureMap textureMap, Block parentBlock, SlabBlock slabBlock) {
        Identifier bottomID = Models.SLAB.upload(slabBlock, textureMap, bsmGen.modelCollector);
        Identifier topID = Models.SLAB_TOP.upload(slabBlock, textureMap, bsmGen.modelCollector);
        String parentKey = parentBlock.getTranslationKey();
        String parentName = parentKey.substring(parentKey.lastIndexOf('.') + 1);
        Identifier doubleID = new Identifier("block/" + parentName);
        bsmGen.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(slabBlock, bottomID, topID, doubleID));
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
