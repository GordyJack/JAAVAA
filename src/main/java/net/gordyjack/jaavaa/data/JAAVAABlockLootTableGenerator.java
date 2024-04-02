package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.gordyjack.jaavaa.block.JAAVAABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class JAAVAABlockLootTableGenerator
extends FabricBlockLootTableProvider {
    public JAAVAABlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (Block block : JAAVAABlocks.BLOCKS) {
            String blockKey = block.getTranslationKey();
            if (blockKey.contains("glass")) {
                addDrop(block,  dropsWithSilkTouch(block));
            } else if (block instanceof SlabBlock) {
                addDrop(block, slabDrops(block));
            } else {
                addDrop(block);
            }
        }
    }
}
