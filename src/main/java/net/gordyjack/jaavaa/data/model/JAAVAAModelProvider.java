package net.gordyjack.jaavaa.data.model;

import net.fabricmc.fabric.api.client.datagen.v1.provider.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.gordyjack.jaavaa.block.*;
import net.gordyjack.jaavaa.block.util.*;
import net.gordyjack.jaavaa.data.model.custom.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;

import static net.gordyjack.jaavaa.block.JAAVAABlocks.*;

public class JAAVAAModelProvider extends FabricModelProvider {
    private BlockModelGenerators bmGen;
    private ItemModelGenerators imGen;

    public JAAVAAModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        this.bmGen = blockModelGenerators;
        generateBlockPieceSet(CHISELED_SANDSTONE_SET,
                ModelLocationUtils.getModelLocation(Blocks.CHISELED_SANDSTONE),
                ModelLocationUtils.getModelLocation(Blocks.SANDSTONE, "_top")
        );
        generateBlockPieceSet(CUT_SANDSTONE_SET,
                ModelLocationUtils.getModelLocation(Blocks.CUT_SANDSTONE),
                ModelLocationUtils.getModelLocation(Blocks.SANDSTONE, "_top")
        );
        generateBlockPieceSet(IRON_BLOCK_SET,
                ModelLocationUtils.getModelLocation(Blocks.IRON_BLOCK)
        );
        generateBlockPieceSet(SANDSTONE_SET,
                ModelLocationUtils.getModelLocation(Blocks.SANDSTONE),
                ModelLocationUtils.getModelLocation(Blocks.SANDSTONE, "_bottom"),
                ModelLocationUtils.getModelLocation(Blocks.SANDSTONE, "_top")
        );
        generateBlockPieceSet(SMOOTH_SANDSTONE_SET,
                ModelLocationUtils.getModelLocation(Blocks.SANDSTONE, "_top")
        );
        generateBlockPieceSet(STONE_SET,
                ModelLocationUtils.getModelLocation(Blocks.STONE)
        );
    }
    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        this.imGen = itemModelGenerators;
    }
    private void generateBlockPieceSet(BlockPieceSet set, ResourceLocation texture) {
        generateBlockPieceSet(set, texture, texture, texture);
    }
    private void generateBlockPieceSet(BlockPieceSet set, ResourceLocation side, ResourceLocation ends) {
        generateBlockPieceSet(set, side, ends, ends);
    }
    private void generateBlockPieceSet(BlockPieceSet set, ResourceLocation side, ResourceLocation down, ResourceLocation up) {
        BeamModelGenerator beamGen = new BeamModelGenerator(bmGen);
        BlocktantModelGenerator blocktantGen = new BlocktantModelGenerator(bmGen);
        PanelModelGenerator panelGen = new PanelModelGenerator(bmGen);
        beamGen.generate(set.beam(), side, down, up);
        blocktantGen.generate(set.blocktant(), side, down, up);
        panelGen.generate(set.panel(), side, down, up);
    }
}
