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
    private static final PropertyDispatch<VariantMutator> DOWN_DEFAULT_ROTATION_OPERATIONS =
            PropertyDispatch.modify(PanelBlock.FACING)
                    .select(Direction.DOWN, NOP)
                    .select(Direction.UP, X_ROT_180)
                    .select(Direction.SOUTH, X_ROT_90)
                    .select(Direction.NORTH, X_ROT_90.then(Y_ROT_180))
                    .select(Direction.EAST, X_ROT_90.then(Y_ROT_270))
                    .select(Direction.WEST, X_ROT_90.then(Y_ROT_90));

    public PanelModelGenerator(BlockModelGenerators bmGen) {
        super(bmGen);
    }

    @Override
    public void generate(PanelBlock block, ResourceLocation side, ResourceLocation down, ResourceLocation up) {
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(PanelBlock.LAYERS)
                        .generate((layerAmount) -> plainVariant(
                                TexturedModel.createDefault((ignored) -> {
                                            TextureMapping textureMap = TextureMapping.cube(side);
                                            textureMap.put(TextureSlot.DOWN, down);
                                            textureMap.put(TextureSlot.UP, up);
                                            return textureMap;
                                        }, new ModelTemplate(Optional.of(JAAVAA.id("block/panel_block_template_" + layerAmount)),
                                                Optional.of("_" + layerAmount), TextureSlot.SIDE, TextureSlot.DOWN, TextureSlot.UP)
                                ).create(block, this.modelOutput)))
                ).with(DOWN_DEFAULT_ROTATION_OPERATIONS));
        this.registerSimpleItemModel(block, JAAVAA.id("block/" + IDUtil.nameFromId(block) + "_1"));
    }
}
