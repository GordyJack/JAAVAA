package net.gordyjack.jaavaa.data.model.custom;

import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.custom.*;
import net.gordyjack.jaavll.util.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;

import java.util.*;

public class PanelModelGenerator extends JAAVAAModelGenerator<PanelBlock> {
    public PanelModelGenerator(BlockModelGenerators bmGen) {
        super(bmGen);
    }

    @Override
    public void generate(PanelBlock block, ResourceLocation side, ResourceLocation down, ResourceLocation up) {
        TextureMapping textures = TextureMapping.cube(side);
        textures.put(TextureSlot.DOWN, down);
        textures.put(TextureSlot.UP,   up);

        MultiVariantGenerator generator = MultiVariantGenerator
                .dispatch(block)
                .with(PropertyDispatch.initial(PanelBlock.LAYERS)
                        .generate(layerAmount -> plainVariant(
                                simpleModel("block/panel_block_template_" + layerAmount, "_" + layerAmount)
                                        .create(block, textures, this.modelOutput))));

        this.registerDownDefaultRotateable(generator);
        this.registerSimpleItemModel(block, JAAVAA.id("block/" + IDUtil.nameFromId(block) + "_1"));
    }
}
