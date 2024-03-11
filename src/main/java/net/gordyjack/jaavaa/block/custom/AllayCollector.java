package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.MapCodec;
import net.gordyjack.jaavaa.block.custom.entity.AllayCollectorEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class AllayCollector
extends Block
implements BlockEntityProvider {
    //TODO: All of this
    public AllayCollector(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AllayCollectorEntity(pos, state);
    }
}
