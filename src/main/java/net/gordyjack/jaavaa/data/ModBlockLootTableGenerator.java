package net.gordyjack.jaavaa.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.gordyjack.jaavaa.blocks.ModBlocks;
import net.minecraft.block.Block;

public class ModBlockLootTableGenerator
extends FabricBlockLootTableProvider {
    public ModBlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (Block block : ModBlocks.BLOCKS) {
            String blockKey = block.getTranslationKey();
            if (blockKey.contains("glass")) {
                addDrop(block,  dropsWithSilkTouch(block));
            } else {
                addDrop(block);
            }
        }
    }
}