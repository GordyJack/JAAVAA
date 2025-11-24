package net.gordyjack.jaavaa.data.model;

import net.fabricmc.fabric.api.client.datagen.v1.provider.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.gordyjack.jaavaa.block.*;
import net.gordyjack.jaavaa.data.model.custom.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.world.level.block.*;

public class JAAVAAModelProvider extends FabricModelProvider {
    private BlockModelGenerators bmGen;
    private ItemModelGenerators imGen;

    public JAAVAAModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        this.bmGen = blockModelGenerators;
        BlocktantModelGenerator blocktantGen = new BlocktantModelGenerator(blockModelGenerators);
        blocktantGen.generate(JAAVAABlocks.STONE_SET.blocktant(),
                ModelLocationUtils.getModelLocation(Blocks.STONE)
        );
        PanelModelGenerator panelGen = new PanelModelGenerator(blockModelGenerators);
        panelGen.generate(JAAVAABlocks.STONE_SET.panel(),
                ModelLocationUtils.getModelLocation(Blocks.STONE)
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        this.imGen = itemModelGenerators;
    }
}
