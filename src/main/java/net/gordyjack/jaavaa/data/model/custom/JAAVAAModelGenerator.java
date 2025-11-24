package net.gordyjack.jaavaa.data.model.custom;

import net.minecraft.client.data.models.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;

public abstract class JAAVAAModelGenerator<T extends Block> extends BlockModelGenerators {
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
}
