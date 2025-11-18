package net.gordyjack.jaavaa.block.custom;

import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import org.jetbrains.annotations.*;

public abstract class BlockPiece extends DirectionalBlock implements SimpleWaterloggedBlock {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected BlockPiece(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        BlockState existingState = ctx.getLevel().getBlockState(pos);
        FluidState existingFluidState = ctx.getLevel().getFluidState(pos);
        BlockState newState = super.getStateForPlacement(ctx);
        Direction facing = existingState.is(this) ? existingState.getValue(FACING) : ctx.getClickedFace().getOpposite();
        return newState
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, existingFluidState.is(Fluids.WATER));
    }
}
