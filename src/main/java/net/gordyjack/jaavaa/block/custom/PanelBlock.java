package net.gordyjack.jaavaa.block.custom;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.*;

public class PanelBlock extends BlockPiece {
    private final MapCodec<PanelBlock> CODEC = simpleCodec(PanelBlock::new);

    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;

    public PanelBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        builder.add(LAYERS);
        super.createBlockStateDefinition(builder);
    }
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        int i = state.getValue(LAYERS);
        if (!ctx.getItemInHand().is(this.asItem()) || i >= 8) {
            return i == 1;
        } else {
            return !ctx.replacingClickedOnBlock() || ctx.getClickedFace().getOpposite() == state.getValue(FACING);
        }
    }
    @Override
    protected float getShadeBrightness(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return state.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
    }
    @Override
    protected @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        int layersInPixels = state.getValue(LAYERS) * 2;
        return switch (state.getValue(FACING)) {
            case DOWN -> box(0, 0, 0, 16, layersInPixels, 16);
            case UP -> box(0, 16 - layersInPixels, 0, 16, 16, 16);
            case NORTH -> box(0, 0, 0, 16, 16, layersInPixels);
            case SOUTH -> box(0,0, 16 - layersInPixels, 16, 16, 16);
            case WEST -> box(0, 0, 0, layersInPixels, 16, 16);
            case EAST -> box(16 - layersInPixels, 0, 0, 16, 16, 16);
        };
    }
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState existingState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        BlockState newState = super.getStateForPlacement(ctx);
        int layers = existingState.is(this) ? existingState.getValue(LAYERS) + 1 : 1;
        return newState.setValue(LAYERS, Math.min(layers, 8));
    }
    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return state.getValue(LAYERS) < 8;
    }
}
