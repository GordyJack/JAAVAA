package net.gordyjack.jaavaa.data.model.custom;

import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.custom.*;
import net.gordyjack.jaavaa.block.util.*;
import net.gordyjack.jaavll.util.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.state.properties.*;

public class BeamModelGenerator extends JAAVAAModelGenerator<BeamBlock> {
    public BeamModelGenerator(BlockModelGenerators bmGen) {
        super(bmGen);
    }

    @Override
    public void generate(BeamBlock beam, ResourceLocation side, ResourceLocation down, ResourceLocation up) {
        // Shared textures
        TextureMapping textures = TextureMapping.cube(side);
        textures.put(TextureSlot.DOWN, down);
        textures.put(TextureSlot.UP,   up);

        // One model per cap type, all using their own template
        ResourceLocation noneModel = simpleModel("block/beam_template_none", "_none")
                .create(beam, textures, this.modelOutput);
        ResourceLocation topModel = simpleModel("block/beam_template_top", "_top")
                .create(beam, textures, this.modelOutput);
        ResourceLocation bottomModel = simpleModel("block/beam_template_bottom", "_bottom")
                .create(beam, textures, this.modelOutput);
        ResourceLocation bothModel = simpleModel("block/beam_template_both", "_both")
                .create(beam, textures, this.modelOutput);

        // Blockstate: select model based on BEAM_CAP_TYPE
        MultiVariantGenerator generator = MultiVariantGenerator
                .dispatch(beam)
                .with(PropertyDispatch.initial(BeamBlock.BEAM_CAP_TYPE)
                        .select(BeamCapType.NONE,   BlockModelGenerators.plainVariant(noneModel))
                        .select(BeamCapType.TOP,    BlockModelGenerators.plainVariant(topModel))
                        .select(BeamCapType.BOTTOM, BlockModelGenerators.plainVariant(bottomModel))
                        .select(BeamCapType.BOTH,   BlockModelGenerators.plainVariant(bothModel))
                );
        var beamFacingOps = PropertyDispatch.modify(BeamBlock.FACING)
                .select(Direction.DOWN, NOP)
                .select(Direction.UP, NOP)
                .select(Direction.SOUTH, X_ROT_270)
                .select(Direction.NORTH, X_ROT_270)
                .select(Direction.EAST, X_ROT_90.then(Y_ROT_90))
                .select(Direction.WEST, X_ROT_90.then(Y_ROT_90));
        this.registerCustomRotateable(generator, beamFacingOps);
        this.registerSimpleItemModel(beam, JAAVAA.id("block/" + IDUtil.nameFromId(beam) + "_bottom"));
    }
}
