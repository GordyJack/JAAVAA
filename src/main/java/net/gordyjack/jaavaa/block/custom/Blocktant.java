package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.*;
import net.minecraft.world.level.block.*;

public class Blocktant extends BlockPiece {
    private static final MapCodec<Blocktant> CODEC = simpleCodec(Blocktant::new);

    public Blocktant(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }
}
