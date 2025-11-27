package net.gordyjack.jaavaa.data.model.custom;

import net.gordyjack.jaavaa.*;
import net.gordyjack.jaavaa.block.custom.*;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;

import java.util.*;

public abstract class JAAVAAModelGenerator<T extends Block> extends BlockModelGenerators {
    protected static final PropertyDispatch<VariantMutator> DOWN_DEFAULT_ROTATION_OPERATIONS =
            PropertyDispatch.modify(BlockStateProperties.FACING)
                    .select(Direction.DOWN, NOP)
                    .select(Direction.UP, X_ROT_180)
                    .select(Direction.SOUTH, X_ROT_90)
                    .select(Direction.NORTH, X_ROT_90.then(Y_ROT_180))
                    .select(Direction.EAST, X_ROT_90.then(Y_ROT_270))
                    .select(Direction.WEST, X_ROT_90.then(Y_ROT_90));

    public JAAVAAModelGenerator (BlockModelGenerators bmGen) {
        super(bmGen.blockStateOutput, bmGen.itemModelOutput, bmGen.modelOutput);
    }

    public abstract void generate(T block, ResourceLocation side, ResourceLocation down, ResourceLocation up);
    public void generate(T block, ResourceLocation side, ResourceLocation ends) {
        generate(block, side, ends, ends);
    }
    public void generate(T block, ResourceLocation all) {
        generate(block, all, all, all);
    }
    protected void registerDownDefaultRotateable(MultiVariantGenerator generator) {
        this.registerCustomRotateable(generator, DOWN_DEFAULT_ROTATION_OPERATIONS);
    }
    protected void registerCustomRotateable(MultiVariantGenerator generator, PropertyDispatch<VariantMutator> rotationOperations) {
        this.blockStateOutput.accept(generator.with(rotationOperations));
    }
    protected void registerDownDefaultRotateable(Block block, TexturedModel.Provider modelFactory) {
        MultiVariant mV = BlockModelGenerators.plainVariant(modelFactory.create(block, this.modelOutput));
        this.blockStateOutput.accept(this.createDownDefaultRotateableBlockState(block, mV));
    }
    protected BlockModelDefinitionGenerator createDownDefaultRotateableBlockState(Block block, MultiVariant model) {
        return MultiVariantGenerator.dispatch(block, model).with(DOWN_DEFAULT_ROTATION_OPERATIONS);
    }
    protected ModelTemplate simpleModel(String name) {
        return simpleModel(name, "");
    }
    protected ModelTemplate simpleModel(String name, String suffix) {
        return new ModelTemplate(
                Optional.of(JAAVAA.id(name)),
                Optional.of(suffix),
                TextureSlot.SIDE, TextureSlot.DOWN, TextureSlot.UP);
    }
}
