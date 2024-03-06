package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.gordyjack.jaavaa.blocks.*;
import net.gordyjack.jaavaa.items.*;
import net.minecraft.data.client.*;
import net.minecraft.item.*;

public class ModModelProvider extends FabricModelProvider {
    private final FabricDataOutput OUTPUT;
    public ModModelProvider(FabricDataOutput output) {
        super(output);
        this.OUTPUT = output;
    }
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator bsmGen) {
        bsmGen.registerSimpleCubeAll(ModBlocks.TEST_BLOCK);
        generateMiniBlockModels();
    }
    @Override
    public void generateItemModels(ItemModelGenerator imGen) {
        for (Item item : ModItems.ITEMS) {
            imGen.register(item, Models.GENERATED);
        }
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
